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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.model.BankUser;
import com.dev.model.Checking;
import com.dev.model.Saving;
import com.dev.models.dtos.BankUserDTO;
import com.dev.services.BankUserService;
import com.dev.services.CheckingsService;
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
	private SavingsService saveServ;
	private JwtUtil jwt;

	public BankUserController(BankUserService userServ, CheckingsService checkServ, JwtUtil jwt,
			SavingsService saveServ) {
		this.userServ = userServ;
		this.checkServ = checkServ;
		this.jwt = jwt;
		this.saveServ = saveServ;

	}

	@PostMapping(path = "/register")
	public ResponseEntity<Boolean> registerUser(@RequestBody BankUser body) {

		boolean usernameDoesNotExist = userServ.verifyRegistration(body);
		if (usernameDoesNotExist) {
			return ResponseEntity.status(HttpStatus.CREATED).body(true);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(false);
		}

	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<BankUserDTO> getUserById(@PathVariable("id") int id) {

		BankUserDTO dto = new BankUserDTO(userServ.findById(id).get());

		return ResponseEntity.status(HttpStatus.OK).body(dto);

	}

}