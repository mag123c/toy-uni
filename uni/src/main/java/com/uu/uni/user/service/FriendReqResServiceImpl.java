package com.uu.uni.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uu.uni.user.entity.FriendReqResEntity;
import com.uu.uni.user.repo.FriendReqResRepository;

@Service
public class FriendReqResServiceImpl implements FriendReqResService{

	FriendReqResRepository friendReqResRepository;
	
	@Autowired
	public void FriendReqResRes(FriendReqResRepository friendReqResRepository) {		
		this.friendReqResRepository = friendReqResRepository;
	}
	
	@Override
	public List<Optional<FriendReqResEntity>> getReq(int idx) {
		List<Optional<FriendReqResEntity>> req = friendReqResRepository.findByTo(idx);
		System.out.println(req.size());
		return req;
	}

}
