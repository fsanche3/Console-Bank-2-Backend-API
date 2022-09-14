package com.dev.controllers.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dev.controllers.AccountsController;
import com.dev.model.BankUser;
import com.dev.model.Checking;
import com.dev.model.Saving;
import com.dev.services.BankUserService;
import com.dev.services.CheckingTransactionService;
import com.dev.services.CheckingsService;
import com.dev.services.SavingTransactionService;
import com.dev.services.SavingsService;
import com.dev.utils.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(controllers = AccountsController.class)
public class AccountsControllerTest {

	@MockBean
	private BankUserService userServ;

	@MockBean
	private CheckingsService checkingServ;
	
	@MockBean
	private CheckingTransactionService ctServ;
	
	@MockBean
	private SavingTransactionService stServ;
	
	@MockBean
	private SavingsService savingServ;
	
	@MockBean
	private JwtUtil jwtUtil;
	
	@Autowired
	private MockMvc mockMvc;
	
	private ObjectMapper om = new ObjectMapper();
		
	@Test
	public void getCheckingById() throws Exception {
		Checking checking = new Checking(1, new BigDecimal(1.0),"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		Optional<Checking> opt = Optional.of(checking);
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";

		Mockito.when(checkingServ.findById(Mockito.anyInt())).thenReturn(opt);
		
		mockMvc.perform(get("/accounts/get_checkings/1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				)
		.andExpect(status().isOk());
	}
	
	@Test
	public void getSavingById() throws Exception {
		Saving saving = new Saving(1, new BigDecimal(1.0), 0.2,"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		Optional<Saving> opt = Optional.of(saving);
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";

		Mockito.when(savingServ.findById(Mockito.anyInt())).thenReturn(opt);
		
		mockMvc.perform(get("/accounts/get_savings/1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				)
		.andExpect(status().isOk());
	}
	
	@Test
	public void getCheckings() throws Exception {
		Checking checking = new Checking(1, new BigDecimal(1.0),"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		List<Checking> list = new ArrayList<>();
		list.add(checking);
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";
			
		Mockito.when(checkingServ.getByUserId(Mockito.anyInt())).thenReturn(list);
		
		mockMvc.perform(get("/accounts/get_checkings").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				)
		.andExpect(status().isOk());
	}
	
	@Test
	public void getSavings() throws Exception {
		Saving saving = new Saving(1, new BigDecimal(1.0), 0.2,"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		List<Saving> list = new ArrayList<>();
		list.add(saving);
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";
			
		Mockito.when(savingServ.getByUserId(Mockito.anyInt())).thenReturn(list);
		
		mockMvc.perform(get("/accounts/get_savings").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				)
		.andExpect(status().isOk());
	}
	
	@Test
	public void createCheckings() throws JsonProcessingException, Exception {
		Checking checking = new Checking(1, new BigDecimal(-9.0),"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";
		
		Mockito.when(userServ.findById(Mockito.anyInt())).thenReturn(Optional.of(new BankUser()));
		Mockito.when(checkingServ.findByName(Mockito.any())).thenReturn(false);
	
		mockMvc.perform(post("/accounts/create_checkings").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				.content(om.writeValueAsString(checking)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void cannotCreateCheckings() throws JsonProcessingException, Exception {
		Checking checking = new Checking(1, new BigDecimal(1.0),"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";
		
		Mockito.when(userServ.findById(Mockito.anyInt())).thenReturn(Optional.of(new BankUser()));
		Mockito.when(checkingServ.findByName(Mockito.any())).thenReturn(true);
	
		mockMvc.perform(post("/accounts/create_checkings").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				.content(om.writeValueAsString(checking)))
		.andExpect(status().isNotAcceptable());
	}

	@Test
	public void createSavings() throws JsonProcessingException, Exception {
		Saving saving = new Saving(1, new BigDecimal(-9.0), 0.2,"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";
		
		Mockito.when(userServ.findById(Mockito.anyInt())).thenReturn(Optional.of(new BankUser()));
		Mockito.when(savingServ.findByName(Mockito.any())).thenReturn(false);
	
		mockMvc.perform(post("/accounts/create_savings").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				.content(om.writeValueAsString(saving)))
		.andExpect(status().isOk());
	}
	

	@Test
	public void cannotCreateSavings() throws JsonProcessingException, Exception {
		Saving saving = new Saving(1, new BigDecimal(9.0), 0.2,"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";
		
		Mockito.when(userServ.findById(Mockito.anyInt())).thenReturn(Optional.of(new BankUser()));
		Mockito.when(savingServ.findByName(Mockito.any())).thenReturn(true);
	
		mockMvc.perform(post("/accounts/create_savings").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				.content(om.writeValueAsString(saving)))
		.andExpect(status().isNotAcceptable());
	}
	
	@Test
	public void applyInterest() throws JsonProcessingException, Exception {
		Saving saving = new Saving(1, new BigDecimal(9.0), 0.2,"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";
		
		Mockito.when(savingServ.findById(Mockito.anyInt())).thenReturn(Optional.of(saving));
		
		mockMvc.perform(put("/accounts/apply_intrest/1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken))
		.andExpect(status().isOk());
	}
	
	@Test
	public void depositCheckings() throws Exception {
		Checking checking = new Checking(1, new BigDecimal(1.0),"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";

		Mockito.when(checkingServ.findById(Mockito.anyInt())).thenReturn(Optional.of(checking));
		
		mockMvc.perform(put("/accounts/deposit_checkings/1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				.content(om.writeValueAsString(checking)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void cannotDepositCheckings() throws Exception {
		Checking checking = new Checking(1, new BigDecimal(-1.0),"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";

		Mockito.when(checkingServ.findById(Mockito.anyInt())).thenReturn(Optional.of(checking));
		
		mockMvc.perform(put("/accounts/deposit_checkings/1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				.content(om.writeValueAsString(checking)))
		.andExpect(status().isNotAcceptable());
	}
	
	@Test
	public void depositSavings() throws Exception {
		Saving saving = new Saving(1, new BigDecimal(9.0), 0.2,"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";

		Mockito.when(savingServ.findById(Mockito.anyInt())).thenReturn(Optional.of(saving));
		
		mockMvc.perform(put("/accounts/deposit_savings/1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				.content(om.writeValueAsString(saving)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void cannotDepositSavings() throws Exception {
		Saving saving = new Saving(1, new BigDecimal(-9.0), 0.2,"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";

		Mockito.when(savingServ.findById(Mockito.anyInt())).thenReturn(Optional.of(saving));
		
		mockMvc.perform(put("/accounts/deposit_savings/1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				.content(om.writeValueAsString(saving)))
		.andExpect(status().isNotAcceptable());
	}
	
	@Test
	public void withdrawlSavings() throws JsonProcessingException, Exception {
		Saving saving = new Saving(1, new BigDecimal(9.0), 0.2,"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";
		
		Mockito.when(savingServ.findById(Mockito.anyInt())).thenReturn(Optional.of(saving));

		mockMvc.perform(put("/accounts/withdrawl_savings/1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				.content(om.writeValueAsString(saving)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void cannotWithdrawlSavings() throws JsonProcessingException, Exception {
		Saving saving = new Saving(1, new BigDecimal(-9.0), 0.2,"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";
		
		Mockito.when(savingServ.findById(Mockito.anyInt())).thenReturn(Optional.of(saving));

		mockMvc.perform(put("/accounts/withdrawl_savings/1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				.content(om.writeValueAsString(saving)))
		.andExpect(status().isNotAcceptable());
	}
	
	@Test
	public void withdrawlCheckings() throws JsonProcessingException, Exception {
		Checking checking = new Checking(1, new BigDecimal(1.0),"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";
		
		Mockito.when(checkingServ.findById(Mockito.anyInt())).thenReturn(Optional.of(checking));

		mockMvc.perform(put("/accounts/withdrawl_checkings/1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				.content(om.writeValueAsString(checking)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void cannotWithdrawlCheckings() throws JsonProcessingException, Exception {
		Checking checking = new Checking(1, new BigDecimal(-1.0),"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";
		
		Mockito.when(checkingServ.findById(Mockito.anyInt())).thenReturn(Optional.of(checking));

		mockMvc.perform(put("/accounts/withdrawl_checkings/1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				.content(om.writeValueAsString(checking)))
		.andExpect(status().isNotAcceptable());
	}
	
	@Test
	public void removeCheckings() throws JsonProcessingException, Exception {
		Checking checking = new Checking(1, new BigDecimal(1.0),"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";
		
		Mockito.when(checkingServ.findById(Mockito.anyInt())).thenReturn(Optional.of(checking));
		
		mockMvc.perform(delete("/accounts/remove_checkings/1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken))
		.andExpect(status().isOk());
	}
	
	@Test
	public void cannotRemoveCheckings() throws JsonProcessingException, Exception {
		Checking checking = new Checking(1, new BigDecimal(1.0),"name",Timestamp.valueOf(LocalDateTime.now()), null);
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";
		
		Mockito.when(checkingServ.findById(Mockito.anyInt())).thenReturn(Optional.of(checking));
		
		mockMvc.perform(delete("/accounts/remove_checkings/1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void removeSavings() throws JsonProcessingException, Exception {
		Saving saving = new Saving(1, new BigDecimal(9.0), 0.2,"name",Timestamp.valueOf(LocalDateTime.now()), new BankUser());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";
		
		Mockito.when(savingServ.findById(Mockito.anyInt())).thenReturn(Optional.of(saving));
		
		mockMvc.perform(delete("/accounts/remove_savings/1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken))
		.andExpect(status().isOk());
	}
	
	@Test
	public void canonotRemoveSavings() throws JsonProcessingException, Exception {
		Saving saving = new Saving(1, new BigDecimal(9.0), 0.2,"name",Timestamp.valueOf(LocalDateTime.now()), null);
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";
		
		Mockito.when(savingServ.findById(Mockito.anyInt())).thenReturn(Optional.of(saving));
		
		mockMvc.perform(delete("/accounts/remove_savings/1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken))
		.andExpect(status().isNotFound());
	}
	
}
