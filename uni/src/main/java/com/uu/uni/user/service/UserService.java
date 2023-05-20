package com.uu.uni.user.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.uu.uni.user.dto.UserDTO;
import com.uu.uni.user.dto.UserSignUpDTO;
import com.uu.uni.user.entity.UserEntity;

public interface UserService {

//	public boolean login(UserSignInDTO userLoginDTO);

	public boolean signup(UserSignUpDTO userSignUpDTO);

	public String validation(String param, String text);
	
	public Optional<UserEntity> getUser(String id);

	public boolean img_modify(MultipartFile imgfile, UserDTO dto) throws IOException;

	public void modify(UserDTO dto);
	
}