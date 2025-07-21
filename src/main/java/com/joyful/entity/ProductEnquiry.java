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
public class ProductEnquiry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fullName", columnDefinition = "TEXT")
	private String fullName;
	@Column(name = "email", columnDefinition = "TEXT")
	private String email;
	@Column(name = "phone", columnDefinition = "TEXT")
	private String phone;
	@Column(name = "product", columnDefinition = "TEXT")
	private String product;
	@Column(name = "message", columnDefinition = "TEXT")
	private String message;

}
