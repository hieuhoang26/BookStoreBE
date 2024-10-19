package com.bookstore.modules.order.controller;

import com.bookstore.common.dto.response.ResponseData;
import com.bookstore.common.dto.response.ResponseSuccess;
import com.bookstore.common.model.Book;
import com.bookstore.common.model.Order;
import com.bookstore.common.model.OrderItem;
import com.bookstore.common.service.OrderService;
import com.bookstore.common.util.OrderStatus;
import com.bookstore.common.util.Uri;
import com.bookstore.modules.cart.dto.CartRequest;
import com.bookstore.modules.order.dto.ListOrderResponse;
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
    @GetMapping(value = Uri.ORDER)
    public ResponseSuccess getOrderByShop(@RequestParam Integer shopId){
        List<ListOrderResponse> orders = orderService.getOrderByShopId(shopId);
        return new ResponseSuccess(HttpStatus.OK,"get order sucess",orders);
    }

    @PostMapping(value = Uri.ORDER)
    public ResponseData CreateOrder(@RequestBody OrderRequest request){
        orderService.createOrder(request);
        return new ResponseData(HttpStatus.CREATED.value(),"Create order sucess");
    }
    @PatchMapping(value = Uri.ORDER)
    public ResponseData UpdateStatusOrder(@RequestParam Integer orderId, @RequestParam Integer status){
        Order order = orderService.getOrderById(orderId);
        order.setOrderStatus(OrderStatus.fromCode(status));
        if(OrderStatus.fromCode(status)=="Successful"){
            List<OrderItem> orderItems = orderService.getAllOrderItemByOrderId(orderId);
            orderItems.stream().forEach(orderItem -> {
                Book book = orderItem.getBook();
                book.setSoldQuantity(book.getSoldQuantity() + 1);
                book.setCurrentQuantity(book.getCurrentQuantity()-1);
            });
        }
        orderService.save(order);
        return new ResponseData(HttpStatus.OK.value(),"Update status success");
    }
    @DeleteMapping(value = Uri.ORDER)
    public ResponseData DeleteOrder(@RequestParam Integer orderId){
        Order order = orderService.getOrderById(orderId);
       orderService.delete(order);
        return new ResponseData(HttpStatus.OK.value(),"Delete order sucess");
    }

}
