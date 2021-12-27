package com.cg.shopping.profileservice.entity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JwtRes {
    private String _id , name, email, token, role;
    private Boolean isAdmin;

    public JwtRes(String _id, String name, String email, String token, String role, Boolean isAdmin) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.token = token;
        this.role = role;
        this.isAdmin = isAdmin;
    }
}
