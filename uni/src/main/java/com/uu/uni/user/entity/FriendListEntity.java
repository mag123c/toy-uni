package com.uu.uni.user.entity;

import java.time.LocalDateTime;

import groovy.transform.builder.Builder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "FriendList")
@Entity
public class FriendListEntity {

	@Builder
	public FriendListEntity(int user_idx, int friend_idx, String comment, LocalDateTime create_date, LocalDateTime last_update_date) {
		this.user_idx = user_idx;
		this.friend_idx = friend_idx;
		this.comment = comment;
		this.create_date = create_date;
		this.last_update_date = last_update_date;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int user_idx;
	
	@Column(nullable=false)
	private int friend_idx;
	
	@Column
	private String comment;
	
	@Column
	private LocalDateTime create_date;
	
	@Column
	private LocalDateTime last_update_date;
	
}
