package com.bookstore.common.service.serviceImp;

import com.bookstore.common.model.*;
import com.bookstore.common.repository.OrderRepository;
import com.bookstore.common.service.BookService;
import com.bookstore.common.service.OrderService;
import com.bookstore.common.service.ShopService;
import com.bookstore.common.service.UserService;
import com.bookstore.common.util.OrderStatus;
import com.bookstore.modules.order.dto.ListOrderResponse;
import com.bookstore.modules.order.dto.OrderItemDTO;
import com.bookstore.modules.order.dto.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<ListOrderResponse> getOrderByShopId(Integer shopId) {
        return orderRepository.findByShop_Id(shopId).stream()
                .map(order -> new ListOrderResponse(
                        order.getId(),
                        order.getName(),
                        order.getPhone(),
                        order.getShoppingAddress(),
                        order.getTotalPrice(),
                        order.getOrderStatus(),
                        order.getOrderItems().stream()
                                .map(item -> new OrderItemDTO(
                                        item.getId(),
                                        item.getBook().getTitle(),
                                        getFirstBookImageUrl(item.getBook()),
                                        item.getQuantity()
                                )).collect(Collectors.toSet())
                )).collect(Collectors.toList());
    }
    private String getFirstBookImageUrl(Book book) {
        if (book.getBookImages() != null && !book.getBookImages().isEmpty()) {
            return book.getBookImages().iterator().next().getImagePath();
        }
        return null;  // Return null or a default image URL if no images are present
    }

    @Override
    public void createOrder(OrderRequest request) {
        User user = userService.getUserById(request.getUserId());

        // Group items by shopId
        Map<Integer, List<OrderRequest.Item>> itemsByShop = request.getOrderItems().stream()
                .collect(Collectors.groupingBy(OrderRequest.Item::getShopId));

        itemsByShop.forEach((shopId, items) -> {
            Shop shop = shopService.getShopById(shopId); // Get the shop by shopId

            Order order = Order.builder()
//                    .totalPrice(items.stream().mapToDouble(item -> {
//                        Book book = bookService.getBookById(item.getBookId());
//                        return book.getPrice() * item.getQuantity();
//                    }).sum())
                    .totalPrice(request.getTotalPrice())

                    .name(user.getUsername())
                    .phone(user.getPhoneNumber())
                    .shoppingAddress(request.getAddress())
//                    to processing
                    .orderStatus(OrderStatus.fromCode(request.getStatusOrder()))
                    .user(user)
                    .shop(shop)
                    .build();

            // Add order items to the order
            items.forEach(item -> {
                Book book = bookService.getBookById(item.getBookId());
                OrderItem orderItem = new OrderItem(book, item.getQuantity());
                order.addOrderItem(orderItem); // Associate order item with the order
            });

            // Save each order
            orderRepository.save(order);
        });
    }

    @Override
    public List<OrderItem> getAllOrderItemByOrderId(Integer orderId) {
        return orderRepository.findOrderItemsByOrderId(orderId);
    }
}
