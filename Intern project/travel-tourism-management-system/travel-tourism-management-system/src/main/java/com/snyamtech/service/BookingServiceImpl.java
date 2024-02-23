package com.snyamtech.service;


import com.snyamtech.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService{

  @Autowired
  private BookingRepository bookingRepository;





}
