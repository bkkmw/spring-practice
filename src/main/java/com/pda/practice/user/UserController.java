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
    public ResponseEntity<Map<String, Object>> signup(@Valid @RequestBody UserDto.SignupReqDto signupReqDto) {
        HttpStatus status;
        Map<String, Object> result = new HashMap<>();

        log.info("Received signup request : {}", signupReqDto.toString());
        try {
            if(! Pattern.matches("^(.+)@(\\S+)$", signupReqDto.getEmail())) {
                log.info("invalid email : {}", signupReqDto.getEmail());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User user = userService.signup(signupReqDto);
            status = HttpStatus.CREATED;
            log.info("created user PK : {}", user.getId());
            result.put("userId", user.getUserId());

        } catch (DuplicatedKeyException | DataIntegrityViolationException e) {
            status = HttpStatus.CONFLICT;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("message", e.getMessage());
            log.info("Unintended Exception : {}", e.getClass());
        }

        return new ResponseEntity<>(result, status);
    }

    @PostMapping("/join")
    public ApiUtils.ApiResult<Object> joinByApiResult(@RequestBody UserDto.SignupReqDto signupReqDto) {

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
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDto.LoginReqDto loginReqDto) {
        HttpStatus status;
        Map<String, Object> result = new HashMap<>();

        log.info("Received login request : {}", loginReqDto.toString());
        try {
            UserDto.LoginRespDto user = userService.login(loginReqDto);

            status = HttpStatus.OK;
            result.put("userInfo", user);
        } catch (InvalidLoginReqException | NoResultException e) {
            status = HttpStatus.UNAUTHORIZED;
            result.put("message", "Incorrect ID or password");
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            log.info("Unintended Exception : {}", e.getClass());
            result.put("message", e.getMessage());
        }

        return new ResponseEntity<>(result, status);

    }

}
