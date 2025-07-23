package com.joyful.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joyful.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

}
