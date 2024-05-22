package com.pda.practice.user;


import com.pda.practice.entity.User;
import com.pda.practice.exception.DuplicatedKeyException;
import com.pda.practice.exception.InvalidLoginReqException;
import com.pda.practice.utils.ApiUtils;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
//    public ResponseEntity<Map<String, Object>> signup(@Valid @RequestBody UserDto.SignupReqDto signupReqDto, Errors errors) {
    public ApiUtils.ApiResult<Object> signup(@Valid @RequestBody UserDto.SignupReqDto signupReqDto) {
        Map<String, Object> result = new HashMap<>();

        log.info("Received signup request : {}", signupReqDto.toString());

        User user = userService.signup(signupReqDto);
        log.info("created user PK : {}", user.getId());
        result.put("userId", user.getUserId());

        return ApiUtils.success(result);
    }

    @GetMapping("/checkId/{userId}")
    public ApiUtils.ApiResult<Object> checkId(@PathVariable String userId) {
        Map<String, Object> result = new HashMap<>();

        log.info("Received user id : {}", userId);

        userService.checkId(userId);
        log.info("user id does not exists");
        result.put("message", "Given user Id is Available");

        return ApiUtils.success(result);
    }

    @PostMapping("/login")
    public ApiUtils.ApiResult<Object> login(@RequestBody UserDto.LoginReqDto loginReqDto) {
        Map<String, Object> result = new HashMap<>();

        log.info("Received login request : {}", loginReqDto.toString());

        UserDto.LoginRespDto user = userService.login(loginReqDto);
        result.put("userInfo", user);

        return ApiUtils.success(result);

    }

    @ExceptionHandler(value = InvalidLoginReqException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiUtils.ApiResult<Object> handleInvalidLoginReq(InvalidLoginReqException e) {
        log.info("Invalid login request");
        return ApiUtils.error(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
