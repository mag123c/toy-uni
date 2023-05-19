package com.uu.uni.user.controller;

import java.awt.PageAttributes.MediaType;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.uu.uni.user.dto.UserDTO;
import com.uu.uni.user.dto.UserSignInDTO;
import com.uu.uni.user.dto.UserSignUpDTO;
import com.uu.uni.user.entity.UserEntity;
import com.uu.uni.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/signin")
	public String signin() {
		return "users/signin";
	}

	//시큐리티로 로그인 대체
//	//나중에 로직을 boolean으로 들고오지말고 유저 객체로 들고올 수도..? 로그인이든 뭐든간에
//	@PostMapping("/signin")
//	public ModelAndView signin(@ModelAttribute("dto") UserSignInDTO dto, HttpSession ss, ModelAndView mv) {
//		if(userService.login(dto)) {
//			ss.setAttribute("ID", dto.getId());
//			mv.setViewName("redirect:/uni/main");
//		}
//		else {
//			mv.setViewName("users/signin");
//			mv.addObject("fail", "로그인실패");
//		}
//		System.out.println("리턴중");
//		return mv;
//	}
	
	@GetMapping
	public String signup() {
		return "users/signup";
	}
	
	@PostMapping
	public ModelAndView signup(@ModelAttribute("dto") UserSignUpDTO dto, ModelAndView mv) {		
		System.out.println(dto);
		if(userService.signup(dto)) {
			mv.setViewName("main");
		}
		else {
			mv.setViewName("users/signup");
			mv.addObject("fail", "회원가입 실패");
		}
		return mv;
	}
	
	@PostMapping("/validation")
	@ResponseBody
	public String validation(String param, String text) {
		System.out.println(param + " + " + text);
		String msg = userService.validation(param, text);
		return msg;
	}
	
	@PostMapping("/signout")
	public String signout(@RequestBody UserEntity req) {
		return null;
	}	
	
	@GetMapping("/{idx}")
	public void find() {
		
	}
	
	@PutMapping("/{idx}")
	public void modify() {
		
	}
	
	@ResponseBody
	@PostMapping("/img/{idx}")
	public String img_modify(@PathVariable int idx, @RequestParam("imgfile") MultipartFile imgfile, UserDTO dto) throws IOException {
		System.out.println(imgfile.getName());
		System.out.println(imgfile.getSize());
		System.out.println(imgfile.getOriginalFilename());
		
		System.out.println(dto);
		if(userService.img_modify(imgfile, dto)) return "성공";
		else return "실패";
	}
	
	@DeleteMapping("/{idx}")
	public void delete() {
		
	}
	
	
}