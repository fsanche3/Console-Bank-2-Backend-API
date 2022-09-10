package com.dev.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.data.CheckingTransactionRepo;
import com.dev.model.Checking;
import com.dev.model.CheckingTransaction;

import lombok.extern.slf4j.Slf4j;

@Service @Slf4j
public class CheckingTransactionService {

	private CheckingTransactionRepo repo;

	private CheckingTransactionService(CheckingTransactionRepo repo) {
		this.repo = repo;
	}
	
	public void upsert(CheckingTransaction ct) {
		repo.save(ct);
		return;
	}
	
	public List<CheckingTransaction> findByChecking(Checking checking){
		 return repo.findByChecking(checking);
	}
	
}
