package com.dev.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
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
@Table(name="savings") @AllArgsConstructor @NoArgsConstructor @Data
public class Saving {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private BigDecimal balance;
	private double intrestrate;
	private String name;
	private Timestamp creationdate;
	@ManyToOne
	@JoinColumn(name = "userid", referencedColumnName = "id")
	@JsonIgnore
	private BankUser user;
}
