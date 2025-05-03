package com.scope.ScopeIndia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scope.ScopeIndia.model.Country;
import com.scope.ScopeIndia.model.State;
import com.scope.ScopeIndia.repository.StateRepository;

@Service
public class StateService {
@Autowired
private StateRepository staterepository;
public List<State> getstate(){
	return staterepository.findAll();
	}
public List<State>getStateBy(Country countryid){
	return staterepository.findByCountry(countryid);
}
}