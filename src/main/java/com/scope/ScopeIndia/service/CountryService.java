package com.scope.ScopeIndia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scope.ScopeIndia.model.Country;
import com.scope.ScopeIndia.repository.CountryRepository;

@Service
public class CountryService {
@Autowired
private CountryRepository countryrepository;
public List<Country>countrylist(){
	return countryrepository.findAll();
}
public Optional<Country> getById(int id) {
	return countryrepository.findById(id);
}
}
