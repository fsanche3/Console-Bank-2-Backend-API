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
@Table(name="savings") @AllArgsConstructor @NoArgsConstructor @Data
public class Saving {

	private int id;
	private double balance;
	private double rate;
	private Timestamp date;
	@ManyToOne
	@JoinColumn(name = "userid", referencedColumnName = "id")
	private BankUser user;
}
