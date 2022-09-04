package com.dev.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.data.CheckingRepo;
import com.dev.data.CheckingsTransactionRepo;
import com.dev.model.Checking;
import com.dev.model.CheckingTransactions;

import lombok.extern.slf4j.Slf4j;

@Service @Slf4j
public class CheckingsService {
	
	private CheckingRepo repo;
	private CheckingsTransactionRepo transactionsRepo;
	
	public CheckingsService(CheckingRepo repo, CheckingsTransactionRepo transactionsRepo) {
		this.repo = repo;
		this.transactionsRepo = transactionsRepo;
	}
	
	public boolean findByName(Checking checking) {
		log.info("Looking for Checking Accounts with name: "+checking.getName());
		
		List<Checking> list = repo.findByName(checking.getName());
		
		if(list.isEmpty()){			
		log.info("Unique name is valid: "+checking.getName());
		
			return false;
	} else {
		log.info("Not a unique account name: "+checking.getName());
		
			return true;
		}
	}
	
	public void createCheckingsAccount(Checking checking) {
		log.info("Creating Checking Account: "+checking.getName());
		repo.save(checking);
		return;
	}
	
	public Optional<Checking> findById(int id) {
		log.info("Looking for Checking Account: "+id);
		return repo.findById(id);

	}
	
	public void deposit(Checking checking) {
						
		log.info("Depositing into Checking Account: "+checking.getId());
		repo.save(checking);		
	
		return;
	}
		

}
