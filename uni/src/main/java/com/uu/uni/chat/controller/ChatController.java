package com.uu.uni.chat.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uu.uni.user.entity.UserEntity;
import com.uu.uni.user.service.UserService;

@Controller
@RequestMapping("/uni")
public class ChatController {
	
	UserService userService;
	
	@Autowired
	public ChatController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/main")
	public String main(@AuthenticationPrincipal User user, Model model) {
		Optional<UserEntity> one = userService.getUser(user.getUsername());
		model.addAttribute("idx", one.get().getIdx());
		model.addAttribute("nn", one.get().getNn());
		model.addAttribute("cash", one.get().getCash());
		model.addAttribute("img", one.get().getImg());
		
		return "/chat/uni_main";
	}
	
}
