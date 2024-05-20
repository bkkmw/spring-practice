package com.pda.practice.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/test")
public class TestController {

    @PostMapping("/lower-camel")
    public ResponseEntity<String> lowerCamelTest(@RequestBody TestDto.LowerCamelCase reqBody) {
        log.info("Req body : {}", reqBody.getTestValue());
        return new ResponseEntity<>(reqBody.getTestValue(), HttpStatus.OK);
    }

    @PostMapping("/upper-camel")
    public ResponseEntity<String> upperCamelTest(@RequestBody TestDto.UpperCamelCase reqBody) {
        log.info("Req body : {}", reqBody.getTestValue());
        return new ResponseEntity<>(reqBody.getTestValue(), HttpStatus.OK);
    }

    @PostMapping("/snake")
    public ResponseEntity<String> snakeTest(@RequestBody TestDto.SnakeCase reqBody) {
        log.info("Req body : {}", reqBody.getTestValue());
        return new ResponseEntity<>(reqBody.getTestValue(), HttpStatus.OK);
    }

    @PostMapping("/upper-snake")
    public ResponseEntity<String> upperSnakeTest(@RequestBody TestDto.UpperSnakeCase reqBody) {
        log.info("Req body : {}", reqBody.getTestValue());
        return new ResponseEntity<>(reqBody.getTestValue(), HttpStatus.OK);
    }

    @PostMapping("/lower")
    public ResponseEntity<String> lowerTest(@RequestBody TestDto.LowerCase reqBody) {
        log.info("Req body : {}", reqBody.getTestValue());
        return new ResponseEntity<>(reqBody.getTestValue(), HttpStatus.OK);
    }

    @PostMapping("/kebab")
    public ResponseEntity<String> kebabTest(@RequestBody TestDto.KebabCase reqBody) {
        log.info("Req body : {}", reqBody.getTestValue());
        return new ResponseEntity<>(reqBody.getTestValue(), HttpStatus.OK);
    }

    @PostMapping("lower-dot")
    public ResponseEntity<String> lowerDot(@RequestBody TestDto.LowerDotCase reqBody) {
        log.info("Req body : {}", reqBody.getTestValue());
        return new ResponseEntity<>(reqBody.getTestValue(), HttpStatus.OK);
    }
}
