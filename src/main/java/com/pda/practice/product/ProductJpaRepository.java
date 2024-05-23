package com.pda.practice.product;

import com.pda.practice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<Product, Integer> {

    Optional<List<Product>> findAllByCategoryId(int categoryId);
}
