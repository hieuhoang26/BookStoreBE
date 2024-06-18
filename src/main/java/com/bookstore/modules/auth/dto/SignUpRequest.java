package com.bookstore.modules.auth.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignUpRequest {
    @NotBlank
    @Length(max = 50, min = 5)
    String username;

    @NotBlank
    @Length(max = 1000, min = 8)
    String password;

    @NotBlank
    @Email
    String email;

    @NotBlank
    String phoneNumber;




}
