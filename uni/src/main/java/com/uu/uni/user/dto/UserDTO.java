package com.uu.uni.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private int idx;
    private String id;
    private String pw;
    private String phone;
    private String nn;
    private String cash;
    private String img;
    
}