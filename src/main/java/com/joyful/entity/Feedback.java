package com.joyful.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer feedbackid;
	private Integer star;
	private String heading;
	@Column(columnDefinition = "TEXT")
	private String description;
	private String name;
	@Column(columnDefinition = "TEXT")
	private String image;

}
