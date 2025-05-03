package com.scope.ScopeIndia.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="scoperegisterform")
public class Register {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="id")
	private int userid;
	@NotBlank
	private String fname;
	private String lname;
	private String gender;
//	@DateTimeFormat(pattern="dd/MM/yyyy")
	private String dob;
	@Email
	@NotBlank
	private String email;
	private long phonenum;
	@ManyToOne
	@JoinColumn(name="country")
	private Country country;
	

	@ManyToOne
	@JoinColumn(name="state")
	private State state;
	

	@ManyToOne
	@JoinColumn(name="city")
	private City city;
	
	@ElementCollection
    @CollectionTable(name = "user_hobbies", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "hobby")
    private List<String> hobbies;
	private String verificationcode;
	private boolean enabled;

    private String filepath; // Store the full path
    public String OTP;
    private String password;
    private String Course;
    

	public Register(int userid, @NotBlank String fname, String lname, String gender, String dob,
			@Email @NotBlank String email, long phonenum, Country country, State state, City city, List<String> hobbies,
			String verificationcode, boolean enabled, String filepath, String oTP, String password, String course,
			MultipartFile fileupload) {
		super();
		this.userid = userid;
		this.fname = fname;
		this.lname = lname;
		this.gender = gender;
		this.dob = dob;
		this.email = email;
		this.phonenum = phonenum;
		this.country = country;
		this.state = state;
		this.city = city;
		this.hobbies = hobbies;
		this.verificationcode = verificationcode;
		this.enabled = enabled;
		this.filepath = filepath;
		OTP = oTP;
		this.password = password;
		Course = course;
		this.fileupload = fileupload;
	}
	public String getCourse() {
		return Course;
	}
	public void setCourse(String course) {
		Course = course;
	}
	public String getOTP() {
		return OTP;
	}
	public void setOTP(String oTP) {
		OTP = oTP;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	@Transient
	private MultipartFile fileupload;

    // Getters and setters
    public MultipartFile getFileupload() {
        return fileupload;
    }

    public void setFileupload(MultipartFile fileupload) {
        this.fileupload = fileupload;
    }	
    
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getVerificationcode() {
		return verificationcode;
	}
	public void setVerificationcode(String verificationcode) {
		this.verificationcode = verificationcode;
	}
	public Register() {}
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(long phonenum) {
		this.phonenum = phonenum;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public List<String> getHobbies() {
		return hobbies;
	}
	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
	}
	public void setVerified(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
