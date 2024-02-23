package com.snyamtech.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "user_id")
    private String userId;
    @Column(name = "booking_hotel_id")
    private Long bookingHotelId;

    @Column(name = "booking_type")
    private String bookingType;

    @Column(name="booking_date")
    private LocalDate bookingdate = LocalDate.now();




}
