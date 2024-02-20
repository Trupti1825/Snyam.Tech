package com.snyamtech.controller;

import com.snyamtech.model.Feedback;
import com.snyamtech.repository.FeedbackRepository;
import org.hibernate.sql.ast.tree.AbstractUpdateOrDeleteStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/feedback")

public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @PostMapping("/feedbacks")
    public ResponseEntity<Feedback> setFeedbackId(@RequestBody Feedback feedback)
    {
        Feedback setFeedback = feedbackRepository.save(feedback);
        return ResponseEntity.ok(feedback);

    }


    @GetMapping("/list")
    public ResponseEntity<List<Feedback>> getAllFeedbacks(){
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return ResponseEntity.ok(feedbacks);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFeedback(@PathVariable Long id){
        if (feedbackRepository.existsById(id)) {
            feedbackRepository.deleteById(id);
            return ResponseEntity.ok("Feedback delete successfully");
        }else {
            return ResponseEntity.badRequest().body("Feedback is not found with id:" + id);

        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id){
        Optional<Feedback> optionalFeedback = feedbackRepository . findById(id);
        if (optionalFeedback.isPresent()) {
            Feedback feedback = optionalFeedback.get();
            return ResponseEntity.ok(feedback);
        }
            else {
                return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/updates/{id}")
   public ResponseEntity<String> updateFeedback(@PathVariable Long id, @RequestBody Feedback feedbackUpdates ){
        Feedback existingFeedback = feedbackRepository.findById(id).orElse(null);
        if (existingFeedback == null) {
            return ResponseEntity.badRequest().body("Feedback is not found with id:" + id);
        }
        if (feedbackUpdates.getFeedbackId() != null) {
            existingFeedback.setFeedbackId(feedbackUpdates.getFeedbackId());
        }
        if (feedbackUpdates.getFeedbackFromUser() !=null) {
            existingFeedback.setFeedbackFromUser(feedbackUpdates.getFeedbackFromUser());
        }
        if (feedbackUpdates.getFeedbackRating() !=null) {
            existingFeedback.setFeedbackRating(feedbackUpdates.getFeedbackRating());
        }
        feedbackRepository.save(existingFeedback);
        return ResponseEntity.ok("Feedbacks updated successfully");
        }
    }

