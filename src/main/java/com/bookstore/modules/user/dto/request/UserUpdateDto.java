package com.bookstore.modules.user.dto.request;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDto implements Serializable {
    String username;
    String password;
    String phoneNumber;
    String email;
}
