package com.dev.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.model.Saving;
import com.dev.model.SavingTransaction;

@Repository
public interface SavingTransactionRepo extends JpaRepository<SavingTransaction, Integer>{

	List<SavingTransaction> findBySaving(Saving saving);
	
}
