package com.bookstore.modules.user.dto.request;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateOfBirth;
    MultipartFile avatar;
}
