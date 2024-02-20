package com.snyamtech.repository;

import com.snyamtech.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByAdminEmail(String adminEmail);
    Admin findByAdminNameOrAdminEmail(String adminName, String adminEmail);
    Admin findByEmail(String adminEmail);
    Admin findByEmailAndToken(String adminEmail, String token);
}
