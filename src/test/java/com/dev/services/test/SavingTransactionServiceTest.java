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
import com.dev.data.SavingTransactionRepo;
import com.dev.model.BankUser;
import com.dev.model.Saving;
import com.dev.model.SavingTransaction;
import com.dev.services.SavingTransactionService;

@SpringBootTest(classes = ConsoleBankApplication.class)
public class SavingTransactionServiceTest {
	
	@MockBean
	SavingTransactionRepo transacRepo;
	
	@Autowired 
	SavingTransactionService transacServ;
	
	@Test
	public void findBySaving() {
		Saving saving = new Saving(0, new BigDecimal(1.0), 0.2,"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		List<SavingTransaction> list = new ArrayList<>();
		list.add(new SavingTransaction());
		
		Mockito.when(transacRepo.findBySaving(saving)).thenReturn(list);
		
		Assertions.assertEquals(transacServ.findBySaving(saving), list);
	}

	@Test
	public void upsert() {
		Mockito.when(transacRepo.save(Mockito.any())).thenReturn(new SavingTransaction());	
		transacServ.upsert(Mockito.any());
	}

}
