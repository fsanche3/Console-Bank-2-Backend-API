package com.dev.controllers;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.model.BankUser;
import com.dev.model.Checking;
import com.dev.model.CheckingTransactions;
import com.dev.model.Saving;
import com.dev.services.BankUserService;
import com.dev.services.CheckingsService;
import com.dev.services.CheckingsTransactionService;
import com.dev.services.SavingsService;
import com.dev.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/user")
@Slf4j
public class BankUserController {

	private BankUserService userServ;
	private CheckingsService checkServ;
	// transactions
	private CheckingsTransactionService ctServ;
	private SavingsService saveServ;
	private JwtUtil jwt;

	public BankUserController(BankUserService userServ, CheckingsService checkServ, JwtUtil jwt,
			SavingsService saveServ, CheckingsTransactionService ctServ) {
		this.userServ = userServ;
		this.checkServ = checkServ;
		this.jwt = jwt;
		this.saveServ = saveServ;
		this.ctServ = ctServ;
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
	public ResponseEntity<Checking> createCheckings(@RequestBody Checking checking,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {

		int id = jwt.getId(authorization);
		checking.setUser(userServ.findById(id).get());
		checking.setCreationdate(Timestamp.valueOf(LocalDateTime.now()));

		if (checkServ.findByName(checking)) {

			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);

		} else {
			checkServ.createCheckingsAccount(checking);
			return ResponseEntity.status(HttpStatus.OK).body(checking);

		}

	}

	@PostMapping(path = "/create_savings")
	public ResponseEntity<Saving> createSavings(@RequestBody Saving saving,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {

		int id = jwt.getId(authorization);
		saving.setUser(userServ.findById(id).get());
		saving.setCreationdate(Timestamp.valueOf(LocalDateTime.now()));

		if (saveServ.findByName(saving)) {

			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);

		} else {
			saveServ.createSavingsAccount(saving);
			return ResponseEntity.status(HttpStatus.OK).body(saving);

		}

	}

	@PutMapping(path = "/deposit_checkings")
	public ResponseEntity<Boolean> depositCheckings(@RequestBody Checking checking,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {

		int id = jwt.getId(authorization);
		checking.setUser(userServ.findById(id).get());

		Optional<Checking> opt = checkServ.findById(checking.getId());
		checking.setCreationdate(opt.get().getCreationdate());
		checking.setCheckTransactions(new ArrayList<>());

		if (opt.get().getBalance() > checking.getBalance()) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Boolean.FALSE);

		} else {

			double deposited = checking.getBalance() - opt.get().getBalance();

			checkServ.deposit(checking);
			ctServ.upsert(checking, deposited);

			return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
		}

	}
	
	/*
	@PutMapping(path="/deposit_savings")
	public ResponseEntity<Boolean> depositSavings(@RequestBody Saving saving,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {
		
		int id = jwt.getId(authorization);
		saving.setUser(userServ.findById(id).get());
		
		
	}
*/
	
	

}
