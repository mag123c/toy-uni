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
@Table(name = "FriendReqRes")
@Entity
public class FriendReqResEntity {

	@Builder
	public FriendReqResEntity(Long idx, String from, String to, String status, LocalDateTime create_date) {
		this.idx = idx;
		this.from = from;
		this.to = to;
		this.status = status;
		this.create_date = create_date;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;
	
	@Column(length=40, nullable=false)
	private String from;
	
	@Column(length=40, nullable=false)
	private String to;
	
	@Column
	private String status;
	
	@Column
	private LocalDateTime create_date;
}
