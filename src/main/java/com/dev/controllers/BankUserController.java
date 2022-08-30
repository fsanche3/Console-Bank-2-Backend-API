package com.dev.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.model.BankUser;
import com.dev.services.BankUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class BankUserController {
	
	private BankUserService userServ;
	
	@PostMapping(path = "/register")
	public ResponseEntity<Void> registerFreelancerPost(@RequestBody BankUser body) {
		boolean usernameDoesNotExist = userServ.verifyRegistration(body);
		
		if (usernameDoesNotExist) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
	
		
		
	} 

}
