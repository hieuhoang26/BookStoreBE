package com.bookstore.common.repository;

import com.bookstore.common.model.Order;
import com.bookstore.common.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsUserByUsername(String username);
    @Query("select o from Order o where o.user.Id = :id")
    List<Order> findOrderByUserId(Integer id);


}
