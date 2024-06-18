package com.bookstore.modules.user.mapper;

import com.bookstore.common.model.User;
import com.bookstore.modules.user.dto.UserDto;
import org.hibernate.annotations.Comments;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
