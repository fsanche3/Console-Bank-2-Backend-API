package com.dev.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.model.Checking;


@Repository
public interface CheckingRepo extends JpaRepository<Checking, Integer> {

	List<Checking> findByName(String name);
}
