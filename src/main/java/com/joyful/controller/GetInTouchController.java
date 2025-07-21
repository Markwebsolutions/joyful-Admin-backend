package com.joyful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.joyful.entity.GetInTouch;
import com.joyful.repository.GetInTouchRepository;



@RestController
@CrossOrigin(origins = "*")
public class GetInTouchController {

	@Autowired
	GetInTouchRepository getInTouchrepo;
	
	@GetMapping("/all-query")
	public ResponseEntity<List<GetInTouch>> getAllQuery() {
	    System.out.println("GET /all-query called");
	    List<GetInTouch> all = getInTouchrepo.findAll();
	    return ResponseEntity.ok(all);
	}

	
	@PostMapping("/contact-us")
	public ResponseEntity<String> postMethodName(@RequestBody GetInTouch getInTouch) {
		getInTouchrepo.save(getInTouch);
		return ResponseEntity.ok("Contact form submitted successfully.");
	}
	
	@DeleteMapping("/delete-query/{id}")
	public ResponseEntity<String> deleteQuery(@PathVariable Long id) {
	    getInTouchrepo.deleteById(id);
	    return ResponseEntity.ok("Deleted successfully.");
	}

}   

