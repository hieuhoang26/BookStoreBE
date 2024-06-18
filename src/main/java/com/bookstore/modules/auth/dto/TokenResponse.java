package com.bookstore.modules.auth.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse implements Serializable {
    private Integer id;
    private String username;
    private List<String> roles;
    private String accessToken;
    private String refreshToken;

}
