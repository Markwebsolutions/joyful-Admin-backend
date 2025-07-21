package com.joyful.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.joyful.entity.Admin;
import com.joyful.service.AdminService;

@RestController
@CrossOrigin(
		  origins = {
		    "http://localhost:5173",
		    "http://localhost:5174",
		    "http://localhost:3000",
		    "https://markweb-joyful.netlify.app",
		    "https://joyful-backend-frontend-production.up.railway.app"
		  },
		  allowCredentials = "true"
		)
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Admin admin) {
        boolean isLoggedIn = adminService.login(admin.getLoginID(), admin.getPassword());

        if (isLoggedIn) {
            return ResponseEntity.ok("Login successful.");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password.");
        }
    }
    
}