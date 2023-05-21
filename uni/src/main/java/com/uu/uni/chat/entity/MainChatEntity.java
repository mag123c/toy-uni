package com.uu.uni.chat.entity;

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
@Table(name = "MainChat")
@Entity
public class MainChatEntity {

	@Builder
	public MainChatEntity(int idx, String id, String content, LocalDateTime create_date) {
		this.idx = idx;
		this.id = id;
		this.content = content;
		this.create_date = create_date;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idx;
	
	@Column(nullable = false)
	String id;
	
	@Column(nullable = false)
	String content;
	
	@Column(nullable = false)
	LocalDateTime create_date;
	
}
