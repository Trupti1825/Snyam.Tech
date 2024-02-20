package com.snyamtech.repository;

import com.snyamtech.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    boolean existsByHotelName(String hotelName);
    Hotel findByHotelName(String hotelName);
    Hotel findByHotelNameAndToken(String hotelName, String hotelToken);
}
