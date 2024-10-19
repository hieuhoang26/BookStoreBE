package com.bookstore.modules.auth.controller;

import com.bookstore.common.dto.response.ResponseData;
import com.bookstore.common.service.serviceImp.AuthServiceImp;
import com.bookstore.common.util.Uri;
import com.bookstore.modules.auth.dto.LogInRequest;
import com.bookstore.modules.auth.dto.TokenRefreshResponse;
import com.bookstore.modules.auth.dto.TokenResponse;
import com.bookstore.modules.auth.dto.SignUpRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    final AuthServiceImp authService;

    @PostMapping(value = Uri.LOGIN)
    public ResponseEntity<TokenResponse> Login(@Valid @RequestBody LogInRequest login) {
        return new ResponseEntity<>(authService.login(login), HttpStatus.OK);
    }

    @PostMapping(value = {Uri.SIGNUP})
    public ResponseEntity<ResponseData> SignUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return new ResponseEntity<>(authService.signUp(signUpRequest), HttpStatus.OK);
    }
    @PostMapping(value = {Uri.LOGOUT})
    public ResponseEntity<ResponseData> Logout() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = {Uri.REFRESH})
    public ResponseEntity<?> Refresh(HttpServletRequest  refreshToken) {
        TokenRefreshResponse data = authService.refresh(refreshToken);
        if (data != null) {
            return new ResponseEntity<>(data, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid or expired refresh token", HttpStatus.FORBIDDEN);
        }
    }

}
