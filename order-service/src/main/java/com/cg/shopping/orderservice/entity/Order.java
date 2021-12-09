package com.cg.shopping.orderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Order {

    @Id
    private String id;

    private int orderId;

    private LocalDate orderDate;
    private int customerId;
    private double amountPaid;
    private String modeOfPayment;
    private String orderStatus;
    private int quantity;
    private int cartId;

    @DBRef(db="address")
    private List<Address> addresses = new ArrayList<>();
}
