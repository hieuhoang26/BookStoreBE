package com.bookstore.common.service;

import com.bookstore.common.dto.response.ResponseData;
import com.bookstore.modules.auth.dto.LogInRequest;
import com.bookstore.modules.auth.dto.SignUpRequest;
import com.bookstore.modules.auth.dto.TokenRefreshResponse;
import com.bookstore.modules.auth.dto.TokenResponse;

public interface AuthService {
    TokenResponse login(LogInRequest logInRequest);
    ResponseData signUp(SignUpRequest signUpRequest);
    TokenRefreshResponse refresh(String refreshToken);

}
