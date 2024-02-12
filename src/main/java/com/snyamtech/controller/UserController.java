package com.snyamtech.controller;

import com.snyamtech.model.User;
import com.snyamtech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;


    /*-------------THIS METHOD IS USE FOR USER SIGNUP----------------*/
    @PostMapping("/signup")
    public ResponseEntity<String>registerUser(@RequestBody User user){

        if ( userRepository.existsByEmail(user.getEmail())){
            return ResponseEntity.badRequest().body("Email is already taken!");
        }
        userRepository.save(user);
        return ResponseEntity.ok("User register successfully!");
    }



   /*-------------THIS METHOD IS USE FOR USER SIGNIN----------------*/
    @PostMapping("/signin")
    public ResponseEntity<String>signInUser(@RequestBody User loginuser){
        User user = userRepository.findByUserNameOrEmail(loginuser.getUserName(),loginuser.getEmail());
        if (user != null && user.getPassword().equals(loginuser.getPassword())){
            return ResponseEntity.ok("User Login Successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid email or password!");
        }
    }




}
