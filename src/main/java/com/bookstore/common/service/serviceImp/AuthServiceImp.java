package com.bookstore.common.service.serviceImp;


import com.bookstore.common.dto.response.ResponseData;
import com.bookstore.common.exception.ResourceNotFoundExcep;
import com.bookstore.common.model.Auth.Role;
import com.bookstore.common.model.User;
import com.bookstore.common.repository.UserRepository;
import com.bookstore.common.security.service.Imp.UserDetailServiceImp;
import com.bookstore.common.service.AuthService;
import com.bookstore.common.security.service.JwtService;
import com.bookstore.common.service.RoleService;
import com.bookstore.common.service.ShopService;
import com.bookstore.common.service.UserService;
import com.bookstore.modules.auth.dto.LogInRequest;
import com.bookstore.modules.auth.dto.TokenRefreshResponse;
import com.bookstore.modules.auth.dto.TokenResponse;
import com.bookstore.modules.auth.dto.SignUpRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.REFERER;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {
    final UserRepository userRepository;
    final AuthenticationManager authenticationManager;
    final PasswordEncoder passwordEncoder;
    final JwtService jwtService;
    final UserService userService;
    final ShopService shopService;
    final RoleService roleService;
    final UserDetailServiceImp userDetailServiceImp;


    @Override
    public TokenResponse login(LogInRequest logInRequest) {
        // xac thuc user
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(logInRequest.getUsername(), logInRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        List<String> roleNames = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        TokenResponse.TokenResponseBuilder responseBuilder = TokenResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(roleNames)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .message("Login success");

        // Check if the user has the "ROLE_SHOP" role and update the shopId if applicable
        if (roleNames.contains("ROLE_Shop")) {
            responseBuilder.shopId(user.getShop().getId());
        }
        else {
            responseBuilder.shopId(null);
        }

        // Build and return the response
        return responseBuilder.build();
    }

    @Override
    public ResponseData signUp(SignUpRequest signUpRequest) {
        if (userService.isExistUserName(signUpRequest.getUsername())) {
            return new ResponseData(HttpStatus.BAD_REQUEST.value(), "UserName is already taken!");
        }
        User user = new User(signUpRequest.getUsername(), passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getEmail(), signUpRequest.getPhoneNumber());
        Role role = roleService.getByName("ROLE_User");
//        UserHasRole userHasRole = new UserHasRole();
//        userHasRole.setUser(user);
//        userHasRole.setRole(role);
//        user.setRoles(Collections.singleton(userHasRole));
        user.addRole(role);
        userService.saveUser(user);
        return new ResponseData<>(HttpStatus.OK.value(), "Sign Up sucess");
    }



    @Override
    public TokenRefreshResponse refresh(HttpServletRequest request) {
        final String refreshToken = request.getHeader(REFERER);
        if (StringUtils.isBlank(refreshToken)) {
            throw new ResourceNotFoundExcep("Token must be not blank");
        }
        String username = jwtService.extractUsername(refreshToken);
        UserDetails user = userDetailServiceImp.loadUserByUsername(username);
        if (jwtService.isValid(refreshToken, user)) {
            String newToken = jwtService.generateToken(user);
            return TokenRefreshResponse.builder()
                    .tokenType("Bearer")
                    .accessToken(newToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            return null;
        }

    }

}
