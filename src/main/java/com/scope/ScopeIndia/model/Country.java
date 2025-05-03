package com.scope.ScopeIndia.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Country {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int countryid;
private String countryname;
public int getCountryid() {
	return countryid;
}
public void setCountryid(int countryid) {
	this.countryid = countryid;
}
public String getCountryname() {
	return countryname;
}
public void setCountryname(String countryname) {
	this.countryname = countryname;
}
@Override
public String toString() {
    return countryname;
}

}