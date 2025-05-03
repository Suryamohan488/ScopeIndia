package com.scope.ScopeIndia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scope.ScopeIndia.model.City;
import com.scope.ScopeIndia.model.State;

public interface CityRepository extends JpaRepository<City,Integer>{
	List<City>findByState(State stateid);
}
