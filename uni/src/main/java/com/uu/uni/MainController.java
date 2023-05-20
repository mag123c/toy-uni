package com.uu.uni;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String main(@AuthenticationPrincipal User user) {
		if(user != null) return "redirect:/uni/main";		
		else return "main";
	}	
}