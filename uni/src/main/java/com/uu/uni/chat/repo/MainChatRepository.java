package com.uu.uni.chat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uu.uni.chat.entity.MainChatEntity;

@Repository
public interface MainChatRepository extends JpaRepository<MainChatEntity, Long>{

}
