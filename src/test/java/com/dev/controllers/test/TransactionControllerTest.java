package com.dev.controllers.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dev.controllers.TransactionController;
import com.dev.model.BankUser;
import com.dev.model.Checking;
import com.dev.model.CheckingTransaction;
import com.dev.model.Saving;
import com.dev.model.SavingTransaction;
import com.dev.services.CheckingTransactionService;
import com.dev.services.CheckingsService;
import com.dev.services.SavingTransactionService;
import com.dev.services.SavingsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = TransactionController.class)
public class TransactionControllerTest {
	
	@MockBean
	private CheckingTransactionService ctServ;
	
	@MockBean
	private SavingTransactionService stServ;
	
	@MockBean
	private CheckingsService checkServ;
	
	@MockBean
	private SavingsService saveServ;
	
	@Autowired
	private MockMvc mockMvc;
	
	private ObjectMapper om = new ObjectMapper();
	
	@Test
	public void getCheckingsTransactionsById() throws JsonProcessingException, Exception {
		Checking checking = new Checking(1, new BigDecimal(1.0),"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";
		
		Mockito.when(checkServ.findById(Mockito.anyInt())).thenReturn(Optional.of(checking));
		Mockito.when(ctServ.findByChecking(Mockito.any())).thenReturn(new ArrayList<CheckingTransaction>());
	
		mockMvc.perform(get("/transactions/get_checkings/1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getSavingsTransactionsById() throws JsonProcessingException, Exception {
		Saving saving = new Saving(1, new BigDecimal(1.0), 0.2,"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";
		
		Mockito.when(saveServ.findById(Mockito.anyInt())).thenReturn(Optional.of(saving));
		Mockito.when(stServ.findBySaving(Mockito.any())).thenReturn(new ArrayList<SavingTransaction>());
	
		mockMvc.perform(get("/transactions/get_savings/1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken))
		.andExpect(status().isOk());
	}

}
