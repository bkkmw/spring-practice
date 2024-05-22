package com.pda.practice.user;

import com.pda.practice.entity.User;
import com.pda.practice.exception.DuplicatedKeyException;
import com.pda.practice.exception.InvalidLoginReqException;
import com.pda.practice.utils.RandAuthenticationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

//    private final UserRepository userRepository;
    private final UserJpaRepository userRepository;

    @Transactional
    public User signup(UserDto.SignupReqDto signupReqDto) {
//        userRepository.create(signupReqDto);

        userRepository.save(User.builder()
                .userId(signupReqDto.getUserId())
                .password(signupReqDto.getPassword())
                .email(signupReqDto.getEmail())
                .name(signupReqDto.getName())
                .contact(signupReqDto.getContact())
                .build());

        log.info("created user");

        User user = userRepository.findByUserId(signupReqDto.getUserId()).get();
        log.info("Retrieve user : {}", user.getId());

        return user;
    }

    public int checkId(String userId) {
        if(userRepository.findByUserId(userId).isEmpty()) {
            log.info("user does not exists");
            return 1;
        }
        else {
            log.info("user id {} already exists", userId);
            throw new DuplicatedKeyException("Given user id already exists");
        }
    }

    public UserDto.LoginRespDto login(UserDto.LoginReqDto loginReqDto) {

        User user = userRepository.findByUserId(loginReqDto.getUserId())
                        .orElseThrow(InvalidLoginReqException::new);

        log.info("User found : {}", user);

        if(user.getPassword() == null || !user.getPassword().equals(loginReqDto.getPassword()))
            throw new InvalidLoginReqException();

        String[] tokens = RandAuthenticationUtil.createToken(user);

        return UserDto.LoginRespDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .contact(user.getContact())
                .accessToken(tokens[0])
                .build();
    }
}
