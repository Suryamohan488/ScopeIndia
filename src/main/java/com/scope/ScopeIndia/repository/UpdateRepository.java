package com.scope.ScopeIndia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scope.ScopeIndia.model.Register;
@Repository
public interface UpdateRepository extends JpaRepository<Register, Integer>{
	public Register findByEmail(String email);
}
