package com.dev.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.data.CheckingsTransactionRepo;
import com.dev.model.Checking;
import com.dev.model.CheckingTransactions;

import lombok.extern.slf4j.Slf4j;

@Service @Slf4j
public class CheckingsTransactionService {
	
	private CheckingsTransactionRepo repo;
	
	public CheckingsTransactionService(CheckingsTransactionRepo repo) {
		this.repo = repo;
	}
	
	public void upsert(Checking checking, double deposited) {
		
	}

}
