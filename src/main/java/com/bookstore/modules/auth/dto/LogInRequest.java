package com.bookstore.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogInRequest {
    @NotBlank
    @Length(max = 50, min = 5)
    String username;

    @NotBlank
    String password;
}
