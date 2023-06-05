package com.example.myapplication.data.models;

import com.example.myapplication.data.models.enums.GenderType;
import com.example.myapplication.data.models.enums.UserType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private int weight;
    private int length;
    private GenderType gender;
    private String photoUrl;
    private UserType type;
    private String createdAt;

}
