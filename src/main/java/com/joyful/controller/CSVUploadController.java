package com.joyful.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joyful.service.CSVUploadService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/csv")
public class CSVUploadController {

    @Autowired
    private CSVUploadService csvUploadService;

    @PostMapping("/import")
    public ResponseEntity<?> importCSV(@RequestBody List<Map<String, Object>> csvRows) {
        try {
            Map<String, Object> result = csvUploadService.processCSVData(csvRows);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "message", "Server error: " + e.getMessage()
            ));
        }
    }
}
