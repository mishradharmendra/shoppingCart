package com.cg.shopping.orderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
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
    private String _id;

    private int orderId;

    private String customerId;
    private List<OrderItem> orderItems;
    private ShippingAddress shippingAddress;
    private String paymentMethod;
    private String orderStatus;
    private Double itemsPrice = 0.0;
    private Double taxPrice = 0.0;
    private Double shippingPrice = 0.0;
    private Double totalPrice = 0.0;
    private Boolean isPaid = false;
    private Instant paidAt;
    private Boolean isDelivered = false;
    private Instant deliveredAt;
    @CreatedDate
    private Instant orderDate;
    @LastModifiedDate
    private Instant updatedAt;

    @Data
    public static class OrderItem {

        @MongoId
        String _id;
        String name;
        Integer qty;
        String image;
        Double price;
        String product;

        public OrderItem() {
            this._id = ObjectId.get().toHexString();
        }
    }
}
