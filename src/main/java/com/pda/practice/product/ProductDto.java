package com.pda.practice.product;

import lombok.*;

public class ProductDto {

//    @NoArgsConstructor
    @Getter
    @Setter
    public static class RegisterReq {
        private String name;
        private String desc;
        private String summary;
        private int price;
        private int categoryId;

        public RegisterReq() {
            System.out.println("h");
        }
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class ModifyReq {
        private String name;
        private String desc;
        private String summary;
        private int price;
    }

    @Builder
    @Getter
    public static class Info {
        private int id;

        private int categoryId;

        private String name;

        private String desc;

        private String summary;

        private int price;
    }
}
