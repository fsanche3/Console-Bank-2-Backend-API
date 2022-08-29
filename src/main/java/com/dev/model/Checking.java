package com.dev.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="checkings") @AllArgsConstructor @NoArgsConstructor @Data
public class Checking {

	private int id;
	private Timestamp date;
	private double balance;
	@ManyToOne
	@JoinColumn(name = "userid", referencedColumnName = "id")
	private BankUser user;
	
}
