package com.uu.uni.user.dto;

import lombok.Getter;

@Getter
public enum UserRole {

	ADMIN("A"), USER("U");
	private String value;
	
	UserRole(String value){
		this.value = value;
	}
	
}
