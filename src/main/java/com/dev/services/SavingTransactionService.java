package com.dev.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.data.SavingTransactionRepo;
import com.dev.model.Saving;
import com.dev.model.SavingTransaction;

import lombok.extern.slf4j.Slf4j;

@Service @Slf4j
public class SavingTransactionService {

	private SavingTransactionRepo repo;
	
	private SavingTransactionService(SavingTransactionRepo repo) {
		this.repo = repo;
	}
	
	public void upsert(SavingTransaction st) {
		repo.save(st);
		return;
	}
	
	public List<SavingTransaction> findBySaving(Saving saving){
		return repo.findBySaving(saving);
	}
}
