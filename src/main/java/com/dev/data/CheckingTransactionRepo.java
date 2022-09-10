package com.dev.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.model.Checking;
import com.dev.model.CheckingTransaction;


@Repository
public interface CheckingTransactionRepo extends JpaRepository<CheckingTransaction, Integer> {

	List<CheckingTransaction> findByChecking(Checking checking);
}
