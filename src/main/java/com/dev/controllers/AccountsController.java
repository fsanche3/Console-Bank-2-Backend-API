package com.dev.controllers;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
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

import com.dev.model.Checking;
import com.dev.model.CheckingTransaction;
import com.dev.model.Saving;
import com.dev.model.SavingTransaction;
import com.dev.services.BankUserService;
import com.dev.services.CheckingTransactionService;
import com.dev.services.CheckingsService;
import com.dev.services.SavingTransactionService;
import com.dev.services.SavingsService;
import com.dev.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/accounts")
@Slf4j
public class AccountsController {

	private BankUserService userServ;
	private CheckingsService checkServ;
	private CheckingTransactionService ctServ;
	private SavingTransactionService stServ;
	private SavingsService saveServ;
	private JwtUtil jwt;

	public AccountsController(BankUserService userServ, CheckingsService checkServ, JwtUtil jwt,
			SavingsService saveServ, CheckingTransactionService ctServ, SavingTransactionService stServ) {
		this.userServ = userServ;
		this.checkServ = checkServ;
		this.jwt = jwt;
		this.saveServ = saveServ;
		this.ctServ = ctServ;
		this.stServ = stServ;

	}

	@GetMapping(path = "/get_checkings/{id}")
	public ResponseEntity<Checking> getCheckingById(@PathVariable("id") int id,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {
		
		Optional<Checking> checking = checkServ.findById(id);

		return ResponseEntity.status(HttpStatus.OK).body(checking.get());

	}

	@GetMapping(path = "/get_savings/{id}")
	public ResponseEntity<Saving> getSavingById(@PathVariable("id") int id,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {

		Optional<Saving> saving = saveServ.findById(id);

		return ResponseEntity.status(HttpStatus.OK).body(saving.get());

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
	public ResponseEntity<Boolean> createCheckings(@RequestBody Checking checking,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {

		int id = jwt.getId(authorization);
		checking.setUser(userServ.findById(id).get());
		checking.setCreationdate(Timestamp.valueOf(LocalDateTime.now()));

		if (checking.getBalance().intValue() < 0) {
			checking.setBalance(BigDecimal.valueOf(0));
		}

		if (checkServ.findByName(checking)) {

			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(false);

		} else {
			checkServ.upsert(checking);
			return ResponseEntity.status(HttpStatus.OK).body(true);

		}

	}

	@PostMapping(path = "/create_savings")
	public ResponseEntity<Boolean> createSavings(@RequestBody Saving saving,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {

		int id = jwt.getId(authorization);
		saving.setUser(userServ.findById(id).get());
		saving.setCreationdate(Timestamp.valueOf(LocalDateTime.now()));

		if (saving.getBalance().intValue() < 0) {
			saving.setBalance(BigDecimal.valueOf(0));
		}

		if (saveServ.findByName(saving)) {

			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(false);

		} else {
			saveServ.upsert(saving);
			return ResponseEntity.status(HttpStatus.OK).body(true);

		}

	}

	@PutMapping(path = "/apply_intrest/{id}")
	public ResponseEntity<Boolean> applyIntrest(@PathVariable("id") int savingId,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {

		MathContext m = new MathContext(8);

		Optional<Saving> opt = saveServ.findById(savingId);

		BigDecimal total = opt.get().getBalance();
		BigDecimal rate = new BigDecimal(opt.get().getIntrestrate());
		BigDecimal earnings = total.multiply(rate);
		total = earnings.add(total);
		total = total.round(m);
		opt.get().setBalance(total);

		SavingTransaction st = new SavingTransaction(0, "%", total, earnings, Timestamp.valueOf(LocalDateTime.now()),
				opt.get());

		saveServ.upsert(opt.get());
		stServ.upsert(st);

		return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
	}

	@PutMapping(path = "/deposit_checkings/{id}")
	public ResponseEntity<Boolean> depositCheckings(@RequestBody Checking checking, @PathVariable("id") int checkingId,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {

		MathContext m = new MathContext(6);

		Optional<Checking> opt = checkServ.findById(checkingId);

		if (checking.getBalance().intValue() < 0) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Boolean.FALSE);

		} else {

			BigDecimal total = opt.get().getBalance();
			total = total.add(checking.getBalance());
			total = total.round(m);
			opt.get().setBalance(total);

			CheckingTransaction ct = new CheckingTransaction(0, "+", total, checking.getBalance(),
					Timestamp.valueOf(LocalDateTime.now()), opt.get());

			checkServ.upsert(opt.get());
			ctServ.upsert(ct);

			return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
		}

	}

	@PutMapping(path = "/deposit_savings/{id}")
	public ResponseEntity<Boolean> depositSavings(@RequestBody Saving saving, @PathVariable("id") int savingId,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {

		MathContext m = new MathContext(6);

		Optional<Saving> opt = saveServ.findById(savingId);

		if (saving.getBalance().intValue() < 0) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Boolean.FALSE);

		} else {
			BigDecimal total = opt.get().getBalance();
			total = total.add(saving.getBalance());
			total = total.round(m);
			opt.get().setBalance(total);

			SavingTransaction st = new SavingTransaction(0, "+", total, saving.getBalance(),
					Timestamp.valueOf(LocalDateTime.now()), opt.get());

			saveServ.upsert(opt.get());
			stServ.upsert(st);

			return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);

		}

	}

	@PutMapping(path = "/withdrawl_savings/{id}")
	public ResponseEntity<Boolean> withdrawlSavings(@RequestBody Saving saving, @PathVariable("id") int savingId,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {

		MathContext m = new MathContext(6);

		Optional<Saving> opt = saveServ.findById(savingId);

		if (opt.get().getBalance().intValue() < saving.getBalance().intValue() || saving.getBalance().intValue() < 0) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Boolean.FALSE);

		} else {

			BigDecimal total = opt.get().getBalance();
			total = total.add(saving.getBalance().negate());
			total = total.round(m);
			opt.get().setBalance(total);

			SavingTransaction st = new SavingTransaction(0, "-", total, saving.getBalance(),
					Timestamp.valueOf(LocalDateTime.now()), opt.get());

			saveServ.upsert(opt.get());
			stServ.upsert(st);

			return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);

		}
	}

	@PutMapping(path = "/withdrawl_checkings/{id}")
	public ResponseEntity<Boolean> withdrawlCheckings(@RequestBody Checking checking,
			@PathVariable("id") int checkingId,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {

		MathContext m = new MathContext(6);

		Optional<Checking> opt = checkServ.findById(checkingId);

		if (opt.get().getBalance().doubleValue() < checking.getBalance().doubleValue()
				|| checking.getBalance().intValue() < 0) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Boolean.FALSE);

		} else {

			BigDecimal total = opt.get().getBalance();
			total = total.add(checking.getBalance().negate());
			total = total.round(m);
			opt.get().setBalance(total);

			CheckingTransaction ct = new CheckingTransaction(0, "-", total, checking.getBalance(),
					Timestamp.valueOf(LocalDateTime.now()), opt.get());

			checkServ.upsert(opt.get());
			ctServ.upsert(ct);

			return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);

		}
	}

	@DeleteMapping(path = "/remove_checkings/{id}")
	public ResponseEntity<Boolean> deleteCheckings(@PathVariable("id") int i,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {

		Optional<Checking> delete = checkServ.findById(i);

		if (delete.get().getUser() != null) {

			CheckingTransaction ct = new CheckingTransaction(0, "x", BigDecimal.valueOf(0), BigDecimal.valueOf(0),
					Timestamp.valueOf(LocalDateTime.now()), delete.get());

			checkServ.remove(delete.get());
			ctServ.upsert(ct);

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

		if (delete.get().getUser() != null) {

			SavingTransaction st = new SavingTransaction(0, "x", BigDecimal.valueOf(0), BigDecimal.valueOf(0),
					Timestamp.valueOf(LocalDateTime.now()), delete.get());

			saveServ.remove(delete.get());
			stServ.upsert(st);

			return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Boolean.FALSE);

		}

	}

}
