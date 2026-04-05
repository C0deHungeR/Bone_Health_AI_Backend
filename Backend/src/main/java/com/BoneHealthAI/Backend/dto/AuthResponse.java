package com.BoneHealthAI.Backend.dto;



import com.BoneHealthAI.Backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
    private String name;
    private User.Role role;
}
