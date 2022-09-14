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
import com.dev.data.CheckingRepo;
import com.dev.model.BankUser;
import com.dev.model.Checking;
import com.dev.services.CheckingsService;

@SpringBootTest(classes = ConsoleBankApplication.class)
public class CheckingsServiceTest {
	
	@MockBean
	private CheckingRepo checkingRepo;
	
	@Autowired
	CheckingsService checkingServ;
	
	@Test
	public void findByName() throws InstantiationException, IllegalAccessException {
		Checking check = new Checking(0, new BigDecimal(1.0),"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		List<Checking> list = new ArrayList<>();
		list.add(check);
		
		Mockito.when(checkingRepo.findByName(Mockito.anyString())).thenReturn(list);
		
		Assertions.assertEquals(checkingServ.findByName(check), true);
	}

	@Test
	public void cannotFindByName() throws InstantiationException, IllegalAccessException {
		Checking check = new Checking(0, new BigDecimal(1.0),"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		List<Checking> list = new ArrayList<>();
		
		Mockito.when(checkingRepo.findByName(Mockito.anyString())).thenReturn(list);
		
		Assertions.assertEquals(checkingServ.findByName(check), false);
	}
	

	@Test
	public void findById() {
		Checking check = new Checking(0, new BigDecimal(1.0),"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		Optional<Checking> opt = Optional.of(check);
		
		Mockito.when(checkingRepo.findById(Mockito.anyInt())).thenReturn(opt);
		
		Assertions.assertEquals(checkingServ.findById(Mockito.anyInt()), opt);
	}

	@Test
	public void findByUserId() {
		Checking check = new Checking(0, new BigDecimal(1.0),"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		List<Checking> list = new ArrayList<>();
		list.add(check);
		
		Mockito.when(checkingRepo.findByUserId(Mockito.anyInt())).thenReturn(list);
		
		Assertions.assertEquals(checkingServ.getByUserId(Mockito.anyInt()), list);
	}
	
	@Test
	public void upsert() {
		Checking check = new Checking(0, new BigDecimal(1.0),"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		checkingServ.upsert(check);
	}
	
	@Test
	public void delete() {
		Checking check = new Checking(0, new BigDecimal(1.0),"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		checkingServ.remove(check);
	}
	
}
