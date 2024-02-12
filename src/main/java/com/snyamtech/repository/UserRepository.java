package com.snyamtech.repository;

import com.snyamtech.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    User findByUserNameOrEmail(String userName, String email);
}
