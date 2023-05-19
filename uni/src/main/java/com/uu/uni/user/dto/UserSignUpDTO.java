package com.uu.uni.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpDTO {

    private String id;
    private String pw;
    private String nn;
    private String email;
    private String phone;
    
}