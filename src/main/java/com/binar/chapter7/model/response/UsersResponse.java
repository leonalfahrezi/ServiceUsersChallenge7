package com.binar.chapter7.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersResponse {

    private Integer id;

    private String username;

    private String email;

    private String password;

}
