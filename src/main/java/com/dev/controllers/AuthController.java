package com.dev.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.dev.data.BankUserRepo;
import com.dev.model.BankUser;
import com.dev.models.dtos.LoginRequest;
import com.dev.services.BankUserService;
import com.dev.utils.JwtUtil;

@RestController
@RequestMapping(path = "/login")
public class AuthController {

	private JwtUtil jwtUtil;
	private BankUserService userServ;
	private BankUserRepo repo;

	public AuthController(JwtUtil jwtUtil, BankUserService userServ, BankUserRepo repo) {
		this.jwtUtil = jwtUtil;
		this.userServ = userServ;
		this.repo = repo;
	}

	@PostMapping()
	public ResponseEntity<BankUser> loginPost(@RequestBody LoginRequest body) {

		String token = "";

		BankUser user = userServ.verifyAuth(body);
		
		if (user != null) {
			
			JWTCreator.Builder builder = jwtUtil.getJwtBuilder();
			Algorithm algorithm = Algorithm.HMAC256("franklyn");

			token = builder.withIssuer("auth0").withClaim("id", user.getId()).withClaim("name", user.getName())
					.withClaim("username", user.getUsername()).withClaim("email", user.getEmail()).sign(algorithm);
			
			return ResponseEntity.status(200).header("Auth", token).body(user);
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		}

	}
}
