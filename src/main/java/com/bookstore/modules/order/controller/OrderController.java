package com.bookstore.modules.order.controller;

import com.bookstore.common.dto.response.ResponseData;
import com.bookstore.common.model.Book;
import com.bookstore.common.model.Order;
import com.bookstore.common.model.OrderItem;
import com.bookstore.common.service.OrderService;
import com.bookstore.common.util.Uri;
import com.bookstore.modules.order.dto.request.OrderItemRequest;
import com.bookstore.modules.order.dto.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    final OrderService orderService;

    @PostMapping(value = Uri.ORDER)
    public ResponseData CreateOrder(@RequestBody OrderRequest request){
        orderService.createOrder(request);
        return new ResponseData(HttpStatus.CREATED.value(),"Create order sucess");
    }
    @PutMapping(value = Uri.ORDER)
    public ResponseData UpdateStatusOrder(@RequestParam Integer orderId, @RequestBody String status){
        Order order = orderService.getOrderById(orderId);
        order.setOrderStatus(status);
        orderService.save(order);
        return new ResponseData(HttpStatus.OK.value(),"Update status success");
    }
    @DeleteMapping(value = Uri.ORDER)
    public ResponseData DeleteOrder(@RequestParam Integer orderId){
        Order order = orderService.getOrderById(orderId);
       orderService.delete(order);
        return new ResponseData(HttpStatus.OK.value(),"Delete order sucess");
    }
    @GetMapping(value = {Uri.ORDER_ITEM})
    public ResponseData RetrieveAllOrderItemsForOrder(@RequestParam Integer orderId){
        List<OrderItem> orderItems = orderService.getAllOrderItemByOrderId(orderId);
        List<OrderItemRequest> rs = orderItems.stream().map(item -> {
            return OrderItemRequest.builder()
                    .bookId(item.getId())
                    .quantity(item.getQuantity())
                    .build();
        }).collect(Collectors.toList());
        return new ResponseData(HttpStatus.OK.value(),"Delete order sucess", rs);
    }
    @PutMapping(value = {Uri.ORDER_CONFIRM})
    public ResponseEntity<?> ConfirmOrderSuccess(@RequestParam Integer orderId){
        List<OrderItem> orderItems = orderService.getAllOrderItemByOrderId(orderId);
        orderItems.stream().forEach(orderItem -> {
            Book book = orderItem.getBook();
            book.setSoldQuantity(book.getSoldQuantity() + 1);
        });
        Order order = orderService.getOrderById(orderId);
        order.setIsConfirm(true);
        orderService.save(order);
        return new ResponseEntity(HttpStatus.OK);
    }
}
