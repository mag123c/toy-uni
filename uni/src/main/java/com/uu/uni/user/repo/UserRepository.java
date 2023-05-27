package com.uu.uni.user.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uu.uni.user.dto.UserSignUpDTO;
import com.uu.uni.user.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

//	@Query("SELECT u.pw FROM User u WHERE u.id = :id")
	Optional<UserEntity> findById(@Param("id") String id);
		
	Optional<UserEntity> findByNn(@Param("nn") String nn);

	@Query(value="SELECT * FROM User u WHERE u.id=:#{#dto.id} or u.nn=:#{#dto.nn}", nativeQuery=true)
	Optional<UserEntity> validation(@Param("dto") UserSignUpDTO dto);

	Optional<UserEntity> findByIdx(int i);

}