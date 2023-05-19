package com.uu.uni.user.service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.uu.uni.user.dto.UserDTO;
import com.uu.uni.user.dto.UserSignUpDTO;
import com.uu.uni.user.entity.UserEntity;
import com.uu.uni.user.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private PasswordEncoder bcpe;
	
    @Autowired
    private S3Uploader s3Uploader;
	
	@Autowired
	public void UserService(UserRepository userRepository, PasswordEncoder bcpe) {
		this.userRepository = userRepository;
		this.bcpe = bcpe;
	}

//	@Override
//	public boolean login(UserSignInDTO dto) {
//		Optional<UserEntity> User = userRepository.findById(dto.getId());
//		if(User.isPresent()) {
//			UserEntity userEntity = User.get();
//			if(bcpe.matches(dto.getPw(), userEntity.getPw())) {
//				return true;
//			}
//			else return false;
//			
//		}
//		else return false;
//	}

	@Transactional
	@Override
	public boolean signup(UserSignUpDTO dto) {
		Optional<UserEntity> User = userRepository.validation(dto);
		if(User.isPresent()) return false;
		else {
			UserEntity userEntity = new UserEntity();
			dto.setPw(bcpe.encode(dto.getPw()));
			
			userEntity.setId(dto.getId());
			userEntity.setPw(dto.getPw());
			userEntity.setEmail(dto.getEmail());
			userEntity.setNn(dto.getNn());
			userEntity.setPhone(dto.getPhone());
			
			userRepository.save(userEntity);
			
			return true;
		}
	}

	@Override
	public String validation(String param, String text) {		
		Optional<UserEntity> User = null;
		switch(param) {
		case "id":
			User = userRepository.findById(text);			
			break;
		case "email":
			User = userRepository.findByEmail(text);
			break;
		case "nn":
			User = userRepository.findByNn(text);
			break;
		}
		if(User.isPresent()) {
			return "중복되는 " + param + "이(가) 존재합니다";
		}
		else return null;
	}

	@Override
	public Optional<UserEntity> getUser(String id) {
		Optional<UserEntity> User = userRepository.findById(id);
		
		if(User.isPresent()) return User;
		else return null;
	}

	@Transactional
	@Override
	public boolean img_modify(MultipartFile imgfile, UserDTO dto) throws IOException {
		if(!imgfile.isEmpty()) {			
	        String uuid = UUID.randomUUID().toString();
			String serverFileName = s3Uploader.upload(imgfile, dto.getId()+uuid);
			dto.setImg(serverFileName);
			
			Optional<UserEntity> User = userRepository.findByIdx(dto.getIdx());
			User.get().setImg(serverFileName);
			return true;
		}		
		else return false;		
	}


	
}
