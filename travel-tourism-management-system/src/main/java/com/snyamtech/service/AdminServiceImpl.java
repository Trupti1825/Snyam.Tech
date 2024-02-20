package com.snyamtech.service;

import com.snyamtech.dao.AdminDaoImpl;
import com.snyamtech.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl {

    @Autowired
    AdminDaoImpl adminDaoImpl;

    public Admin saveData(Admin admin){
        return adminDaoImpl.saveData(admin);
    }

    public List<Admin> getAllData(){
        return adminDaoImpl.getAllData();
    }

    public Admin updateData(Admin admin){
        return adminDaoImpl.updateData(admin);
    }

    public void deleteData(long adminId){
        adminDaoImpl.deleteData(adminId);
    }


}
