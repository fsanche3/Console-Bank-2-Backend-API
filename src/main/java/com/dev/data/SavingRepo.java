package com.dev.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.model.Saving;

@Repository
public interface SavingRepo extends JpaRepository<Saving, Integer> {

	List<Saving> findByName(String name);
	
	List<Saving> findByUserId(int userid);
}
