package com.pda.practice.order;

import com.pda.practice.product.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@Slf4j
public class OrderRepository {

    private final Map<Integer, Order> orderTable;
    private int increment;

    public OrderRepository() {
        this.orderTable = new HashMap<>();
        this.increment = 1;
    }

    public Order create(Order order) {
        order.setId(increment++);
        orderTable.put(order.getId(), order);

        return order;
    }

}
