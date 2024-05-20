package com.pda.practice.user;


import com.pda.practice.exception.DuplicatedKeyException;
import com.pda.practice.utils.ApiUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @ExceptionHandler
    public ApiUtils.ApiResult<Object> exceptionHandler(MethodArgumentNotValidException e) {
        return ApiUtils.success("from controller");
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@Valid @RequestBody User.SignupReqDto signupReqDto, Errors errors) {
//    public ResponseEntity<Map<String, Object>> signup(@Valid @RequestBody User.SignupReqDto signupReqDto) {
        HttpStatus status;
        Map<String, Object> result = new HashMap<>();
        log.warn("error : {}", errors.toString());
        if(errors.hasErrors()) {
            result.put("errors", errors.getAllErrors());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        log.info("Received signup request : {}", signupReqDto.toString());
        try {
            if(! Pattern.matches("^(.+)@(\\S+)$", signupReqDto.getEmail())) {
                log.info("invalid email : {}", signupReqDto.getEmail());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User user = userService.signup(signupReqDto);
            status = HttpStatus.CREATED;
            result.put("userId", user.getUserId());

        } catch (DuplicatedKeyException e) {
            status = HttpStatus.CONFLICT;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("message", e.getMessage());
        }

        return new ResponseEntity<>(result, status);
    }

    @PostMapping("/join")
    public ApiUtils.ApiResult<Object> joinByApiResult(@RequestBody User.SignupReqDto signupReqDto) {

        try {
            User user = userService.signup(signupReqDto);
            return ApiUtils.success(user.getUserId());
        } catch (DuplicatedKeyException e) {
            return ApiUtils.error("user already exists", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ApiUtils.error("unintended server error : "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/checkId/{userId}")
    public ResponseEntity<Map<String, Object>> checkId(@PathVariable String userId) {
        HttpStatus status;
        Map<String, Object> result = new HashMap<>();

        log.info("Received user id : {}", userId);
        try {
            userService.checkId(userId);
            log.info("user id does not exists");
            status = HttpStatus.OK;
        } catch (DuplicatedKeyException e) {
            log.info("user already exists");
            status = HttpStatus.CONFLICT;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("message", e.getMessage());
        }

        return new ResponseEntity<>(result, status);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User.LoginReqDto loginReqDto) {
        HttpStatus status;
        Map<String, Object> result = new HashMap<>();

        log.info("Received login request : {}", loginReqDto.toString());
        try {
//            User user = userService.login(loginReq);

            status = HttpStatus.OK;
//            result.put("user", user);
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("message", e.getMessage());
        }

        return new ResponseEntity<>(result, status);
    }

}