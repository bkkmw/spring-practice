package com.pda.practice.order;

import com.pda.practice.entity.Product;
import com.pda.practice.entity.User;
import com.pda.practice.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Order {

    private int id;
//    private int productId; - SQL
    private Product product; // Domain
    private User user;
    private int quantity;

    @NoArgsConstructor
    @Setter
    @Getter
    public static class RegisterReq {
        private int productId;
        private String userId;
        private int quantity;
    }
}
