package com.pda.practice.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

public class UserDto {

    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class SignupReqDto {
        private String userId;
        @NotBlank(message = "Password cannot be empty string")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()._-])[a-zA-Z0-9!@#$%^&*()._-]{8,20}$", message = "Invalid password")
        private String password;
        @NotEmpty(message = "Name cannot be empty string")
        private String name;
        @NotEmpty(message = "Email cannot not be empty string")
        @Email(message = "Invalid email format")
        private String email;
        @Pattern(regexp = "^((0)[1-9][0-9]{1,2})-?[0-9]{3,4}-?[0-9]{4}$", message = "Invalid phone number format")
        private String contact;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class LoginReqDto {
        private String userId;
        private String password;
    }

    @Builder
    @Getter
    public static class LoginRespDto {
        private int id;
        private String name;
        private String email;
        private String contact;
        private String accessToken;
    }
}
