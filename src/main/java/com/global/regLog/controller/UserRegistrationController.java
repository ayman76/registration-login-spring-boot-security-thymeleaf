package com.global.regLog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.global.regLog.dto.UserRegistrationDto;
import com.global.regLog.service.UserService;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
	
	@Autowired
	private UserService userService;



	@PostMapping("")
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto userDto) {
		userService.insert(userDto);
		return "redirect:/registration?success";
	}
	
	public String showRegistrationForm() {
		return "registration";
	}

}
