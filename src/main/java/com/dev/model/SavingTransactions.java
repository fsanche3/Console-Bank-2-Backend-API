package com.dev.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="transactions_savings") @NoArgsConstructor @Data
public class SavingTransactions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String signal;
	private double total;
	private double transacted;
	@ManyToOne
	@JoinColumn(name = "savingsid", referencedColumnName = "id")
	@JsonIgnore
	private Saving savingsid;
	private Timestamp date;
}
