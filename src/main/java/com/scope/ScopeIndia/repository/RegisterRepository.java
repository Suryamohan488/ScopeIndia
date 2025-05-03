package com.scope.ScopeIndia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scope.ScopeIndia.model.Register;

	@Repository
	public interface RegisterRepository extends CrudRepository <Register,Integer> {
		public Register findByVerificationcode(String code);
		public Register findByEmail(String email);
		public Register findByEmailAndPassword(String email,String password);
		public boolean findByEnabledTrue();
		public boolean existsByEmail(String email);
	}

