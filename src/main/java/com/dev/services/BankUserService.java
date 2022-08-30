package com.dev.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.data.BankUserRepo;
import com.dev.model.BankUser;
import com.dev.models.dtos.LoginRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service @Slf4j
public class BankUserService {

	private BankUserRepo userRepo;
	
	public BankUserService(BankUserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	public boolean verifyRegistration(BankUser user) {
		log.info("Checking For unique username: " + user.getUsername());

		boolean check;
		List<BankUser> list = userRepo.findByUsername(user.getUsername());
		if(list.size() == 0) {
		userRepo.save(user);
		check = true;

		log.info("Check passed: " + user.getUsername() +" is unique");

		} else {
			check = false;
		log.info("Check failed: " + user.getUsername() +" is not unique");
		}
		
		return check;
	}
	
	public BankUser verifyAuth(LoginRequest body) {
		log.info("Verifying Login for user "+ body.getUsername()+"");
		
		List<BankUser> userList = userRepo.findByUsername(body.getUsername());
		BankUser user = userList.get(0);
		
		if(user == null ) {
		log.info("Login authentication failed for username:  "+ body.getUsername()+"");
		return null;
		}
		
		if(user.getPassword() == body.getPassword()) {
			log.info("Login complete");
			return user;
		} 	
		else {	
			log.info("Login authentication failed for password: "+body.getPassword()+"");
			return null;
		}
	}
}
