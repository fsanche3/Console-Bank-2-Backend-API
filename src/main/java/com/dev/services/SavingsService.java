package com.dev.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.data.SavingRepo;
import com.dev.model.Saving;

import lombok.extern.slf4j.Slf4j;

@Service @Slf4j
public class SavingsService {

	private SavingRepo repo;
	
	public SavingsService(SavingRepo repo) {
		this.repo = repo;
	}
	
	public boolean findByName(Saving saving) {
		log.info("Looking for Saving Accounts with name: "+saving.getName());
		
		List<Saving> list = repo.findByName(saving.getName());
		
		if(list.isEmpty()){			
		log.info("Unique name is valid: "+saving.getName());
		
			return false;
	} else {
		log.info("Not a unique account name: "+saving.getName());
		
			return true;
		}
	}
	
	public void createSavingsAccount(Saving saving) {
		log.info("Creating Savings Account: "+saving.getName());
		repo.save(saving);
		return;
	}
	
	
	
	
	
}
