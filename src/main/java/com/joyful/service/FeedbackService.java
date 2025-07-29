package com.joyful.service;

import java.util.List;

import com.joyful.entity.Feedback;

public interface FeedbackService {
	Feedback saveFeedback(Feedback feedback);

	Feedback updateFeedback(Feedback feedback);

	List<Feedback> getAllFeedback();

	void deleteById(Integer id);

	void deleteAll();
}