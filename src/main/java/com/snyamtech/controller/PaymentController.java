package com.snyamtech.controller;

import com.snyamtech.model.Payment;
import com.snyamtech.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;


    /*---------THIS METHOD IS USE FOR SAVE PAYMENT---------*/
    @PostMapping("/process")
    public Payment processPayment(@RequestBody Payment payment) {
        return paymentRepository.save(payment);
    }

    /*---------THIS METHOD IS USE FOR GET PAYMENT HISTORY---------*/
    @GetMapping("/history")
    public List<Payment> getPaymentHistory() {
        return paymentRepository.findAll();
    }


    /*---------THIS METHOD IS USE FOR GET PAYMENT BY ID---------*/
    @GetMapping("/get/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {

        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*---------THIS METHOD IS USE FOR UPDATE PAYMENT---------*/
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updatePayment(@PathVariable Long id,
                                                @RequestBody Payment paymentUpdates) {
        Payment existingPayment = paymentRepository.findById(id).orElse(null);
        if (existingPayment == null) {
            return ResponseEntity.badRequest().body("Payment not found with id: " + id);
        }
        if (paymentUpdates.getPaymentMode() != null) {
            existingPayment.setPaymentMode(paymentUpdates.getPaymentMode());
        }
        paymentRepository.save(existingPayment);
        return ResponseEntity.ok("Payment updated successfully");
    }
}
