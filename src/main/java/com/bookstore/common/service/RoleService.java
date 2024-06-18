package com.bookstore.common.service;

import com.bookstore.common.model.Auth.Role;
import com.bookstore.common.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RoleService{
    Role getByName(String name);
}
