package com.bookstore.common.service;

import com.bookstore.common.model.User;
import com.bookstore.modules.order.dto.OrderDto;
import com.bookstore.modules.user.dto.UserDto;
import com.bookstore.modules.user.dto.request.UserUpdateDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
//    UserDetailsService userDeailServive();
    void saveUser(User user);
    User getUserById(Integer id);
    User getUserByUserName(String username);
    Boolean isExistUserName(String username);
    User getUserByEmail(String username);
    void updateUser(Integer id, UserUpdateDto userUpdateDto);
    void deleteUser(Integer id);
    List<OrderDto> getAllOrderByUserId(Integer id);


}
