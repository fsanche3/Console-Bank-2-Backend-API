package com.dev.controllers;

import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.model.BankUser;
import com.dev.model.Checking;
import com.dev.services.BankUserService;
import com.dev.services.CheckingsService;
import com.dev.utils.JwtUtil;

@RestController
@RequestMapping(path = "/user")
public class BankUserController {
	
	private BankUserService userServ;
	private CheckingsService checkServ;
	private JwtUtil jwt;
	
	public BankUserController(BankUserService userServ, CheckingsService checkServ, JwtUtil jwt) {
		this.userServ = userServ;
		this.checkServ = checkServ;
		this.jwt = jwt;
	}
	
	@PostMapping(path = "/register")
	public ResponseEntity<Boolean> registerFreelancerPost(@RequestBody BankUser body) {
		boolean usernameDoesNotExist = userServ.verifyRegistration(body);
		if (usernameDoesNotExist) {
			return ResponseEntity.status(HttpStatus.CREATED).body(true);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(false);
		}
	
	}
	
	@PostMapping(path = "/add_checkings")
	public ResponseEntity<Checking> createCheckings(@RequestBody Checking checking, @RequestHeader(value = "Authorization", required = true) String authorization) throws UnsupportedEncodingException{
		
		int id = jwt.getId(authorization);
		checking.setUser(userServ.findById(id).get());
		
		if(!checkServ.findByName(checking)) {
			
			checkServ.createAccount(checking);
			return ResponseEntity.status(HttpStatus.OK).body(checking);
						
		} else {
		
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
			
		}
				
	}
		
	

}
