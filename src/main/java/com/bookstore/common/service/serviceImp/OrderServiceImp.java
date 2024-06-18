package com.bookstore.common.service.serviceImp;

import com.bookstore.common.model.*;
import com.bookstore.common.repository.OrderRepository;
import com.bookstore.common.service.BookService;
import com.bookstore.common.service.OrderService;
import com.bookstore.common.service.ShopService;
import com.bookstore.common.service.UserService;
import com.bookstore.modules.order.dto.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {
    final OrderRepository orderRepository;
    final UserService userService;
    final ShopService shopService;
    final BookService bookService;

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public Order getOrderById(Integer id) {
        return orderRepository.findOrderById(id);
    }

    @Override
    public void createOrder(OrderRequest request) {
        Order order = Order.builder()
                .totalPrice(request.getTotalPrice())
                .orderDate(LocalDate.now())
                .shoppingAddress(request.getAddress())
                .orderStatus("test")
                .isConfirm(false)
                .isEvaluate(false)
                .build();
        User user = userService.getUserById(request.getUserId());
        Shop shop = shopService.getShopById(request.getShopId());
        order.setUser(user);
        order.setShop(shop);
        request.getOrderItems().forEach(item -> {
            Book book = bookService.getBookById(item.getBookId());
            OrderItem orderItem = new OrderItem(book, item.getQuantity());
            order.addOrderItem(orderItem);
        });
        orderRepository.save(order);
    }

    @Override
    public List<OrderItem> getAllOrderItemByOrderId(Integer orderId) {
        return orderRepository.findOrderItemsByOrderId(orderId);
    }
}
