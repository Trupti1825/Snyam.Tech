package com.snyamtech.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "admin",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "admin_email")})
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;

    @Column(name = "admin_name")
    private String adminName;

    @Column(name = "admin_contact_number", length=10,unique=true)
    private Long adminContactNumber;

    @Column(name = "admin_address")
    private String adminAddress;

    @Column(name = "admin_email")
    private String adminEmail;

    @Column(name = "admin_password")
    private String adminPassword;

    @Column(name = "token")
    private String token;


}
