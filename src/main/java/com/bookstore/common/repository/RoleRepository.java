package com.bookstore.common.repository;

import com.bookstore.common.model.Auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository  extends JpaRepository<Role,Integer> {
    @Query(value = "select r from Role r inner join UserHasRole ur on r.Id=ur.user.Id where ur.user.Id=:userId")
    List<Role> getAllByUserId(Integer userId);
    Role findByName(String name);
}
