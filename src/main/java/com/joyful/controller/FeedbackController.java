package com.joyful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joyful.entity.Feedback;
import com.joyful.repository.FeedbackRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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
	
}
