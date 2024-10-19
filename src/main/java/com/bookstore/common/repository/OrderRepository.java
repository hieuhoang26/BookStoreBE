package com.bookstore.common.repository;

import com.bookstore.common.model.Order;
import com.bookstore.common.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findOrderById(Integer id);
    List<Order> findByShop_Id(Integer shopId);
    @Query("select oi from OrderItem oi where oi.order.Id = :orderId")
    List<OrderItem> findOrderItemsByOrderId(Integer orderId);
}
