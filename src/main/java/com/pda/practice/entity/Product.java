package com.pda.practice.entity;

import com.pda.practice.product.ProductDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    private int id;
    private int categoryId;
    private String name;
    private String description;
    private String summary;
    private int price;

    public void update(ProductDto.ModifyReq modifyReq) {
        this.name = modifyReq.getName() == null ? this.name : modifyReq.getName();
        this.description = modifyReq.getDesc() == null ? this.description : modifyReq.getDesc();
        this.summary = modifyReq.getSummary() == null ? this.summary : modifyReq.getSummary();
        this.price = modifyReq.getPrice() == 0 ? this.price : modifyReq.getPrice();
    }

}
