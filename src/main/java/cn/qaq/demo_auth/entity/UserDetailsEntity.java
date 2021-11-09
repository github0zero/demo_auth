package cn.qaq.demo_auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("user_details")
public class UserDetailsEntity {
    Long id;
    Integer uid;
    String username;
    String password;
    String role;

}
