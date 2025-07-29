package com.joyful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.joyful.entity.Feedback;
import com.joyful.service.FeedbackService;

@RestController
@CrossOrigin("*")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/feedbacks")
    public List<Feedback> getAllFeedback() {
        return feedbackService.getAllFeedback();
    }

    // ✅ Create or update based on feedbackid (POST used for both)
    @PostMapping("/feedback")
    public Feedback createOrUpdateFeedback(@RequestBody Feedback feedback) {
        if (feedback.getFeedbackid() != null) {
            return feedbackService.updateFeedback(feedback);
        } else {
            return feedbackService.saveFeedback(feedback);
        }
    }

    @DeleteMapping("/deletefeedback")
    public String deleteFeedback(@RequestParam Integer id) {
        feedbackService.deleteById(id);
        return "deleted";
    }

    @DeleteMapping("/deleteAllFeedback")
    public ResponseEntity<String> deleteAll() {
        feedbackService.deleteAll();
        return ResponseEntity.ok("All deleted");
    }
}
