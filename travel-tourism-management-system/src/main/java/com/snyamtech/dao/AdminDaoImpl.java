package com.snyamtech.dao;

import com.snyamtech.model.Admin;
import com.snyamtech.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminDaoImpl {

    @Autowired
    AdminRepository adminRepository;

    public Admin saveData(Admin admin){
        return adminRepository.save(admin);
    }

    public List<Admin> getAllData(){
        return adminRepository.findAll();
    }

    public Admin updateData(Admin admin){
        return adminRepository.save(admin);
    }

    public void deleteData(long adminId){
        adminRepository.deleteById(adminId);
    }

}
