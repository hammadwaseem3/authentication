package com.example.authentication.demo.domain.repository;

import com.example.authentication.demo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String username);

    boolean existsByEmail(String email);
}
