package com.joyful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joyful.entity.Feedback;
import com.joyful.repository.FeedbackRepository;

@RestController
@CrossOrigin("*")
public class FeedbackController {

	@Autowired
	FeedbackRepository feedbackrepo;

	@GetMapping("/feedbacks")
	public List<Feedback> getallfeedback() {
		List<Feedback> all = feedbackrepo.findAll();
		return all;
	}

	@PostMapping("/feedback")
	public Feedback createFeedback(@RequestBody Feedback feedback) {
		Feedback save = feedbackrepo.save(feedback);
		return save;
	}

	@DeleteMapping("/deletefeedback")
	public String deleteFeedback(@RequestParam Integer id) {
		feedbackrepo.deleteById(id);
		return "deleted";
	}

	@DeleteMapping("/deleteAllFeedback")
	public ResponseEntity<String> deleteAll() {
		feedbackrepo.deleteAll();
		return ResponseEntity.ok("All deleted");
	}
	
	@PutMapping("/feedbacks/update")
	public Feedback updateFeedback(@RequestBody Feedback feedback) {
	    return feedbackrepo.save(feedback);
	}

}
