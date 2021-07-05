package cn.qaq.demo_auth.entity;

import lombok.Data;

@Data
public class UserDetailsEntity {
    Integer uid;
    String username;
    String password;
    String role;

    public UserDetailsEntity(Integer uid, String username, String password, String role) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
