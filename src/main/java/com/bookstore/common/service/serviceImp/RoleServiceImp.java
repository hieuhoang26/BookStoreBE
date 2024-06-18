package com.bookstore.common.service.serviceImp;

import com.bookstore.common.model.Auth.Role;
import com.bookstore.common.repository.RoleRepository;
import com.bookstore.common.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {
    final RoleRepository roleRepository;
    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name);
    }
}
