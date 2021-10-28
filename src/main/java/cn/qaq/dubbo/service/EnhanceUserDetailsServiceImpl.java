package cn.qaq.dubbo.service;

import cn.qaq.demo_auth.entity.UserDetailsEntity;
import cn.qaq.demo_auth.mapper.UserDetailsMapper;
import com.alibaba.fastjson.JSONObject;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Random;

@DubboService(version = "1.0")
@Component
public class EnhanceUserDetailsServiceImpl implements EnhanceUserDetailsService {

    @Autowired
    UserDetailsMapper userDetailsMapper;

    @Override
    public JSONObject newAccessToken() {
        JSONObject r = new JSONObject();
        r.put("app_name","demo_auth");
        r.put("token","123asf");
        return r;
    }

    @Override
    public void newUser(String uname){
        if("crime".equals(uname)){
            throw new RuntimeException("a crimer");
        }
        UserDetailsEntity user = UserDetailsEntity.builder()
                .username(uname)
                .uid(new Random().nextInt(999))
                .password("psd")
                .role("USER")
                .build();
        userDetailsMapper.insertUser(user);
    }
}
