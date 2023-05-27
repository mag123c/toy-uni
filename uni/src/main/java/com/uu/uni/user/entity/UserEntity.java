package com.uu.uni.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "User")
@Entity
public class UserEntity {
	
	@Builder
	public UserEntity(int idx, String id, String pw, String nn, String phone,
			String onoff, Integer cash, String img, LocalDateTime create_date, LocalDateTime update_date) {
		this.idx = idx;
		this.id = id;
		this.pw = pw;
		this.nn = nn;		
		this.phone = phone;
		this.onoff = onoff;
		this.cash = cash;
		this.create_date = create_date;
		this.update_date = update_date;
		this.img = img;		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idx;
	
	@Column(length=40, nullable=false)
	private String id;
	
	@Column(length=40, nullable=false)
	private String pw;
	
	@Column(length=40, nullable=false)
	private String nn;

	@Column(length=40)
	private String phone;
	
	@Column(length=2)
	private String onoff;
	
	@Column
	private Integer cash;
	
	@Column
	private String img;
	
	@Column
	private LocalDateTime create_date;
	
	@Column
	private LocalDateTime update_date;

	
}