package com.uu.uni.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uu.uni.user.entity.FriendReqResEntity;

public interface FriendReqResRepository extends JpaRepository<FriendReqResEntity, Long> {

	@Query(value="SELECT * FROM FriendReqRes f WHERE f.to=:to", nativeQuery=true)
	List<Optional<FriendReqResEntity>> findByTo(@Param("to") int to);
}
