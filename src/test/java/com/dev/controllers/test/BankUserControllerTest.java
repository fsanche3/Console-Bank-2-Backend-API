package com.dev.controllers.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dev.controllers.BankUserController;
import com.dev.model.BankUser;
import com.dev.services.BankUserService;
import com.dev.services.CheckingsService;
import com.dev.services.SavingsService;
import com.dev.utils.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = BankUserController.class)
public class BankUserControllerTest {
	@MockBean
	private BankUserService userServ;
	
	@MockBean
	private CheckingsService checkServ;
	
	@MockBean
	private SavingsService saveServ;
	
	@MockBean
	private JwtUtil jwt;
	
	@Autowired
	private MockMvc mockMvc;
	
	private ObjectMapper om = new ObjectMapper();
	
	@Test
	public void registerUser() throws JsonProcessingException, Exception {
		BankUser user = new BankUser(0,"franklyn","franklynBanking","password","snail@test.snail");

		Mockito.when(userServ.verifyRegistration(Mockito.any())).thenReturn(true);
		
		mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(user)))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void cannotRegisterUser() throws JsonProcessingException, Exception {
		BankUser user = new BankUser(0,"franklyn","franklynBanking","password","snail@test.snail");

		Mockito.when(userServ.verifyRegistration(Mockito.any())).thenReturn(false);
		
		mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(user)))
		.andExpect(status().isNotAcceptable());
	}
	
	@Test
	public void getUserById() throws Exception {
		BankUser user = new BankUser(0,"franklyn","franklynBanking","password","snail@test.snail");
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";
		
		Mockito.when(userServ.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
		
		mockMvc.perform(get("/user/1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken))
		.andExpect(status().isOk());
	}
	
	@Test
	public void changeUsername() throws JsonProcessingException, Exception {
		BankUser user = new BankUser(1,"franklyn","franklynBanking","password","snail@test.snail");
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";

		Mockito.when(userServ.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
		
		mockMvc.perform(put("/user/change_username/1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				.content(om.writeValueAsString(user)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void changeEmail() throws JsonProcessingException, Exception {
		BankUser user = new BankUser(1,"franklyn","franklynBanking","password","snail@test.snail");
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJGcmFua2x5biBTYW5jaGV6IiwiaWQiOjEsImVtYWlsIjoiZnNhbmNoZTNAb3N3ZWdvLmVkdSIsInVzZXJuYW1lIjoiRnJhbmtseW40QmFua2luZyJ9.5xBMKR3Dl49AU_7Zu2wvnz0ItV7exATYi5rU0MAdmPg";

		Mockito.when(userServ.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
		
		mockMvc.perform(put("/user/change_email/1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				.content(om.writeValueAsString(user)))
		.andExpect(status().isOk());
	}

}
