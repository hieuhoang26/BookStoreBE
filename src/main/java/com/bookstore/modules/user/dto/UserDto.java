package com.bookstore.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {
    Integer id;
    String username;
    String password;
    String phoneNumber;
    String email;
}
