package com.bookstore.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {
    Integer id;
    String username;
//    String password;
    String phoneNumber;
    String email;
    Date dateOfBirth;
    String avatar;
}
