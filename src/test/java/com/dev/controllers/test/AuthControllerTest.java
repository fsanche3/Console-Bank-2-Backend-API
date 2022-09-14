package com.dev.controllers.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.dev.controllers.AuthController;
import com.dev.model.BankUser;
import com.dev.models.dtos.LoginRequest;
import com.dev.services.BankUserService;
import com.dev.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = AuthController.class)
public class AuthControllerTest {
	
	@MockBean
	private BankUserService userServ;
	
	@MockBean
	private JwtUtil jwtUtil;
	
	@Autowired
	private MockMvc mockMvc;
	
	private ObjectMapper om = new ObjectMapper();
	
	@Test
	public void login() throws Exception {
		LoginRequest body = new LoginRequest("franklynBanking","password");
		BankUser user = new BankUser(0,"franklyn","franklynBanking","password","snail@test.snail");

		Mockito.when(userServ.verifyAuth(Mockito.any())).thenReturn(user);
		Mockito.when(jwtUtil.getJwtBuilder()).thenReturn(JWT.create());

		mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(body)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void cannotLogin() throws Exception {
		LoginRequest body = new LoginRequest("franklynBanking","password");
		BankUser user = null;

		Mockito.when(userServ.verifyAuth(Mockito.any())).thenReturn(user);
		Mockito.when(jwtUtil.getJwtBuilder()).thenReturn(JWT.create());

		mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(body)))
		.andExpect(status().isBadRequest());
	}

}
