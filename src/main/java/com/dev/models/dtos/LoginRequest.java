package com.dev.models.dtos;

import java.util.List;

import com.dev.model.BankUser;
import com.dev.model.Checking;
import com.dev.model.Saving;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @Data @AllArgsConstructor
public class LoginRequest {

	private String username;
	private String password;
}
