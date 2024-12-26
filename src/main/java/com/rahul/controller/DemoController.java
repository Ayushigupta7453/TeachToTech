package com.rahul.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.rahul.model.Category;
import com.rahul.model.Course;
import com.rahul.model.Trainer;
import com.rahul.service.CategoryService;
import com.rahul.service.CourseService;
import com.rahul.service.TrainerService;




@Controller
public class DemoController {

	@Autowired
    private CourseService service;
    
	@Autowired
    private CourseService courseService;


	@Autowired
    private TrainerService trainerService;


	@Autowired
    private CategoryService categoryService;


	@GetMapping({ "/", "/index" })
	public String Home() {
		return "index";
	}

	
	public String showCourses(Model model) {
		
		List<Course> pendingCourses = courseService.getPendingCourse();
		List<Course> approvedCourses = courseService.getApprovedCourses();
		List<Course> rejectedCourses = courseService.getRejectedCourses();
	
		model.addAttribute("pendingCourses", pendingCourses);
		model.addAttribute("approvedCourses", approvedCourses);
		model.addAttribute("rejectedCourses", rejectedCourses);
	//System.out.println(approvedCourses);
		return "courses"; // Name of the Thymeleaf template

	}
 
	@GetMapping("/courses")
    public String showCourses(@RequestParam(value = "category", required = false) String category, Model model) {
        // Fetch categories for the filter
        List<Category> categories = categoryService.getCategory();
        model.addAttribute("categories", categories); // Add categories to the model

        // Fetch courses based on category, or all if no category is specified
        List<Course> courses;
        if (category != null && !category.isEmpty()) {
            courses = courseService.getCoursesByCategory(category); // Assuming this method exists in CourseService
        } else {
            courses = courseService.getApprovedCourses(); // Show only approved courses by default
        }

        model.addAttribute("courses", courses);
        model.addAttribute("selectedCategory", category); // Pass selected category to the view
        return "courses"; // Thymeleaf template
    }
	
	@GetMapping("/pricing")
	public String pricing() {
		return "pricing";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/events")
	public String events() {
		return "events";
	}

	// @GetMapping("/trainers")
	// public String trainers() {
	// 	return "trainers";
	// }

	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}

	@GetMapping("/course-details/{id}")
    public String getCourseDetails(@PathVariable("id") Long courseId, Model model) {
        Course course = courseService.findCourseById(courseId); // Fetch the course by ID from your service layer
        model.addAttribute("course", course); // Add the course to the model
        return "course-details"; // Return the course-details.html template
    }

	@GetMapping("/api/users/register")
	public String register(){
		return "RegistrationForm";

	}
	@GetMapping("/trainer-forms")
	public String trainerget(){
		return "";
	}
	@GetMapping("/api/users/login")
	public String getlogin() {
		return "LoginPage";

	}
	
	@GetMapping("/api/trainer/addtrainer")
	public String getMethodName() {
		return "AddTrainer";
	}
	
	

	@GetMapping("/trainers")
    public String getAllTrainers(Model model) {
        List<Trainer> trainers = trainerService.getallTrainer();  // Fetch data from the service
        model.addAttribute("trainers", trainers);  // Add trainer data to the model
        return "trainers";  // Return the Thymeleaf template name
    }

	// @GetMapping("/trainer-forms")
	// public String trainerget(){
	// 	return "";
	// }


	
}
