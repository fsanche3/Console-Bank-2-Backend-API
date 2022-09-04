package com.dev.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="checkings") @Data @NoArgsConstructor
public class Checking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private double balance;	
	@ManyToOne
	@JoinColumn(name = "userid", referencedColumnName = "id")
	@JsonIgnore
	private BankUser user;
	private String name;
	private Timestamp creationdate;
	

	@OneToMany()
	@JoinColumn(name="checkingsid")
	@JsonIgnore
	private List<CheckingTransactions> checkTransactions;
	

	public Checking(int id, double balance, BankUser user, String name, Timestamp creationdate, List<CheckingTransactions> checkTransactions) {
		super();
		this.id = id;
		this.balance = balance;
		this.user = user;
		this.name = name;
		this.creationdate = creationdate;
		this.checkTransactions = new ArrayList<>();
	}


	public Checking(Checking checkingsid) {
		this.id =checkingsid.getId();
		this.balance = checkingsid.getBalance();
		this.user = checkingsid.getUser();
		this.name = checkingsid.getName();
		this.creationdate = checkingsid.getCreationdate();
		this.checkTransactions = checkingsid.getCheckTransactions();

	}
}
