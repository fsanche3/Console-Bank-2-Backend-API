package com.dev.controllers;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.model.BankUser;
import com.dev.model.Checking;
import com.dev.model.Saving;
import com.dev.services.BankUserService;
import com.dev.services.CheckingsService;
import com.dev.services.SavingsService;
import com.dev.utils.JwtUtil;

@RestController
@RequestMapping(path = "/user")
public class BankUserController {
	
	private BankUserService userServ;
	private CheckingsService checkServ;
	private SavingsService saveServ;
	private JwtUtil jwt;
	
	public BankUserController(BankUserService userServ, CheckingsService checkServ, JwtUtil jwt, SavingsService saveServ) {
		this.userServ = userServ;
		this.checkServ = checkServ;
		this.jwt = jwt;
		this.saveServ = saveServ;
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
	
	@PostMapping(path = "/create_checkings")
	public ResponseEntity<Checking> createCheckings(@RequestBody Checking checking, @RequestHeader(value = "Authorization", required = true) String authorization) throws UnsupportedEncodingException{
		
		int id = jwt.getId(authorization);
		checking.setUser(userServ.findById(id).get());
		checking.setCreationdate(Timestamp.valueOf(LocalDateTime.now()));
		
		if(checkServ.findByName(checking)) {
			
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
								
		} else {
			checkServ.createCheckingsAccount(checking);
			return ResponseEntity.status(HttpStatus.OK).body(checking);
			
		}
				
	}
	
	@PostMapping(path =  "/create_savings")
	public ResponseEntity<Saving> createSavings(@RequestBody Saving saving, @RequestHeader(value = "Authorization", required = true) String authorization) throws UnsupportedEncodingException{
		
		int id = jwt.getId(authorization);
		saving.setUser(userServ.findById(id).get());
		saving.setCreationdate(Timestamp.valueOf(LocalDateTime.now()));
		
		if(saveServ.findByName(saving)) {
			
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
								
		} else {
			saveServ.createSavingsAccount(saving);
			return ResponseEntity.status(HttpStatus.OK).body(saving);
			
		}
			
	}
	
	
		
	

}
