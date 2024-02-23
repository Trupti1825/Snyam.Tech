package com.snyamtech.controller;

import com.snyamtech.model.Hotel;
import com.snyamtech.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    HotelRepository hotelRepository;

    /*-------------THIS METHOD IS USE FOR HOTEL SIGNUP----------------*/
    @PostMapping("/signup")
    public ResponseEntity<String> registrationHotel(@RequestBody Hotel hotel) {

        if (hotelRepository.existsByHotelName(hotel.getHotelName())) {
            return ResponseEntity.badRequest().body(" Hotel Name id already taken!");
        }
        hotelRepository.save(hotel);
        return ResponseEntity.ok(" Hotel Name registration successfully!");
    }

    /*-------------THIS METHOD IS USE FOR HOTEL SIGNIN----------------*/
    @PostMapping("/signin")
    public ResponseEntity<String> signInHotel(@RequestBody Hotel hotelLogin) {
        Hotel hotel= hotelRepository.findByHotelName(hotelLogin.getHotelName());
        if (hotel != null && hotel.getHotelPassword().equals(hotelLogin.getHotelPassword())) {
            return ResponseEntity.ok(" Hotel LogIn Successfully");
        } else {
            return ResponseEntity.badRequest().body(" Invalid Name or Password!");
        }
    }

    /*-------------THIS METHOD IS HOTEL FOR SAVE DATA----------------*/
    @PostMapping("/savedata")
    public ResponseEntity<Hotel> saveData(@RequestBody Hotel hotel){
        return ResponseEntity.ok(hotelRepository.save(hotel));
    }

    /*-------------THIS METHOD IS USE FOR HOTEL FORGET PASSWORD----------------*/
    @PostMapping("/forgetpassword")
    public ResponseEntity<String> fogetPassword(@RequestParam String hoteName){
        Hotel hotel = hotelRepository.findByHotelName(hoteName);
        if (hotel == null){
            return ResponseEntity.badRequest().body("No hotel found with the provided hotel name!");
        }
        // Generate a unique reset token and associate it with the hotel name
        String resetToken = generateResetToken();
        hotel.setHotelToken(resetToken);
        hotelRepository.save(hotel);
        return ResponseEntity.ok("Reset token generated successfully: " + resetToken);
    }

    /*-------------THIS METHOD IS USE FOR HOTEL RESET PASSWORD----------------*/
    @PostMapping("/resetpassword")
    public ResponseEntity<String> resetPassword(@RequestParam String hotelName,
                                                @RequestParam String hotelToken,
                                                @RequestParam String hotelPassword){
        Hotel hotel = hotelRepository.findByHotelNameAndToken(hotelName, hotelToken);
        if (hotel == null){
            return ResponseEntity.badRequest().body("Invalid reset token!");
        }
        hotel.setHotelPassword(hotelPassword);
        hotel.setHotelToken(hotelToken);
        hotelRepository.save(hotel);
        return ResponseEntity.ok("Hotel Password reset successfully!");
    }
    private String generateResetToken() {
        // Generate a unique reset token using UUID
        return UUID.randomUUID().toString();
    }

    /*-------------THIS METHOD IS USE FOR GET HOTEL BY ID----------------*/
    @GetMapping("/get/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long hotelId){
        // Retrieve the hptel by ID from the database
        Optional<Hotel> optionalHotelName = hotelRepository.findById(hotelId);
        if (optionalHotelName.isPresent()){
            Hotel hotel = optionalHotelName.get();
            return ResponseEntity.ok(hotel);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    /*-------------THIS METHOD IS USE FOR GET HOTEL LIST----------------*/
    @GetMapping("/list")
    public ResponseEntity<List<Hotel>> getAllHotels(){
        List<Hotel> hotels = hotelRepository.findAll();
        return  ResponseEntity.ok(hotels);
    }

    /*-------------THIS METHOD IS USE FOR UPDATE HOTEL----------------*/
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateHotelData(@PathVariable Long hotelId,
                                                  @RequestBody Hotel hotelUpdates){
        Hotel existingHotel = hotelRepository.findById(hotelId).orElse(null);
        if (existingHotel == null){
            return ResponseEntity.badRequest().body("Hotel not found with id: " + hotelId);
        }
        if (hotelUpdates.getHotelName() != null){
            existingHotel.setHotelName(hotelUpdates.getHotelName());
        }
        if (hotelUpdates.getHotelContactNumber() != null){
            existingHotel.setHotelContactNumber(hotelUpdates.getHotelContactNumber());
        }
        if (hotelUpdates.getHotelAddress() != null){
            existingHotel.setHotelAddress(hotelUpdates.getHotelAddress());
        }
        if (hotelUpdates.getHotelPassword() != null){
            existingHotel.setHotelPassword(hotelUpdates.getHotelPassword());
        }
        hotelRepository.save(existingHotel);
        return ResponseEntity.ok("Hotel Data updated successfully!");
    }

    /*-------------THIS METHOD IS USE FOR DELETE HOTEL----------------*/

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable Long hotelId) {
        if (hotelRepository.existsById(hotelId)) {
            hotelRepository.deleteById(hotelId);
            return ResponseEntity.ok("Hotel deleted successfully!");
        } else {
            return ResponseEntity.badRequest().body("Hotel not found with id: " + hotelId);
        }
    }

}
