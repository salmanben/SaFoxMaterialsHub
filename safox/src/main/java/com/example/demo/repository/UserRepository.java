package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.User;
import com.example.demo.model.Role;


public interface UserRepository extends JpaRepository<User, Integer> {

    Page<User> findByRole(Role role, Pageable page);
    User findByEmail(String username);

    User findByPhone(String phone);
}
