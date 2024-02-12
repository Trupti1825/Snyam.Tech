package com.snyamtech.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "hotel_name")
    private String hotelName;

    @Column(name = "hotel_type")
    private String hotelType;

    @Column(name = "hotel_description")
    private String hotelDescription;

    @Column(name = "hotel_address")
    private String hotelAddress;

    @Column(name = "hotel_contact_number")
    private Long hotelContactNumber;

    @Column(name = "hotel_price")
    private Double hotelPrice;

}
