package com.snyamtech.controller;

import com.snyamtech.model.Admin;
import com.snyamtech.repository.AdminRepository;
import com.snyamtech.service.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AdminServiceImpl adminServiceImpl;

    /*-------------THIS METHOD IS USE FOR ADMIN SIGNUP----------------*/
    @PostMapping("/signup")
    public ResponseEntity<String> registrationAdmin(@RequestBody Admin admin) {

        if (adminRepository.existsByAdminEmail(admin.getAdminEmail())) {
            return ResponseEntity.badRequest().body(" Admin Email id already taken!");
        }
        adminRepository.save(admin);
        return ResponseEntity.ok(" Admin registration successfully!");
    }

    /*-------------THIS METHOD IS USE FOR ADMIN SIGNIN----------------*/
    @PostMapping("/signin")
    public ResponseEntity<String> signInAdmin(@RequestBody Admin adminLogin) {
        Admin admin = adminRepository.findByAdminNameOrAdminEmail(adminLogin.getAdminName(), adminLogin.getAdminEmail());
        if (admin != null && admin.getAdminPassword().equals(adminLogin.getAdminPassword())) {
            return ResponseEntity.ok(" Admin LogIn Successfully");
        } else {
            return ResponseEntity.badRequest().body(" Invalid Email id or Password!");
        }
    }

    /*-------------THIS METHOD IS ADMIN FOR SAVE DATA----------------*/
    @PostMapping("/savedata")
    public ResponseEntity<Admin> saveData(@RequestBody Admin admin){
        return ResponseEntity.ok(adminServiceImpl.saveData(admin));
    }

    /*-------------THIS METHOD IS USE FOR ADMIN FORGET PASSWORD----------------*/
    @PostMapping("/forgetpassword")
    public ResponseEntity<String> fogetPassword(@RequestParam String adminEmail){
        Admin admin = adminRepository.findByEmail(adminEmail);
        if (admin == null){
            return ResponseEntity.badRequest().body("No admin found with the provided email!");
        }
        // Generate a unique reset token and associate it with the admin
        String resetToken = generateResetToken();
        admin.setToken(resetToken);
        adminRepository.save(admin);
        return ResponseEntity.ok("Reset token generated successfully: " + resetToken);
    }

    /*-------------THIS METHOD IS USE FOR ADMIN RESET PASSWORD----------------*/
    @PostMapping("/resetpassword")
    public ResponseEntity<String> resetPassword(@RequestParam String adminEmail,
                                                @RequestParam String token,
                                                @RequestParam String password){
        Admin admin = adminRepository.findByEmailAndToken(adminEmail, token);
        if (admin == null){
            return ResponseEntity.badRequest().body("Invalid reset token!");
        }
        admin.setAdminPassword(password);
        admin.setToken(token);
        adminRepository.save(admin);
        return ResponseEntity.ok("Password reset successfully!");
    }
    private String generateResetToken() {
        // Generate a unique reset token using UUID
        return UUID.randomUUID().toString();
    }

    /*-------------THIS METHOD IS USE FOR GET ADMIN BY ID----------------*/
    @GetMapping("/get/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long adminId){
        // Retrieve the admin by ID from the database
        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
        if (optionalAdmin.isPresent()){
            Admin admin = optionalAdmin.get();
            return ResponseEntity.ok(admin);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    /*-------------THIS METHOD IS USE FOR GET ADMIN LIST----------------*/
    @GetMapping("/list")
    public ResponseEntity<List<Admin>> getAllAdmins(){
        List<Admin> admins = adminRepository.findAll();
        return  ResponseEntity.ok(admins);
    }

    /*-------------THIS METHOD IS USE FOR UPDATE ADMIN----------------*/
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateAdminData(@PathVariable Long adminId,
                                                  @RequestBody Admin adminUpdates){
        Admin existingAdmin = adminRepository.findById(adminId).orElse(null);
        if (existingAdmin == null){
            return ResponseEntity.badRequest().body("Admin not found with id: " + adminId);
        }
        if (adminUpdates.getAdminName() != null){
            existingAdmin.setAdminName(adminUpdates.getAdminName());
        }
        if (adminUpdates.getAdminContactNumber() != null){
            existingAdmin.setAdminContactNumber(adminUpdates.getAdminContactNumber());
        }
        if (adminUpdates.getAdminAddress() != null){
            existingAdmin.setAdminAddress(adminUpdates.getAdminAddress());
        }
        if (adminUpdates.getAdminEmail() != null){
            existingAdmin.setAdminEmail(adminUpdates.getAdminEmail());
        }
        if (adminUpdates.getAdminPassword() != null){
            existingAdmin.setAdminPassword(adminUpdates.getAdminPassword());
        }
        adminRepository.save(existingAdmin);
        return ResponseEntity.ok("Admin Data updated successfully!");
    }

    /*-------------THIS METHOD IS USE FOR DELETE USER----------------*/

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long adminId) {
        if (adminRepository.existsById(adminId)) {
            adminRepository.deleteById(adminId);
            return ResponseEntity.ok("Admin deleted successfully!");
        } else {
            return ResponseEntity.badRequest().body("Admin not found with id: " + adminId);
        }
    }

    /*-------------THIS METHOD IS USE FOR ADMIN GETALLDATA----------------*/
    @GetMapping("/getalldata")
    public ResponseEntity<List<Admin>> getAllData(){
        return ResponseEntity.ok(adminServiceImpl.getAllData());
    }

    /*-------------THIS METHOD IS USE FOR ADMIN UPDATEDATA----------------*/
    @PutMapping("/updatedata/{adminId}")
    public ResponseEntity<Admin> updateData(@PathVariable long adminId, @RequestBody Admin admin){
        return ResponseEntity.ok(adminServiceImpl.updateData(admin));
    }

    /*-------------THIS METHOD IS USE FOR ADMIN DELETEDATA----------------*/
    @DeleteMapping("/deletedata/{adminId}")
    public ResponseEntity<String> deleteData(@PathVariable long adminId){
        adminServiceImpl.deleteData(adminId);
        return ResponseEntity.ok(" Admin data Deleted Successfully!");
    }

}
