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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "transactions_checkings") @AllArgsConstructor @NoArgsConstructor @Data
public class CheckingTransaction {
	
	@Id // specifies that this field is the primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // specifies that the db generates this value
	private int id;
	private String signal;
	private BigDecimal total;
	private BigDecimal transacted;
	private Timestamp date;
	@ManyToOne
	@JoinColumn(name = "checkingsid", referencedColumnName = "id")
	private Checking checking;

}
