package com.scope.ScopeIndia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scope.ScopeIndia.model.CourseModel;
import com.scope.ScopeIndia.model.Register;
import com.scope.ScopeIndia.repository.CourseRepository;
import com.scope.ScopeIndia.repository.RegisterRepository;

@Service
public class CourseService {
	@Autowired
	private CourseRepository courserepository;
	@Autowired
	private RegisterRepository registrationrepository;
	public List<CourseModel>courselist(){
		return courserepository.findAll();
	}
	public List<Register> getAllStudents() {
        return (List<Register>) registrationrepository.findAll(); // Fetch all students from the DB
    }
	public boolean signupForCourse(Long courseId) {
		 Optional<CourseModel> courseOpt = courserepository.findById(courseId);
	        if (courseOpt.isPresent()) {
	            CourseModel course = courseOpt.get();
	           
	            return true;
	        } 
		return false;
	}
	  public CourseModel getCourseById(Long id) {
	        return courserepository.findById(id).orElse(null);
	    }

}
