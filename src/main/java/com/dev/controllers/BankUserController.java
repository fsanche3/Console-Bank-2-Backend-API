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

	@GetMapping(path = "/get_checkings/{id}")
	public ResponseEntity<Checking> getCheckingById(@PathVariable("id") int id,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {
		
		int userId = jwt.getId(authorization);
		
		Optional<Checking> checking = checkServ.findById(id);

		if (checking.get().getUser().getId() == userId) {

		return ResponseEntity.status(HttpStatus.OK).body(checking.get());
		
		}else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		}
	}

	@GetMapping(path = "/get_savings/{id}")
	public ResponseEntity<Saving> getSavingById(@PathVariable("id") int id,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {

		int userId = jwt.getId(authorization);

		Optional<Saving> saving = saveServ.findById(id);

		if (saving.get().getUser().getId() == userId) {

			return ResponseEntity.status(HttpStatus.OK).body(saving.get());

		} else {

			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		}
	}

	@GetMapping(path = "/get_checkings")
	public ResponseEntity<List<Checking>> getCheckings(
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {

		int id = jwt.getId(authorization);

		return ResponseEntity.status(HttpStatus.OK).body(checkServ.getByUserId(id));

	}

	@GetMapping(path = "/get_savings")
	public ResponseEntity<List<Saving>> getSavings(
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {

		int id = jwt.getId(authorization);

		return ResponseEntity.status(HttpStatus.OK).body(saveServ.getByUserId(id));

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
			checkServ.upsert(checking);
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
			saveServ.upsert(saving);
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

		if (opt.get().getBalance() > checking.getBalance()) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Boolean.FALSE);

		} else {

			double deposited = checking.getBalance() - opt.get().getBalance();

			checkServ.upsert(checking);

			return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
		}

	}

	@PutMapping(path = "/deposit_savings")
	public ResponseEntity<Boolean> depositSavings(@RequestBody Saving saving,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {

		int id = jwt.getId(authorization);
		saving.setUser(userServ.findById(id).get());

		Optional<Saving> opt = saveServ.findById(saving.getId());
		saving.setCreationdate(opt.get().getCreationdate());
		saving.setIntrestrate(opt.get().getIntrestrate());

		if (opt.get().getBalance() > saving.getBalance()) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Boolean.FALSE);

		} else {

			double deposited = saving.getBalance() - opt.get().getBalance();

			saveServ.upsert(saving);

			return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);

		}

	}

	@PutMapping(path = "/withdrawl_savings")
	public ResponseEntity<Boolean> withdrawlSavings(@RequestBody Saving saving,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {

		int id = jwt.getId(authorization);
		saving.setUser(userServ.findById(id).get());

		Optional<Saving> opt = saveServ.findById(saving.getId());
		saving.setCreationdate(opt.get().getCreationdate());
		saving.setIntrestrate(opt.get().getIntrestrate());

		if (opt.get().getBalance() < saving.getBalance()) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Boolean.FALSE);

		} else {

			double withdrawn = saving.getBalance() - opt.get().getBalance();

			saveServ.upsert(saving);

			return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);

		}
	}

	@PutMapping(path = "/withdrawl_checkings")
	public ResponseEntity<Boolean> withdrawlCheckings(@RequestBody Checking checking,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {

		int id = jwt.getId(authorization);
		checking.setUser(userServ.findById(id).get());

		Optional<Checking> opt = checkServ.findById(checking.getId());
		checking.setCreationdate(opt.get().getCreationdate());

		if (opt.get().getBalance() < checking.getBalance()) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Boolean.FALSE);

		} else {

			double withdrawn = checking.getBalance() - opt.get().getBalance();

			checkServ.upsert(checking);

			return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);

		}
	}

	@DeleteMapping(path = "/remove_checkings/{id}")
	public ResponseEntity<Boolean> deleteCheckings(@PathVariable("id") int i,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {

		Optional<Checking> delete = checkServ.findById(i);

		if (delete.get() != null) {
			checkServ.remove(delete.get());
			return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Boolean.FALSE);

		}

	}

	@DeleteMapping(path = "/remove_savings/{id}")
	public ResponseEntity<Boolean> deleteSavings(@PathVariable("id") int i,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {

		Optional<Saving> delete = saveServ.findById(i);

		if (delete.get() != null) {
			saveServ.remove(delete.get());
			return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Boolean.FALSE);

		}

	}

}
