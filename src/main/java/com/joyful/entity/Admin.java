package com.joyful.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Admin {
	@Id
	private String loginID;
	private String password;

	@Override
	public String toString() {
		return "Admin [LoginID=" + loginID + ", password=" + password + "]";
	}

}
