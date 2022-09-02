package com.dev.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.data.CheckingRepo;
import com.dev.model.Checking;

import lombok.extern.slf4j.Slf4j;

@Service @Slf4j
public class CheckingsService {
	
	private CheckingRepo repo;
	
	public CheckingsService(CheckingRepo repo) {
		this.repo = repo;
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
	
	

}
