package com.joyful.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GetInTouch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contactid")
	private long contactid;

	@Column(name = "firstname", columnDefinition = "TEXT")
	private String firstname;

	@Column(name = "lastname", columnDefinition = "TEXT")
	private String lastname;
	
	@Column(name = "email", columnDefinition = "TEXT")
	private String email;
	
	@Column(name = "phone", columnDefinition = "TEXT")
	private String phone;

	@Column(name = "querytype", columnDefinition = "TEXT")
	private String querytype;

	@Column(name = "message", columnDefinition = "TEXT")
	private String message;

}
