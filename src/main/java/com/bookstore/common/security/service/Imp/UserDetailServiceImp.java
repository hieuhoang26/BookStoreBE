package com.bookstore.common.security.service.Imp;

import com.bookstore.common.model.User;
import com.bookstore.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserDetailServiceImp implements UserDetailsService {
    final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username)  {
        User user = userService.getUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        user.getRoles().forEach(userHasRole -> {
//            authorities.add(new SimpleGrantedAuthority(userHasRole.getRole().getName()));
//        });
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(), user.getPassword(), authorities);
        return user;
    }
}
