package com.scope.ScopeIndia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scope.ScopeIndia.model.CourseModel;

public interface CourseRepository extends JpaRepository<CourseModel,Long>{

}
