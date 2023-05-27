package com.uu.uni.user.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import net.nurigo.sdk.message.model.Message;

@Service
public class PhoneValidationServiceImpl implements PhoneValidationService{

	@Override
	public String getValidationCode() {
		String ran_str = "";
		for(int i=0; i<6; i++) {
			ran_str += (int)(Math.random()*10);
		}
		return ran_str;
	}

	@Override
	public Message getMsgForm(String ran_str, String phone) {
		Message msg = new Message();
		
		msg.setFrom("01024402059");
		msg.setTo(phone);
		msg.setText("[randomchatUNI] 아래의 인증번호를 입력해주세요\n" + ran_str);
		
		return msg;
	}

	@Override
	public String validationSMS(String validation, HttpSession ss) {
		if(ss.getAttribute("message_id") == null || ss.getAttribute("validation") == null) return "인증번호 만료, 처음부터 다시 시도해주세요";
		else {
			if(ss.getAttribute("validation").equals(validation)) return "인증 완료";
			else return "인증 실패";
		}		
	}
	
}
