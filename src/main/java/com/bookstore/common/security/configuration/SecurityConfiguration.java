package com.bookstore.common.security.configuration;

import com.bookstore.common.security.filter.PreFilter;
import com.bookstore.common.security.service.UserDetailServiceImp;
import com.bookstore.common.service.UserService;
//import io.jsonwebtoken.security.Password;
import com.bookstore.common.util.Uri;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {
    final UserDetailsService userDetailsService;
    final UserDetailServiceImp userDetailServiceImp;
    final UserService userService;
    final PreFilter preFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
//        set UserDetail
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
//        provider.setUserDetailsService(userService.userDeailServive());
//        provider.setUserDetailsService(userDetailServiceImp);
        provider.setUserDetailsService(userDetailsService);
        return provider;

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        // manage role
        return configuration.getAuthenticationManager();
        //return new ProviderManager(Collections.singletonList(authenticationProvider()));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // api whitelist and blacklist
        http.authorizeHttpRequests(request -> request
                .requestMatchers(Uri.LOGIN,Uri.SIGNUP).permitAll()
                .requestMatchers(HttpMethod.GET, Uri.USER).hasAnyRole("User", "Shop", "Admin") // need prefix ROLE_
                .requestMatchers(Uri.SHOP).hasAnyRole( "Shop", "Admin")
                .requestMatchers(Uri.BOOK_DETAIL,Uri.BOOK_SHOP, Uri.BOOK_PAGE, Uri.BOOKS_FILTER).permitAll()
                .requestMatchers(Uri.CATEGORY).permitAll()
                .requestMatchers(HttpMethod.GET,Uri.SHOP,Uri.SHOP_BOOK,Uri.SHOP_DETAIL).permitAll()
//                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated());

        // STATELESS - not save token in session
        http.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(preFilter, UsernamePasswordAuthenticationFilter.class) // before - at - after
        ;

        return http.build();


    }
    @Bean
    public WebSecurityCustomizer ignoreResources() {
        // url trên giao diện browser
        return (webSecurity) -> webSecurity
                .ignoring()
                .requestMatchers("/actuator/**", "/v3/**", "/webjars/**", "/swagger-ui*/*swagger-initializer.js", "/swagger-ui*/**");
    }


}
