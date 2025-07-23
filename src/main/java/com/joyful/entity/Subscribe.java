package com.joyful.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;


@Entity
public class Subscribe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer subid;

	private String email;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime date;

	@PrePersist
	public void prePersist() {
		this.date = LocalDateTime.now();  // Automatically set before saving
	}

	// Getters and setters
	public Integer getSubid() { return subid; }
	public void setSubid(Integer subid) { this.subid = subid; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public LocalDateTime getDate() { return date; }
	public void setDate(LocalDateTime date) { this.date = date; }
}
