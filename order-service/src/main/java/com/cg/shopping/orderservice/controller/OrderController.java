package com.cg.shopping.orderservice.controller;

import com.cg.shopping.orderservice.client.Wallet;
import com.cg.shopping.orderservice.client.WalletRequest;
import com.cg.shopping.orderservice.client.WalletServiceClient;
import com.cg.shopping.orderservice.entity.ShippingAddress;
import com.cg.shopping.orderservice.entity.Order;
import com.cg.shopping.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final WalletServiceClient walletServiceClient;

    @PostMapping(value = "/placeOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.placeOrder(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> addOrderItems(@PathVariable String id){
        Order order = orderService.findById(id).get();
        return ResponseEntity.status(201).body(order);
    }


    @PostMapping(value = "/onlinePayment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> onlinePayment(@RequestBody Order order) {
        ResponseEntity<Wallet> byCustomerId = walletServiceClient.getByCustomerId(order.getCustomerId());
        Wallet body = byCustomerId.getBody();
        if (body.getWalletId() != 0) {
            WalletRequest walletRequest = WalletRequest
                    .builder()
                    .walletId(body.getWalletId())
                    .amount(order.getTotalPrice())
                    .transactionType("Withdraw")
                    .build();
            ResponseEntity<Void> payMoney = walletServiceClient.payMoney(walletRequest);
            if (payMoney.getStatusCode() == HttpStatus.OK) {
                changeOrderStatus("PAID", order.getOrderId());
            } else {
                changeOrderStatus("PAYMENT-ISSUE", order.getOrderId());
            }
        } else {
            changeOrderStatus("WALLET-NOT-CONFIGURED-FOR-CUSTOMER", order.getOrderId());
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.getOrderById(order.getOrderId()).get());
    }

    @GetMapping(value = "/changeStatus/{status}/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changeOrderStatus(@RequestParam (value = "status") String status,
                                                  @RequestParam (value = "orderId") int orderId) {
        orderService.changeOrderStatus(orderId, status);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping(value = "/allOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> getAllOrder() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping(value = "/findMaxByOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> getLatestOrder() {
        return ResponseEntity.ok(orderService.getLatestOrder());
    }


    @GetMapping(value = "/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> getByCustomerId(@PathVariable("customerId") String customerId ) {
        return ResponseEntity.ok(orderService.getOrderByCustomerId(customerId));
    }

    @DeleteMapping(value = "delete/{orderId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateOrder(@RequestParam(value = "orderId") int orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
