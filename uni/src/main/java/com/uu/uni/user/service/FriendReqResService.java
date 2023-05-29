package com.uu.uni.user.service;

import java.util.List;
import java.util.Optional;

import com.uu.uni.user.entity.FriendReqResEntity;

public interface FriendReqResService {

	List<Optional<FriendReqResEntity>> getReq(int to);

}
