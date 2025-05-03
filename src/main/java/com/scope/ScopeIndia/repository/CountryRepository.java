package com.scope.ScopeIndia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scope.ScopeIndia.model.Country;

public interface CountryRepository extends JpaRepository<Country,Integer>{
}
