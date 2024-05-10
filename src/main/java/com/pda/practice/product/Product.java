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

    public void update(Product.ModifyReq modifyReq) {
        this.name = modifyReq.getName() == null ? this.name : modifyReq.getName();
        this.desc = modifyReq.getDesc() == null ? this.desc : modifyReq.getDesc();
        this.summary = modifyReq.getSummary() == null ? this.summary : modifyReq.getSummary();
        this.price = modifyReq.getPrice() == 0 ? this.price : modifyReq.getPrice();
    }

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
