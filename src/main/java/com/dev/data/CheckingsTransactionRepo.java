package com.dev.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.model.CheckingTransactions;

@Repository
public interface CheckingsTransactionRepo extends JpaRepository<CheckingTransactions, Integer>{

}
