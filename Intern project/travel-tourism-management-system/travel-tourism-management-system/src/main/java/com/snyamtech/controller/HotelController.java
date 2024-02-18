package com.snyamtech.controller;

import com.snyamtech.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/hotel")

public class HotelController {

    @Autowired
    HotelRepository hotelRepository;


    /*-------------THIS METHOD IS USE FOR USER SIGNUP----------------*/



    /*-------------THIS METHOD IS USE FOR USER SIGNIN----------------*/

}
