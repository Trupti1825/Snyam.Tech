package com.snyamtech.controller;

import com.snyamtech.model.User;
import com.snyamtech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;


    /*-------------THIS METHOD IS USE FOR USER SIGNUP----------------*/
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already taken!");
        }
        userRepository.save(user);
        return ResponseEntity.ok("User register successfully!");
    }


    /*-------------THIS METHOD IS USE FOR USER SIGNIN----------------*/
    @PostMapping("/signin")
    public ResponseEntity<String> signInUser(@RequestBody User loginuser) {
        User user = userRepository.findByUserNameOrEmail(loginuser.getUserName(), loginuser.getEmail());
        if (user != null && user.getPassword().equals(loginuser.getPassword())) {
            return ResponseEntity.ok("User Login Successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid email or password!");
        }
    }


    /*-------------THIS METHOD IS USE FOR FORGET PASSWORD----------------*/

    @PostMapping("/forgetpassword")
    public ResponseEntity<String> forgetPassword(@RequestParam String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("No user found with the provided email!");
        }
        // Generate a unique reset token and associate it with the user
        String resetToken = generateResetToken();
        user.setToken(resetToken);
        userRepository.save(user);
        return ResponseEntity.ok("Reset token generated successfully: " + resetToken);
    }


    /*-------------THIS METHOD IS USE FOR RESET PASSWORD----------------*/
    @PostMapping("/resetpassword")
    public ResponseEntity<String> resetPassword(@RequestParam String email,
                                                @RequestParam String token,
                                                @RequestParam String password) {
        User user = userRepository.findByEmailAndToken(email, token);
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid reset token!");
        }
        user.setPassword(password);
        user.setToken(null);
        userRepository.save(user);
        return ResponseEntity.ok("Password reset successfully!");
    }
    private String generateResetToken() {
        // Generate a unique reset token using UUID
        return UUID.randomUUID().toString();
    }


    /*-------------THIS METHOD IS USE FOR GET USER BY ID----------------*/
    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        // Retrieve the user by ID from the database
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*-------------THIS METHOD IS USE FOR GET USER LIST----------------*/
    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }


    /*-------------THIS METHOD IS USE FOR UPDATE USER----------------*/
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,
                                             @RequestBody User userUpdates) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return ResponseEntity.badRequest().body("User not found with id: " + id);
        }
        if (userUpdates.getUserName() != null) {
            existingUser.setUserName(userUpdates.getUserName());
        }
        if (userUpdates.getAddress() != null) {
            existingUser.setAddress(userUpdates.getAddress());
        }
        if (userUpdates.getEmail() != null) {
            existingUser.setEmail(userUpdates.getEmail());
        }
        if (userUpdates.getPassword() != null) {
            existingUser.setPassword(userUpdates.getPassword());
        }
        if (userUpdates.getContact() != null) {
            existingUser.setContact(userUpdates.getContact());
        }
        if (userUpdates.getAge() != null) {
            existingUser.setAge(userUpdates.getAge());
        }
        if (userUpdates.getGender() != null) {
            existingUser.setGender(userUpdates.getGender());
        }
        userRepository.save(existingUser);

        return ResponseEntity.ok("User updated successfully!");
    }



    /*-------------THIS METHOD IS USE FOR DELETE USER----------------*/
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully!");
        } else {
            return ResponseEntity.badRequest().body("User not found with id: " + id);
        }
    }


}



