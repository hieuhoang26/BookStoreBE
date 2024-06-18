package com.bookstore.common.security.filter;


import com.bookstore.common.security.service.JwtService;
import com.bookstore.common.security.service.UserDetailServiceImp;
import com.bookstore.common.util.Uri;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class PreFilter extends OncePerRequestFilter {
    final UserDetailServiceImp userDetailServiceImp;
    final JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        log.info("Received token: {}", authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String jwt = authHeader.substring(7);
        final String userName = jwtService.extractUsername(jwt);
        if (StringUtils.isNotEmpty(userName) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailServiceImp.loadUserByUsername(userName);
            if (jwtService.isValid(jwt, userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestUri = request.getRequestURI();
        return requestUri.startsWith(Uri.LOGIN)
                ||  requestUri.startsWith(Uri.SIGNUP)
                ||  requestUri.startsWith(Uri.BOOK_SHOP)
                ||  requestUri.startsWith(Uri.BOOK_DETAIL)
                ||  requestUri.startsWith(Uri.BOOK_PAGE)
                ||  requestUri.startsWith(Uri.BOOKS_FILTER)
                ||  requestUri.startsWith(Uri.CATEGORY)
                ||  requestUri.startsWith(Uri.SHOP_BOOK)
                ||  requestUri.startsWith(Uri.SHOP +"/{id}");
//        return super.shouldNotFilter(request);
    }
}
