package com.pda.practice.user;

import com.pda.practice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByUserId(String userId);
}
