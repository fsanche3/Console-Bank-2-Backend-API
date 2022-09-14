package com.dev.services.test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.dev.ConsoleBankApplication;
import com.dev.data.CheckingTransactionRepo;
import com.dev.model.BankUser;
import com.dev.model.Checking;
import com.dev.model.CheckingTransaction;
import com.dev.services.CheckingTransactionService;

@SpringBootTest(classes = ConsoleBankApplication.class)
public class CheckingTransactionServiceTest {
	
	@MockBean
	CheckingTransactionRepo transacRepo;
	
	@Autowired
	CheckingTransactionService transacServ;
	
	@Test
	public void findByChecking() {
		Checking check = new Checking(0, new BigDecimal(1.0),"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		List<CheckingTransaction> list = new ArrayList<>();
		list.add(new CheckingTransaction());
		
		Mockito.when(transacRepo.findByChecking(check)).thenReturn(list);
		
		Assertions.assertEquals(transacServ.findByChecking(check), list);
	}
	
	@Test
	public void upsert() {
		Mockito.when(transacRepo.save(Mockito.any())).thenReturn(new CheckingTransaction());	
		transacServ.upsert(Mockito.any());
	}

}
