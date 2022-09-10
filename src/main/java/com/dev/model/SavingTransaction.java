package com.dev.model;

import java.math.BigDecimal;
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
@Table(name = "transactions_savings") @AllArgsConstructor @NoArgsConstructor @Data
public class SavingTransaction {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	private String signal;
	private BigDecimal total;
	private BigDecimal transacted;
	private Timestamp date;
	@ManyToOne
	@JoinColumn(name = "savingsid", referencedColumnName = "id")
	private Saving saving;

}
