package com.dev.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="bankuser") @NoArgsConstructor @Data
public class BankUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String username;
	private String password;
	private String email;
	
	@OneToMany
	@JoinColumn(name="userid")
	@JsonIgnore
	private List<Checking> checkAccounts;
	
	@OneToMany
	@JoinColumn(name="userid")
	@JsonIgnore
	private List<Saving> savAccounts;
	

	public BankUser(int id, String name, String username, String password, String email) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.checkAccounts = new ArrayList<>();
		this.savAccounts = new ArrayList<>();

	}

}
