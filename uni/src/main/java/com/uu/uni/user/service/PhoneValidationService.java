package com.uu.uni.user.service;

import jakarta.servlet.http.HttpSession;
import net.nurigo.sdk.message.model.Message;

public interface PhoneValidationService {

	String getValidationCode();

	Message getMsgForm(String ran_str, String phone);

	String validationSMS(String validation, HttpSession ss);	
	
}
