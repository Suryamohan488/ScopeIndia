package com.scope.ScopeIndia.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.scope.ScopeIndia.model.City;
import com.scope.ScopeIndia.model.Country;
import com.scope.ScopeIndia.model.CourseModel;
import com.scope.ScopeIndia.model.Register;
import com.scope.ScopeIndia.model.State;
import com.scope.ScopeIndia.repository.CountryRepository;
import com.scope.ScopeIndia.repository.RegisterRepository;
import com.scope.ScopeIndia.service.CityService;
import com.scope.ScopeIndia.service.CountryService;
import com.scope.ScopeIndia.service.CourseService;
import com.scope.ScopeIndia.service.RegisterService;
import com.scope.ScopeIndia.service.StateService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;




@Controller
public class RegisterController {
	@Autowired
	private RegisterService registerservice;
	@Autowired
	private CourseService courseservice;
	@Autowired
	private CountryService countryservice;
	@Autowired
	private StateService stateservice;
	@Autowired
	private CityService cityservice;
	
	@Autowired
	private CountryRepository repository;
	@Autowired
	private RegisterRepository registrationrepository;

@RequestMapping("")
public String home1(HttpSession session, Model model) {
    // Assuming "userEmail" is stored in session upon login
    String userEmail = (String) session.getAttribute("userEmail");

    if (userEmail != null) {
        // User is logged in, show logged-in home page
        model.addAttribute("loggedIn", true);
    } else {
        // User is not logged in
        model.addAttribute("loggedIn", false);
    }
    
	model.addAttribute("register",new Register());
	return "home";
	
}
	@RequestMapping("register")
	public String form(Model model) {
		model.addAttribute("countries",countryservice.countrylist());
		model.addAttribute("registeruser",new Register());
		return "register";
	}
	@RequestMapping("send")
	public String send(@Valid @ModelAttribute("registeruser")Register reg,@RequestParam("image") MultipartFile file, BindingResult result,Model model,HttpServletRequest request) throws MessagingException, IOException {
		Register existingemail=registrationrepository.findByEmail(reg.getEmail());
		if (file.isEmpty()) {
	            result.rejectValue("file", "error.file", "*Please select an image to upload");
	        }
		 if (existingemail!= null) {
		        result.rejectValue("email", "error.email", "*Email already registered");
		    }

		if(result.hasErrors()) {
			model.addAttribute("countries", countryservice.countrylist());
			return "register";
			}else {
			registerservice.insertData(reg, getSiteURL(request),file);
			model.addAttribute("registeruser",reg);
			model.addAttribute("success","Successfully Registered");
			model.addAttribute("successdis","Your account has been successfully registered with SCOPE INDIA.");
			model.addAttribute("verification","A verification email has been sent to your email address; please verify your account if you have not done so already.");
			return "success";
			}
	}
	
	
	private String getSiteURL(HttpServletRequest request) {
		String siteurl=request.getRequestURL().toString();
		return siteurl.replace(request.getServletPath(), "");
	}
	@RequestMapping("/verify")
	public String verify(@Param("code")String code,Model model) {
		if(registerservice.verify(code)) {
			model.addAttribute("success","Successfully verified");
			model.addAttribute("successdis","Your account has been successfully verified with SCOPE INDIA.");
			model.addAttribute("verify","You will be redirected shortly to set your new password.");
			return "success";
		}else {
		return "error";
		}
		}
	@GetMapping("/countries")
	public List<Country> GetCountries(){
		return repository.findAll();	
	}
	@GetMapping("/states/{countryid}")
	public @ResponseBody Iterable<State>getStateByCountry(@PathVariable Country countryid){
		return stateservice.getStateBy(countryid);	
	}
	@GetMapping("/cities/{stateid}")
	public @ResponseBody Iterable<City>getCityByState(@PathVariable State stateid){
		return cityservice.getCityBy(stateid);	
	}
	@GetMapping("/send-otp")
	public String sen(Model model) {
	model.addAttribute("register",new Register());
	return "send-otp";
	}
	@PostMapping("/send-otp")
	public String sendOtp(Model model,@RequestParam("email")String email,Register register) throws UnsupportedEncodingException, MessagingException {
		Register existingregister=registrationrepository.findByEmail(email);
		if(existingregister!=null) {
			String newOtp=generateRandomOtp();
			existingregister.setOTP(newOtp);
			registrationrepository.save(existingregister);
			registerservice.sendEmail(email,newOtp);
			model.addAttribute("email",email);
			return "verify-otp";
		}else {
			model.addAttribute("error","*Email not found.please register first.");
			return "register";
		}
		}


	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam("email")String email,@RequestParam("OTP")String enteredOTP,Model model) {
		Register register=registrationrepository.findByEmail(email);
		if(register!=null&&register.getOTP()!=null&&register.getOTP().equals(enteredOTP)) {
			register.setVerified(true);
			registrationrepository.save(register);
			model.addAttribute("email",email);
			return "set-new-password";
		}else {
			model.addAttribute("email","Invalid OTP.Please try again.");
			return "verify-otp";
		}
		
		
		
	}
	private String generateRandomOtp() {
		String OTP=String.valueOf(new Random().nextInt(900000)+100000);
		return OTP;
	}
	@PostMapping("/set-new-password")
	public String setNewPassword(@RequestParam("email") String email, 
	                             @RequestParam("password") String newPassword, 
	                             Model model) {

	   
	    Register register = registrationrepository.findByEmail(email);
	    
	    if (register != null) {
	       
	        register.setPassword(newPassword); 
	        registrationrepository.save(register);
	        
	        model.addAttribute("message", "Password has been successfully updated.");
	        return "login"; // Redirect to the login page or success page
	    } else {
	        model.addAttribute("error", "Error updating password. User not found.");
	        return "set-new-password";
	    }
	}
	@RequestMapping("/login")
	public String lo(Model model) {
		return "login";
	}
	@PostMapping("/login")
	public String login(@RequestParam("email")String email,@RequestParam("password")String password,@RequestParam(value = "cookies", required = false) String loggedIn,HttpServletResponse response, HttpServletRequest request,HttpSession session,Model model) {
		Register register=registrationrepository.findByEmailAndPassword(email,password);
		if(register!=null) {
			if (loggedIn != null) {
	            // "Keep me logged in" selected, set cookie and don't set session
	            Cookie ck = new Cookie("username", email);
	            ck.setMaxAge(60 *60 *24 ); // Set cookie for 1 minute
	            ck.setPath("/");
	            response.addCookie(ck);
	        } 
			session.setAttribute("userEmail", email);
	        // Redirect to /dashboard after successful login
	        return "redirect:/dashboard";
		}else {
			model.addAttribute("error","Invalid email or password");
			return "login";
		}
		
		
		
	}
	
	@GetMapping("/dashboard")
	public String dashboard(HttpServletRequest request, HttpServletResponse response,HttpSession session, Model model) {
	   
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);

	    // Check if the cookie exists and is valid
	    Cookie[] cookies = request.getCookies();
	    String email=null;
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("username".equals(cookie.getName())) {
	            	 email = cookie.getValue(); // Extract email from the cookie
	                 break;
	            }
	        }
	    }
		
		 				
	    if (email != null) {
	        // Fetch user details from the database using the email
	        Register user = registrationrepository.findByEmail(email);
	        if (user != null) {
	        	
	        	String countryName = registerservice.getCountryNameByRegistrationId(user.getUserid());
	        	model.addAttribute("country",countryName);
	        	
	        	
	            model.addAttribute("user", user);  // Pass user data to the view
	            

	            model.addAttribute("availableCourses", courseservice.courselist()); // Add course list to the model
	         
	         // Fetch available courses
		         List<CourseModel> availableCourses = courseservice.courselist();
		         model.addAttribute("availableCourses", availableCourses);
		         
		         // Retrieve picked courses from session
		       
				List<CourseModel> pickedCourses = (List<CourseModel>) session.getAttribute("pickedCourses");
		         if (pickedCourses == null) {
		             pickedCourses = new ArrayList<>();
		         }
		         model.addAttribute("pickedCourses", pickedCourses);
	           
	            return "dashboard"; 
	        }
	    	
		}
	    model.addAttribute("error", "Session expired, please log in again");
	    return "login";
	}
	


	@PostMapping("/signup-course")
	public String course(HttpSession session, Model model, @RequestParam("Course") String course) {
	    String username = (String) session.getAttribute("userEmail");
	    Register regform = registrationrepository.findByEmail(username);
	    
	    if (regform != null) {
	        regform.setCourse(course);
	        registrationrepository.save(regform);
	        
	        // Add picked course name to model
	        model.addAttribute("pickedCourseName", course);
	        
	        return "redirect:/dashboard";  // Make sure this redirect goes back to the course page
	    } else {
	        return "Error";
	    }
	}

//	change-password
	 @RequestMapping("/change-password")
		public String changepassword(Model model) {
		model.addAttribute("change-password",new Register());
	        return "change-password";
	        		}
	 
	 @PostMapping("/change-password")
	 public String changePassword(@RequestParam("currentPassword") String currentPassword,
	                              @RequestParam("newPassword") String newPassword,
	                              @RequestParam("confirmPassword") String confirmPassword,
	                              HttpServletRequest request, Model model) {

	     // Get the logged-in user's email from session or cookie
	     String email = getEmailFromSessionOrCookie(request);
	     
	     // Fetch the user by email
	     Register user = registrationrepository.findByEmail(email);

	     if (user != null) {
	         // Verify if the current password matches the stored one
	         if (!user.getPassword().equals(currentPassword)) {
	             model.addAttribute("message", "Current password is incorrect.");
	             return "change-password"; // Show form with error message
	         }

	         // Check if new password matches confirm password
	         if (!newPassword.equals(confirmPassword)) {
	             model.addAttribute("message", "New password and confirm password do not match.");
	             return "change-password";
	         }

	         // Update the password and save
	         user.setPassword(newPassword);
	         registrationrepository.save(user);

	         model.addAttribute("message", "Password updated successfully!");
	         return "login";
	     }

	     model.addAttribute("message", "User not found.");
	     return "change-password";
	 }
	 private String getEmailFromSessionOrCookie(HttpServletRequest request) {
		    // Check cookies for email
		    Cookie[] cookies = request.getCookies();
		    if (cookies != null) {
		        for (Cookie cookie : cookies) {
		            if ("username".equals(cookie.getName())) {
		                return cookie.getValue(); // Return email from cookie
		            }
		        }
		    }
		    // If no cookie, return null or handle session-based login logic
		    return null;
		}
	 @PostMapping("/update-password")
	 public String updatePassword(@RequestParam("email") String email, 
	                              @RequestParam("password") String newPassword, 
	                              HttpServletRequest request, 
	                              HttpServletResponse response, 
	                              Model model) {

	     Register register = registrationrepository.findByEmail(email);
	     
	     if (register != null) {
	         // Update password
	         register.setPassword(newPassword);
	         registrationrepository.save(register);
	         
	         // Invalidate the session to log the user out
	         request.getSession().invalidate();
	         
	         // Clear authentication cookies (optional)
	         Cookie[] cookies = request.getCookies();
	         if (cookies != null) {
	             for (Cookie cookie : cookies) {
	                 if ("username".equals(cookie.getName())) {
	                     cookie.setMaxAge(0);  // Expire the cookie
	                     cookie.setPath("/");  // Set the path to match the original cookie
	                     response.addCookie(cookie);
	                 }
	             }
	         }
	         
	         // Redirect to the login page after successful password update and auto-logout
	         return "redirect:/login";  
	     } else {
	         model.addAttribute("error", "Error updating password. User not found.");
	         return "set-new-password";
	     }
	 }
	
	 @GetMapping("/logout")
     public String logout(HttpServletRequest request, HttpServletResponse response) {
         // Invalidate the session
         HttpSession session = request.getSession(false);
         if (session != null) {
             session.invalidate();  // Invalidate the session
         }

         // Remove cookies by setting their max age to 0
         Cookie[] cookies = request.getCookies();
         if (cookies != null) {
             for (Cookie cookie : cookies) {
                 cookie.setMaxAge(0);
                 cookie.setValue(null);  // Clear the value (optional)
                 cookie.setPath("/");     // Ensure the path matches
                 response.addCookie(cookie);  // Add the updated cookie back to the response
             }
         }

         // Redirect to login page after logout
         return "redirect:/login";
     }
	 
		// Edit and update
	 @RequestMapping("/profileEdit")
		public String showpage(Model model) {
			model.addAttribute("profileEdit",new Register());
			return "profileEdit";
		}

		
		    @GetMapping("/updateProfile")
		    public String showProfile(HttpSession session, Model model) {
		        // Get user email from session
		        String userEmail = (String) session.getAttribute("userEmail");

		        if (userEmail != null) {
//		        	
		        	
	            
		            // Fetch user details from the database using email
		            Register user = registrationrepository.findByEmail(userEmail);
		            
		            if (user != null) {
		                System.out.println("File path: " + user.getFilepath()); // Debugging output

		            	
		            	List<Country> countries = countryservice.countrylist();    
		                List<State> states = stateservice.getStateBy(user.getCountry());
		                List<City> cities = cityservice.getCityBy(user.getState());
		                
		             // Add them to the model
		                model.addAttribute("countries", countries);
		                model.addAttribute("states", states);
		                model.addAttribute("cities", cities);

		                // Add selected IDs to the model
		                model.addAttribute("selectedCountryId", user.getCountry().getCountryid());
		                model.addAttribute("selectedStateId", user.getState().getStateid());
		                model.addAttribute("selectedCityId", user.getCity().getCityid());
		                model.addAttribute("user", user);
		                return "profileEdit"; 
		            }
		        }

		        return "redirect:/login";
		    }

		    // Method to save updated user details (POST request)
		    @PostMapping("/updateProfile")
		    public String updateProfile(@Valid @ModelAttribute("user") Register updatedUser,
		                                @RequestParam("fileupload") MultipartFile file,
		                                BindingResult result, HttpSession session) throws IOException {
		        // Get the user email from session
		        String userEmail = (String) session.getAttribute("userEmail");
		        
		        if (userEmail != null && !result.hasErrors()) {
		            Register existingUser = registrationrepository.findByEmail(userEmail);

		            if (existingUser != null) {
		                // Update user fields except file path here
		                existingUser.setFname(updatedUser.getFname());
		                existingUser.setLname(updatedUser.getLname());
		                existingUser.setGender(updatedUser.getGender());
		                existingUser.setDob(updatedUser.getDob());
		                existingUser.setPhonenum(updatedUser.getPhonenum());
		                existingUser.setCountry(updatedUser.getCountry());
		                existingUser.setState(updatedUser.getState());
		                existingUser.setCity(updatedUser.getCity());
		                existingUser.setHobbies(updatedUser.getHobbies());

		                // Update file only if a new one is uploaded
		                if (!file.isEmpty()) {
		                    // Call the service method to handle the file saving logic and update the avatar
		                    registerservice.updateUser(existingUser, userEmail, file);
		                }

		                // Save the updated user to the repository
		                registrationrepository.save(existingUser);
		            }
		        }

		        return "redirect:/dashboard";  // Redirect to the dashboard after updating
		    }



}
