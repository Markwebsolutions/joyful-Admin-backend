package com.joyful.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyful.entity.Feedback;
import com.joyful.repository.FeedbackRepository;
import com.joyful.service.FeedbackService;
import com.joyful.service.ImageStorageService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepo;

    @Autowired
    private ImageStorageService imageStorageService;

    @Override
    public Feedback saveFeedback(Feedback feedback) {
        if (feedback.getImage() != null && !feedback.getImage().isBlank()) {
            String processedImage = imageStorageService.storeImage(feedback.getImage());
            feedback.setImage(processedImage);
        }
        return feedbackRepo.save(feedback);
    }

    @Override
    public Feedback updateFeedback(Feedback feedback) {
        if (feedback.getImage() != null && !feedback.getImage().isBlank()) {
            String processedImage = imageStorageService.storeImage(feedback.getImage());
            feedback.setImage(processedImage);
        }
        return feedbackRepo.save(feedback);
    }

    @Override
    public List<Feedback> getAllFeedback() {
        return feedbackRepo.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        feedbackRepo.deleteById(id);
    }

    @Override
    public void deleteAll() {
        feedbackRepo.deleteAll();
    }
}
