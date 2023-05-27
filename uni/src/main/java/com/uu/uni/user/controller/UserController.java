package com.uu.uni.user.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.uu.uni.user.dto.FriendReqDTO;
import com.uu.uni.user.dto.UserDTO;
import com.uu.uni.user.dto.UserSignUpDTO;
import com.uu.uni.user.service.PhoneValidationService;
import com.uu.uni.user.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {
	
	final UserService userService;
	final DefaultMessageService defaultMessageService;
	final PhoneValidationService phoneValidationService;
	
	@Autowired
	public UserController(UserService userService, PhoneValidationService phoneValidationService) {
		this.userService = userService;
		this.defaultMessageService = NurigoApp.INSTANCE.initialize("NCS1QZEXH48DE8O1", "12MPLZHBL3SP13B2EEDVNRRTW0Z6OO7O", "https://api.coolsms.co.kr");
		this.phoneValidationService = phoneValidationService;
	}
	
	@GetMapping("/signin")
	public String signin(@AuthenticationPrincipal User user) {
		if(user != null) return "redirect:/uni/main";
		else return "users/signin";
	}

	//시큐리티로 로그인 대체
//	//나중에 로직을 boolean으로 들고오지말고 유저 객체로 들고올 수도..? 로그인이든 뭐든간에
//	@PostMapping("/signin")
//	public ModelAndView signin(@ModelAttribute("dto") UserSignInDTO dto, ModelAndView mv) {
//		if(userService.login(dto)) {
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
	public String signup(@AuthenticationPrincipal User user, HttpSession ss) {
		if(user != null) return "redirect:/uni/main";
		ss.invalidate(); //validation phone clear
		return "users/signup";
	}
	
	@PostMapping
	public ModelAndView signup(@ModelAttribute("dto") UserSignUpDTO dto, ModelAndView mv) {		
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
		String msg = userService.validation(param, text);
		return msg;
	}
	
//	@PostMapping("/logout")
//	public String signout(@RequestBody UserEntity req) {
//		return null;
//	}	
	
	@GetMapping("/{idx}")
	public void find() {
		
	}
	
	@ResponseBody
	@PutMapping("/{idx}")
	public void modify(@PathVariable int idx, UserDTO dto) {
		userService.modify(dto);
	}
	
	@ResponseBody
	@PutMapping("/img/{idx}")
	public String imgModify(@PathVariable int idx, @RequestParam("imgfile") MultipartFile imgfile, UserDTO dto) throws IOException {
		if(imgfile.getSize() >= 5242880) return "실패:5MB 미만의 이미지만 사용 가능합니다.";
		if(userService.imgModify(imgfile, dto)) return dto.getImg();
		else return "실패:불가능한 형식의 파일입니다.";
	}
	
	@DeleteMapping("/{idx}")
	public void delete() {
		
	}
	
	@GetMapping("/friends")	
	public void friendAddCheck(FriendReqDTO dto, ModelAndView mv) {		

	}
	
	@ResponseBody
	@PostMapping("/friends")	
	public String friendReq(FriendReqDTO dto) {
		System.out.println(dto);
		return userService.friendReq(dto);		
	}
	
	@PutMapping("/friends")	
	public String friendResp(String from, String to) {		
		return null;
	}
	
	@DeleteMapping("/friends")	
	public String friendDel(String from, String to) {
		return null;
	}
	
	
	@PostMapping("/validation/phone")
	@ResponseBody
	public SingleMessageSentResponse sendSMS(String phone, HttpSession ss) {
		String ran_str = phoneValidationService.getValidationCode();		
		Message msg = phoneValidationService.getMsgForm(ran_str, phone);	
		SingleMessageSentResponse response = this.defaultMessageService.sendOne(new SingleMessageSendingRequest(msg));
				
		ss.setAttribute("validation", ran_str);
		ss.setAttribute("message_id", response.getMessageId());
		ss.setMaxInactiveInterval(180);
		System.out.println(response);
		return response;		
	}
	
	@GetMapping("/validation/phone")
	@ResponseBody
	public String validationSMS(String validation, HttpSession ss) {
		return phoneValidationService.validationSMS(validation, ss);
	}
	
}