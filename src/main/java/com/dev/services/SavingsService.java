package com.dev.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dev.data.SavingRepo;
import com.dev.model.Checking;
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
	
	public Optional<Saving> findById(int i) {
		return repo.findById(i);
	}
	
	public void remove(Saving saving) {
		repo.delete(saving);
		return;
	}
	
	public void upsert(Saving saving) {
		log.info("upsert Savings Account: "+saving.getName());
		repo.save(saving);
		return;
	}
	
	public List<Saving> getByUserId(int id){
		return repo.findByUserId(id);
	}
	
	
	
	
}
