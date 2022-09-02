package com.dev.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="savings") @AllArgsConstructor @NoArgsConstructor @Data
public class Saving {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private double balance;
	private double intrestrate;
	@ManyToOne
	@JoinColumn(name = "userid", referencedColumnName = "id")
	private BankUser user;
	private Timestamp creationdate;

}
