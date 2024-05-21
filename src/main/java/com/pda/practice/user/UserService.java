package com.pda.practice.user;

import com.pda.practice.entity.User;
import com.pda.practice.exception.DuplicatedKeyException;
import com.pda.practice.exception.InvalidLoginReqException;
import com.pda.practice.utils.RandAuthenticationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User signup(UserDto.SignupReqDto signupReqDto) throws Exception {
        userRepository.create(signupReqDto);

        return userRepository.findByUserId(signupReqDto.getUserId());
    }

    public int checkId(String userId) throws Exception {
        if(userRepository.findByUserId(userId) == null) {
            log.info("user does not exists");
            return 1;
        }
        else {
            log.info("user id {} already exists", userId);
            throw new DuplicatedKeyException("Given user id already exists");
        }
    }

    public UserDto.LoginRespDto login(UserDto.LoginReqDto loginReqDto) throws Exception {

        User user = userRepository.findByUserId(loginReqDto.getUserId());

        log.info("???? : {}", user);

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
