package com.snyamtech.controller;


import com.snyamtech.model.Booking;
import com.snyamtech.repository.BookingRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/booking")
public class BookingController {


    @Autowired
    private BookingRepository bookingRepository;
    /*-------------THIS METHOD IS USE FOR BOOKING ----------------*/

    @PostMapping("/bookings")
   public ResponseEntity<Booking> setBookingId(@RequestBody Booking booking){
        Booking setBooking = bookingRepository.save(booking);
        return ResponseEntity.ok(booking);
   }



    @GetMapping("/get/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id){
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if(optionalBooking.isPresent()){
            Booking booking = optionalBooking.get();
            return ResponseEntity.ok(booking);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    //Build Get All BOOKINGS REST API
    @GetMapping("/list")
    public ResponseEntity<List<Booking>> getAllBookings(){
        List<Booking> bookings = bookingRepository.findAll();
        return ResponseEntity.ok(bookings);
    }

    @PatchMapping("/updates/{id}")
    public ResponseEntity<String> updateBooking(@PathVariable Long id, @RequestBody Booking bookingUpdates){
        Booking existingBooking = bookingRepository.findById(id).orElse(null);
        if(existingBooking == null){
            return ResponseEntity.badRequest().body("Booking is not found with id:" + id);
        }
        if(bookingUpdates.getBookingId() != null){
            existingBooking.setBookingId(bookingUpdates.getBookingId());
        }
        if(bookingUpdates.getUserId() != null){
            existingBooking.setUserId(bookingUpdates.getUserId());
        }
        if(bookingUpdates.getBookingHotelId() != null){
            existingBooking.setBookingHotelId(bookingUpdates.getBookingHotelId());
        }
        if(bookingUpdates.getBookingType() != null){
            existingBooking.setBookingType(bookingUpdates.getBookingType());
        }
       /* if(bookingUpdates.getBookingDate() != null){
            existingBooking.setBookingDate(bookingUpdates.getBookingDate());
        }*/

        bookingRepository.save(existingBooking);
        return ResponseEntity.ok("Booking updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id){
        if(bookingRepository.existsById(id)){
            bookingRepository.deleteById(id);
            return ResponseEntity.ok("Booking cancelled successfully");
        } else {
            return ResponseEntity.badRequest().body("Booking is not found with id:" + id);
        }
    }


}
