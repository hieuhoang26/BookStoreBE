package com.bookstore.common.service;

import com.bookstore.common.model.Order;
import com.bookstore.common.model.OrderItem;
import com.bookstore.modules.order.dto.ListOrderResponse;
import com.bookstore.modules.order.dto.request.OrderRequest;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface OrderService {
    void save(Order order);
    void delete(Order order);
    Order getOrderById(Integer id);
    List<ListOrderResponse> getOrderByShopId(Integer shopId);
    void createOrder(OrderRequest request);
    List<OrderItem> getAllOrderItemByOrderId(Integer orderId);
}
