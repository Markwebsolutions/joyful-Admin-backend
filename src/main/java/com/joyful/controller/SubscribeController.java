package com.joyful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.joyful.entity.Subscribe;
import com.joyful.repository.SubscribeRepository;


@RestController
@CrossOrigin("*")
public class SubscribeController {

	@Autowired
	SubscribeRepository subrepo;

	@GetMapping("/subscription")
	public List<Subscribe> getMethodName() {
		List<Subscribe> all = subrepo.findAll();
		return all;
	}
	
	@PostMapping("/subscribe")
	public Subscribe postMethodName(@RequestBody Subscribe subscribe) {
		Subscribe save = subrepo.save(subscribe);
		return save;
	}
	

}
