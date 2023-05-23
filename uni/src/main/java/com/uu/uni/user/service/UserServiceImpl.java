package com.uu.uni.user.service;

import java.io.IOException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.uu.uni.user.dto.FriendReqDTO;
import com.uu.uni.user.dto.UserDTO;
import com.uu.uni.user.dto.UserSignUpDTO;
import com.uu.uni.user.entity.FriendReqResEntity;
import com.uu.uni.user.entity.UserEntity;
import com.uu.uni.user.repo.FriendReqResRepository;
import com.uu.uni.user.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	private FriendReqResRepository friendReqResRepository;
	private PasswordEncoder bcpe;
	
    @Autowired
    private S3Uploader s3Uploader;
	
	@Autowired
	public void UserService(UserRepository userRepository, FriendReqResRepository friendReqResRepository, PasswordEncoder bcpe) {
		this.userRepository = userRepository;
		this.friendReqResRepository = friendReqResRepository;
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
	public boolean imgModify(MultipartFile imgfile, UserDTO dto) throws IOException {
		if(!imgfile.isEmpty()) {
			UserEntity user = userRepository.findByIdx(dto.getIdx()).get();
			String url = "https://randomchatuni.s3.ap-northeast-2.amazonaws.com/";
			String beforeImg = user.getImg()==null ? null : user.getImg().replace(url, "");
			
			if(beforeImg != null) beforeImg = URLDecoder.decode(beforeImg, "UTF-8");
			
			String serverFileName = s3Uploader.upload(imgfile, user.getId(), beforeImg);
			dto.setImg(serverFileName);
			
			user.setImg(serverFileName);
			return true;
		}		
		else return false;		
	}

	@Transactional
	@Override
	public void modify(UserDTO dto) {
		Optional<UserEntity> user = userRepository.findById(dto.getId());
		UserEntity updateUser = user.get();
		updateUser.setPw(bcpe.encode(dto.getPw()));		
		updateUser.setNn(dto.getNn());
		updateUser.setEmail(dto.getEmail());
		updateUser.setPhone(dto.getPhone());
	}

	@Override
	public Optional<UserEntity> getUserForMainChat(String nn) {
		Optional<UserEntity> User = userRepository.findByNn(nn);
		if(User.isPresent()) return User;
		else return null;
	}

	@Transactional
	@Override
	public String friendReq(FriendReqDTO dto) {
		if(dto.getFrom().equals(dto.getTo())) return "본인에게 친구요청을 보낼 수 없습니다";
		
		else {		
			FriendReqResEntity req = new FriendReqResEntity();		
			
			req.setFrom(dto.getFrom());
			req.setTo(dto.getTo());
			req.setStatus("X");
			req.setCreate_date(LocalDateTime.now());
			
			friendReqResRepository.save(req);
			return "완료";
		}
	}

	@Override
	public boolean friendAddCheck(FriendReqDTO dto) {
		if(userRepository.findByNn(dto.getTo()).isEmpty()) return false;
		if(userRepository.findByNn(dto.getFrom()).get().getIdx() == userRepository.findByNn(dto.getTo()).get().getIdx()) return false;		
		else return true;
	}

	@Override
	public UserEntity friendListGet(FriendReqDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}	
}