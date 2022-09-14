package com.dev.services.test;

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
import com.dev.data.BankUserRepo;
import com.dev.model.BankUser;
import com.dev.models.dtos.LoginRequest;
import com.dev.services.BankUserService;

@SpringBootTest(classes = ConsoleBankApplication.class)
public class BankUserServiceTest {

	@MockBean 
	private BankUserRepo userRepo;
	
	@Autowired 
	BankUserService userServ;
	
	@Test
	public void verifyRegistration() {
		List<BankUser> list = new ArrayList<BankUser>();
		BankUser user = new BankUser(0,"franklyn","franklynBanking","password","snail@test.snail");
		
		Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(list);
		Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);
		
		Assertions.assertEquals(userServ.verifyRegistration(user), true);
			
	}
	
	@Test
	public void cannotVerifyRegistration() {
		List<BankUser> list = new ArrayList<BankUser>();
		BankUser user = new BankUser(0,"franklyn","franklynBanking","password","snail@test.snail");
		list.add(user);
		
		Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(list);
		Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);
		
		Assertions.assertEquals(userServ.verifyRegistration(user), false);
	}
	
	@Test
	public void verifyAuth() {
		List<BankUser> list = new ArrayList<>();
		LoginRequest body = new LoginRequest("franklynBanking","password");
		BankUser user = new BankUser(0,"franklyn","franklynBanking","password","snail@test.snail");

		list.add(user);
		
		Mockito.when(userRepo.findByUsername(Mockito.anyString())).thenReturn(list);
		
		Assertions.assertEquals(userServ.verifyAuth(body), user);
	}
	
	@Test
	public void cannotVerifyUsernameAuth() {
		List<BankUser> list = new ArrayList<>();
		LoginRequest body = new LoginRequest("franklynBanking","password");
		BankUser user = new BankUser(0,"franklyn","franklynanking","password","snail@test.snail");
		
		Mockito.when(userRepo.findByUsername(Mockito.anyString())).thenReturn(list);
		
		Assertions.assertEquals(userServ.verifyAuth(body), null);
	}
	
	@Test
	public void cannotVerifyPasswordAuth() {
		List<BankUser> list = new ArrayList<>();
		LoginRequest body = new LoginRequest("franklynBanking","password");
		BankUser user = new BankUser(0,"franklyn","franklynBanking","pword","snail@test.snail");

		list.add(user);
		
		Mockito.when(userRepo.findByUsername(Mockito.anyString())).thenReturn(list);
		
		Assertions.assertEquals(userServ.verifyAuth(body), null);
	}
	
	@Test
	public void findById() {
		BankUser user = new BankUser(0,"franklyn","franklynBanking","password","snail@test.snail");
		Optional<BankUser> opt = Optional.of(user);
		
		Mockito.when(userRepo.findById(Mockito.anyInt())).thenReturn(opt);
		
		Assertions.assertEquals(userServ.findById(Mockito.anyInt()), opt);
	}
	
	@Test
	public void upsert() {
		Mockito.when(userRepo.save(Mockito.any())).thenReturn(new BankUser());
		userServ.upsert(new BankUser());
	}
}
