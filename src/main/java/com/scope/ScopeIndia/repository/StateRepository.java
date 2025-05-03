package com.scope.ScopeIndia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scope.ScopeIndia.model.Country;
import com.scope.ScopeIndia.model.State;

public interface StateRepository extends JpaRepository<State,Integer> {
List<State>findByCountry(Country countryid);
}