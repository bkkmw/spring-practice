package com.pda.practice.test;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class TestDto {

    @NoArgsConstructor
    @Getter
    @Setter
    @JsonNaming(value = PropertyNamingStrategies.LowerCamelCaseStrategy.class)
    public static class LowerCamelCase {
        private String testValue;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
    public static class UpperCamelCase {
        private String testValue;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SnakeCase {
        private String testValue;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @JsonNaming(value = PropertyNamingStrategies.UpperSnakeCaseStrategy.class)
    public static class UpperSnakeCase {
        private String testValue;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @JsonNaming(value = PropertyNamingStrategies.LowerCaseStrategy.class)
    public static class LowerCase {
        private String testValue;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @JsonNaming(value = PropertyNamingStrategies.KebabCaseStrategy.class)
    public static class KebabCase {
        private String testValue;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @JsonNaming(value = PropertyNamingStrategies.LowerDotCaseStrategy.class)
    public static class LowerDotCase {
        private String testValue;
    }

}
