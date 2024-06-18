package com.bookstore.common.service.serviceImp;

import com.bookstore.common.exception.ResourceNotFoundExcep;
import com.bookstore.common.model.Order;
import com.bookstore.common.model.User;
import com.bookstore.common.repository.UserRepository;
import com.bookstore.common.service.UserService;
import com.bookstore.modules.order.dto.OrderDto;
import com.bookstore.modules.user.dto.UserDto;
import com.bookstore.modules.user.dto.request.UserUpdateDto;
import com.bookstore.modules.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor

public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

//    @Override
//    public UserDetailsService userDeailServive() {
//        return username -> userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
//    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserById(Integer id) {
        User user = userRepository.findById(Long.valueOf(id)).orElse(null);
        return user;
    }

    @Override
    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public Boolean isExistUserName(String username) {
        return userRepository.existsUserByUsername(username);
    }

    @Override
    public User getUserByEmail(String username) {
        return  userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void updateUser(Integer id,UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(Long.valueOf(id)).orElse(null);
        user.setUsername(userUpdateDto.getUsername());
        user.setPassword(userUpdateDto.getPassword());
        user.setEmail(userUpdateDto.getEmail());
        user.setPhoneNumber(userUpdateDto.getPhoneNumber());
        userRepository.save(user);

        log.info("User updated successfully");
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(Long.valueOf(id));
        log.info("User delete successfully");

    }

    @Override
    public List<OrderDto> getAllOrderByUserId(Integer id) {
        List<Order> orders = userRepository.findOrderByUserId(id);
        List<OrderDto> rs = orders.stream().map(
            order -> OrderDto.builder()
                    .id(order.getId())
                    .date(order.getOrderDate())
                    .totalPrice(order.getTotalPrice())
                    .orderStatus(order.getOrderStatus())
                    .DeliveryAddress(order.getShoppingAddress())
                    .isConfirm(order.getIsConfirm())
                    .isEvaluate(order.getIsEvaluate())
                    .build()
        ).collect(Collectors.toList());
        return rs;
    }
}
