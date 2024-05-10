package com.pda.practice.product;

import lombok.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class Product {

    private int id;
    private int categoryId;
    private String name;
    private String desc;
    private String summary;
    private int price;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class RegisterReq {
        private String name;
        private String desc;
        private String summary;
        private int price;
        private int categoryId;

    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Info {
        private int id;

        private int categoryId;

        private String name;

        private String desc;

        private String summary;

        private int price;
    }
}
