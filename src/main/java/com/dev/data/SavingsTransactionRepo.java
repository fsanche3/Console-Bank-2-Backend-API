package com.dev.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.model.SavingTransactions;

@Repository
public interface SavingsTransactionRepo extends JpaRepository<SavingTransactions, Integer>{

}
