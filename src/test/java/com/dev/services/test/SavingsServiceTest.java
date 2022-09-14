package com.dev.services.test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.dev.ConsoleBankApplication;
import com.dev.data.SavingRepo;
import com.dev.model.BankUser;
import com.dev.model.Saving;
import com.dev.services.SavingsService;

@SpringBootTest(classes = ConsoleBankApplication.class)
public class SavingsServiceTest {

	@MockBean 
	SavingRepo savingRepo;
	
	@Autowired
	SavingsService savingServ;
	
	@Test
	public void findByName(){
		Saving saving = new Saving(0, new BigDecimal(1.0), 0.2,"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		List<Saving> list = new ArrayList<>();
		list.add(saving);
		
		Mockito.when(savingRepo.findByName(Mockito.anyString())).thenReturn(list);
		
		Assertions.assertEquals(savingServ.findByName(saving), true);
	}
	
	@Test
	public void doNotFindByName(){
		Saving saving = new Saving(0, new BigDecimal(1.0), 0.2,"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		List<Saving> list = new ArrayList<>();
		
		Mockito.when(savingRepo.findByName(Mockito.anyString())).thenReturn(list);
		
		Assertions.assertEquals(savingServ.findByName(saving), false);
	}
	
	@Test
	public void findById() {
		Saving saving = new Saving(0, new BigDecimal(1.0), 0.2,"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		Optional<Saving> opt = Optional.of(saving);
		
		Mockito.when(savingRepo.findById(Mockito.anyInt())).thenReturn(opt);
		
		Assertions.assertEquals(savingServ.findById(Mockito.anyInt()), opt);
	}

	@Test
	public void findByUserId() {
		Saving saving = new Saving(0, new BigDecimal(1.0), 0.2,"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		List<Saving> list = new ArrayList<>();
		list.add(saving);
		
		Mockito.when(savingRepo.findByUserId(Mockito.anyInt())).thenReturn(list);
		
		Assertions.assertEquals(savingServ.getByUserId(Mockito.anyInt()), list);
	}
	
	
	@Test
	public void upsert() {
		Saving saving = new Saving(0, new BigDecimal(1.0), 0.2,"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		Mockito.when(savingRepo.save(Mockito.any())).thenReturn(saving);
		savingServ.upsert(saving);
	}
	
	@Test
	public void delete() {
		Saving saving = new Saving(0, new BigDecimal(1.0), 0.2,"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		savingServ.remove(saving);
	}
	
	
}
