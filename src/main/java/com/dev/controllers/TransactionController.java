package com.dev.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping(path = "/transactions")
@Slf4j
public class TransactionController {
	
	private CheckingTransactionService ctServ;
	private SavingTransactionService stServ;
	private CheckingsService checkServ;
	private SavingsService saveServ;

	public TransactionController(CheckingsService checkServ,
			SavingsService saveServ, CheckingTransactionService ctServ, SavingTransactionService stServ) {
		this.checkServ = checkServ;
		this.saveServ = saveServ;
		this.ctServ = ctServ;
		this.stServ = stServ;
	}
	
	@GetMapping(path = "/get_checkings/{checkingid}")
	public ResponseEntity<List<CheckingTransaction>> getCheckingTransactionById(@PathVariable("checkingid") int checkingid,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {
		
		Optional<Checking> checking = checkServ.findById(checkingid);

		return ResponseEntity.status(HttpStatus.OK).body(ctServ.findByChecking(checking.get()));

	}
	
	@GetMapping(path = "/get_savings/{savingid}")
	public ResponseEntity<List<SavingTransaction>> getSavingTransactionById(@PathVariable("savingid") int savingid,
			@RequestHeader(value = "Authorization", required = true) String authorization)
			throws UnsupportedEncodingException {
		
		Optional<Saving> saving = saveServ.findById(savingid);

		return ResponseEntity.status(HttpStatus.OK).body(stServ.findBySaving(saving.get()));

	}

}
