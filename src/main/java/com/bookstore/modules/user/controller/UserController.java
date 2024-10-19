package com.bookstore.modules.user.controller;

import com.bookstore.common.dto.response.ResponseData;
import com.bookstore.common.dto.response.ResponseError;
import com.bookstore.common.model.User;
import com.bookstore.common.service.UserService;
import com.bookstore.common.util.Uri;
import com.bookstore.modules.order.dto.OrderDto;
import com.bookstore.modules.user.dto.UserDto;
import com.bookstore.modules.user.dto.request.UserUpdateDto;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = Uri.USER + "/{id}")
    public ResponseData UserById(@PathVariable Integer id) {
       User user =  userService.getUserById(id);
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
//                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .dateOfBirth(user.getDateOfBirth())
                .avatar(user.getAvatar())
                .build();
        return new ResponseData(HttpStatus.OK.value(), "User Info",userDto );
    }

    @PutMapping(value = Uri.USER + "/{id}")
    public ResponseData<?> UpdateUser(@PathVariable Integer id, @ModelAttribute UserUpdateDto userUpdate) {
        userService.updateUser(id, userUpdate);
        return new ResponseData<>(HttpStatus.OK.value(), "update user success");
    }
    @DeleteMapping(value = Uri.USER + "/{id}")
    public ResponseData DeleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return new ResponseData(HttpStatus.OK.value(), "delete user success");
    }

    /* <------------------- Uri.USERS_ORDERS ------------------> */
    @GetMapping(value =Uri.USER_ORDER )
    public ResponseData GetOrderForUser(@RequestParam Integer id){
            List<OrderDto> rs = userService.getAllOrderByUserId(id);
            return new ResponseData(HttpStatus.OK.value(),"All Order of User ", rs);
    }


}
