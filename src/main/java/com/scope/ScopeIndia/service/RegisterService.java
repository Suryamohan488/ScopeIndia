package com.scope.ScopeIndia.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.scope.ScopeIndia.model.Register;
import com.scope.ScopeIndia.repository.RegisterRepository;
import com.scope.ScopeIndia.repository.UpdateRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import net.bytebuddy.utility.RandomString;



@Service
public class RegisterService {
	@Autowired
	private JavaMailSender sender;
	@Autowired
	private RegisterRepository registerrepository;
	@Autowired
	private UpdateRepository updaterepository;
	private String folder="src/main/resources/static/images/";
	 public void insertData(Register mail, String siteUrl,MultipartFile file) throws MessagingException, IOException { 
		// Ensure the upload directory exists
	        Path uploadPath = Paths.get(folder);
	        if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	        }

	        // Save the file
	        String filename = file.getOriginalFilename();
	        Path path = uploadPath.resolve(filename);
	        Files.write(path, file.getBytes());
	        mail.setFilepath("/" + filename); // Save the full path
		 
	        
	        String randomCode = RandomString.make(64); 
	        mail.setVerificationcode(randomCode); 
	        mail.setEnabled(false); 
	        registerrepository.save(mail); 
	        sendVerificationEmail(mail, siteUrl);
	        
	    }

	    public void sendVerificationEmail(Register reg, String siteUrl) throws MessagingException, UnsupportedEncodingException { 
	    	 
	    	
	    	String toAddr = reg.getEmail(); 
	        String fromAddr = "suryashaji488@gmail.com"; 
	        String senderName = "Scopeindia"; 
	        String subject = "Verify Registration";  
	        String message = "Dear [[name]], please click the link below to verify: <h3><a href=\"[[URL]]\" target=\"_blank\">VERIFY</a></h3>";
	        
	        MimeMessage msg = sender.createMimeMessage(); 
	        MimeMessageHelper messageHelper = new MimeMessageHelper(msg);
	        messageHelper.setFrom(fromAddr, senderName); 
	        messageHelper.setTo(toAddr);  
	        messageHelper.setSubject(subject); 
	        
	        message = message.replace("[[name]]", reg.getFname()+""+reg.getLname()); 
	        String url = siteUrl + "/verify?code=" + reg.getVerificationcode();
	        message = message.replace("[[URL]]", url); 
	        messageHelper.setText(message, true); 
	        
	        sender.send(msg);
	    }
	    
	    public boolean verify(String verificationcode) { 
	    	Register mail = registerrepository.findByVerificationcode(verificationcode);
	        
	        if (mail == null || mail.isEnabled()) {
	            return false;
	        } else { 
	        	mail.setVerificationcode(null); 
	        	mail.setEnabled(true); 
	        	registerrepository.save(mail); 
	            return true;
	        }
	    }
	    public void sendEmail(String email, String OTP)throws MessagingException, UnsupportedEncodingException {
	    	System.out.println("otp is "+OTP);

	    	String fromaddr="suryashaji488@gmail.com";
	    	String senderName="Scopeindia";
	    	String subject="OTP Sending";
	    	String message="Please check your otp: [[OTP]]";
	    	MimeMessage msg=sender.createMimeMessage();
	    	MimeMessageHelper messageHelper=new MimeMessageHelper(msg);
	    	
	    	messageHelper.setFrom(fromaddr,senderName);
	    	messageHelper.setTo(email);
	    	
	    	message=message.replace("[[OTP]]",OTP);
	    	messageHelper.setText(message,true);
	    	sender.send(msg);
	    }
		
	    public boolean checkEnabled() {
	    	
	     boolean enable=registerrepository.findByEnabledTrue();
	     if(!enable) {
	    	 return false;
	     }
	     return true;
	    }

public String getCountryNameByRegistrationId(int registrationId) {
    Register registration = registerrepository.findById(registrationId)
        .orElseThrow(() -> new EntityNotFoundException("Registration not found with ID: " + registrationId));
    
    // Ensure country is loaded (if using LAZY fetching)
    String countryName = registration.getCountry() != null ? registration.getCountry().getCountryname() : "No Country Assigned";
    
    return countryName;
}

public Register findByEmail(String email) {
    return registerrepository.findByEmail(email);
}
public Register getRegistrationByEmail(String email) {
    return registerrepository.findByEmail(email);
}
public void updateUser(Register user, String email, MultipartFile file) throws IOException {
    Path uploadPath = Paths.get(folder);
    if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
    }

    // Save the file
    String filename = StringUtils.cleanPath(file.getOriginalFilename());
    Path path = uploadPath.resolve(filename);
    Files.write(path, file.getBytes());
    
    // Update file path and save user
    user.setFilepath("/" + filename);

    if (registerrepository.existsByEmail(email)) {
        user.setEmail(email);
        registerrepository.save(user);  // This will update the user if the ID already exists
    }
}




public Register getUserByEmail(String email) {
	return updaterepository.findByEmail(email);
}
//In RegistrationService
public void updatePassword(Register user, String newPassword)  {
	
 user.setPassword(newPassword);
 registerrepository.save(user);
}





}
