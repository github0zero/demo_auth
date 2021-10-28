package cn.qaq.demo_auth.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
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
