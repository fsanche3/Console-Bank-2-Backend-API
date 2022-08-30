package com.dev.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.dev.model.BankUser;
import com.dev.models.dtos.LoginRequest;
import com.dev.services.BankUserService;
import com.dev.utils.JwtUtil;


@RestController
@RequestMapping(path = "/login")
public class AuthController {

	private JwtUtil jwtUtil;
	private BankUserService userServ;
	
	public AuthController(JwtUtil jwtUtil, BankUserService userServ) {
		this.jwtUtil = jwtUtil;
		this.userServ = userServ;
	}
	
	@PostMapping()
	public ResponseEntity<String> loginPost(@RequestBody LoginRequest body) {
		
		String username = body.getUsername();
		String token = "";
		
		BankUser user = userServ.verifyAuth(body);
		
		if(user == null) {
			return new ResponseEntity<>("Wrong username or passowrd", HttpStatus.BAD_REQUEST);
		} 
		else {
			
			JWTCreator.Builder builder = jwtUtil.getJwtBuilder();
			Algorithm algorithm = Algorithm.HMAC256("franklyn");
			
			token = builder.withIssuer("auth0").withClaim("id", user.getId()).withClaim("name", user.getName())
					.withClaim("username", user.getUsername()).withClaim("email", user.getEmail())
					.sign(algorithm);
			
			return new ResponseEntity<>( "Token:"+ token , HttpStatus.ACCEPTED);

		}
	}
}
