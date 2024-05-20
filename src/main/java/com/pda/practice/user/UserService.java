package com.pda.practice.user;

import com.pda.practice.exception.DuplicatedKeyException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User signup(User.SignupReqDto signupReqDto) throws Exception {
        return userRepository.create(signupReqDto);
    }

    public int checkId(String userId) throws Exception {
        if(userRepository.findById(userId) == null) {
            log.info("user does not exists");
            return 1;
        }
        else {
            log.info("user id {} already exists", userId);
            throw new DuplicatedKeyException("Given user id already exists");
        }
    }

//    public User login(User.LoginReq loginReq) throws Exception {
//        User user = userRepository.findById(loginReq.getId());
//
//        if(user.getPassword() == null && !user.getPassword().equals(loginReq.getPassword()))
//            throw new
//    }
}
