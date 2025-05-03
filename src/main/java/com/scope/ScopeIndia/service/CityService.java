package com.scope.ScopeIndia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scope.ScopeIndia.model.City;
import com.scope.ScopeIndia.model.State;
import com.scope.ScopeIndia.repository.CityRepository;


@Service
public class CityService {
@Autowired
private CityRepository cityrepository;
public List<City>getcity(){
	return cityrepository.findAll();

}
public List<City>getCityBy(State stateid){
	return cityrepository.findByState(stateid);
}
}
