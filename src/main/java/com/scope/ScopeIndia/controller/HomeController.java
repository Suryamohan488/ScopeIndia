package com.scope.ScopeIndia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scope.ScopeIndia.model.Register;

import jakarta.servlet.http.HttpSession;


@Controller
public class HomeController {
	@RequestMapping("home")
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
	@RequestMapping("about")
	public String about(HttpSession session,Model model) {
		 String userEmail = (String) session.getAttribute("userEmail");

		    if (userEmail != null) {
		        // User is logged in, show logged-in home page
		        model.addAttribute("loggedIn", true);
		    } else {
		        // User is not logged in
		        model.addAttribute("loggedIn", false);
		    }
		return "about";
	}
	@RequestMapping("contact")
	public String contact(HttpSession session,Model model) {
		 String userEmail = (String) session.getAttribute("userEmail");

		    if (userEmail != null) {
		        // User is logged in, show logged-in home page
		        model.addAttribute("loggedIn", true);
		    } else {
		        // User is not logged in
		        model.addAttribute("loggedIn", false);
		    }
		return "contact";
	}
	@RequestMapping("courses")
	public String courses(HttpSession session,Model model) {
		 String userEmail = (String) session.getAttribute("userEmail");

		    if (userEmail != null) {
		        // User is logged in, show logged-in home page
		        model.addAttribute("loggedIn", true);
		    } else {
		        // User is not logged in
		        model.addAttribute("loggedIn", false);
		    }
		return "courses";
	}
	@RequestMapping("success")
	public String sc(Model model) {
		model.addAttribute("success","Successfully verified");
		model.addAttribute("successdis","Your account has been successfully verified with SCOPE INDIA.");
		model.addAttribute("verify"," You will be redirected in a moment to create your new password.");
		return "success";
	}
	@RequestMapping("java")
	public String java(HttpSession session,Model model) {
		 String userEmail = (String) session.getAttribute("userEmail");

		    if (userEmail != null) {
		        // User is logged in, show logged-in home page
		        model.addAttribute("loggedIn", true);
		    } else {
		        // User is not logged in
		        model.addAttribute("loggedIn", false);
		    }
		return "java";
	}
	@RequestMapping("python")
	public String python(HttpSession session,Model model) {
		String userEmail = (String) session.getAttribute("userEmail");
		
		if (userEmail != null) {
			// User is logged in, show logged-in home page
			model.addAttribute("loggedIn", true);
		} else {
			// User is not logged in
			model.addAttribute("loggedIn", false);
		}
		return "python";
	}
	@RequestMapping("aws")
	public String aws(HttpSession session,Model model) {
		String userEmail = (String) session.getAttribute("userEmail");
		
		if (userEmail != null) {
			// User is logged in, show logged-in home page
			model.addAttribute("loggedIn", true);
		} else {
			// User is not logged in
			model.addAttribute("loggedIn", false);
		}
		return "aws";
	}
	@RequestMapping("dotnet")
	public String dotnet(HttpSession session,Model model) {
		String userEmail = (String) session.getAttribute("userEmail");
		
		if (userEmail != null) {
			// User is logged in, show logged-in home page
			model.addAttribute("loggedIn", true);
		} else {
			// User is not logged in
			model.addAttribute("loggedIn", false);
		}
		return "dotnet";
	}
	@RequestMapping("flutter")
	public String flutter(HttpSession session,Model model) {
		String userEmail = (String) session.getAttribute("userEmail");
		
		if (userEmail != null) {
			// User is logged in, show logged-in home page
			model.addAttribute("loggedIn", true);
		} else {
			// User is not logged in
			model.addAttribute("loggedIn", false);
		}
		return "flutter";
	}
	@RequestMapping("php")
	public String php(HttpSession session,Model model) {
		String userEmail = (String) session.getAttribute("userEmail");
		
		if (userEmail != null) {
			// User is logged in, show logged-in home page
			model.addAttribute("loggedIn", true);
		} else {
			// User is not logged in
			model.addAttribute("loggedIn", false);
		}
		return "php";
	}
	@RequestMapping("mern")
	public String mern(HttpSession session,Model model) {
		String userEmail = (String) session.getAttribute("userEmail");
		
		if (userEmail != null) {
			// User is logged in, show logged-in home page
			model.addAttribute("loggedIn", true);
		} else {
			// User is not logged in
			model.addAttribute("loggedIn", false);
		}
		return "mern";
	}
	@RequestMapping("mean")
	public String mean(HttpSession session,Model model) {
		String userEmail = (String) session.getAttribute("userEmail");
		
		if (userEmail != null) {
			// User is logged in, show logged-in home page
			model.addAttribute("loggedIn", true);
		} else {
			// User is not logged in
			model.addAttribute("loggedIn", false);
		}
		return "mean";
	}
	
}
