package com.dev.data;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dev.model.BankUser;

@Repository
public interface BankUserRepo extends JpaRepository<BankUser, Integer>{
	
	@Query(value = "SELECT c FROM BankUser c WHERE c.username = :username")	
	public List<BankUser> findByUsername(@Param("username") String username);



}
