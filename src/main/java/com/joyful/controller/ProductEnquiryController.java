package com.joyful.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.joyful.entity.ProductEnquiry;
import com.joyful.repository.ProductEnquiryRepository;

@RestController
@CrossOrigin("*")
public class ProductEnquiryController {

    @Autowired
    private ProductEnquiryRepository enquiryRepository;

    // Save enquiry from frontend
    @PostMapping("/enquiry")
    public ResponseEntity<Map<String, Object>> saveEnquiry(@RequestBody ProductEnquiry enquiry) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validate required fields
            if (enquiry.getFullName() == null || enquiry.getFullName().isEmpty() ||
                enquiry.getEmail() == null || enquiry.getEmail().isEmpty() ||
                enquiry.getPhone() == null || enquiry.getPhone().isEmpty()) {
                
                response.put("success", false);
                response.put("message", "All required fields must be filled");
                return ResponseEntity.badRequest().body(response);
            }

            ProductEnquiry savedEnquiry = enquiryRepository.save(enquiry);
            
            response.put("success", true);
            response.put("message", "Enquiry saved successfully");
            response.put("data", savedEnquiry);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error saving enquiry: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Get all enquiries to show on admin dashboard
    @GetMapping("/enquiries")
    public ResponseEntity<Map<String, Object>> getAllEnquiries() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<ProductEnquiry> enquiries = enquiryRepository.findAll();
            
            response.put("success", true);
            response.put("data", enquiries);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving enquiries: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
//    added
    
    @DeleteMapping("/enquiries/{id}")
    public ResponseEntity<Map<String, String>> deleteEnquiry(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            if (!enquiryRepository.existsById(id)) {
                response.put("message", "Enquiry not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            enquiryRepository.deleteById(id);
            response.put("message", "Enquiry deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error deleting enquiry: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
} 