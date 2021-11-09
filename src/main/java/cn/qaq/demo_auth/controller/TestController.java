package cn.qaq.demo_auth.controller;

import cn.qaq.demo_auth.entity.UserDetailsEntity;
import cn.qaq.demo_auth.mapper.UserDetailsMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@Slf4j
@RefreshScope
public class TestController {

//    @Autowired
//    HttpSecurity httpSecurity;

    @Autowired
    UserDetailsMapper userDetailsMapper;


    @GetMapping("/get")
    public UserDetailsEntity get() throws Exception {
//        httpSecurity.authorizeRequests().antMatchers("/auth/admin").access("hasRole('ADMIN')");
        QueryWrapper<UserDetailsEntity> wrapper = new QueryWrapper();
        wrapper.eq("uid","124");
        return userDetailsMapper.selectOne(wrapper);
    }

    @GetMapping("/getList")
    public List<UserDetailsEntity> getList() throws Exception {
//        httpSecurity.authorizeRequests().antMatchers("/auth/admin").access("hasRole('ADMIN')");
        QueryWrapper<UserDetailsEntity> wrapper = new QueryWrapper();
        wrapper.gt("uid","200");
        return userDetailsMapper.selectList(wrapper);
    }

    @GetMapping("/update")
    public int update(){
        UpdateWrapper<UserDetailsEntity> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",4);
        UserDetailsEntity user = UserDetailsEntity.builder()
                .username("test").role("ALL").build();
        return userDetailsMapper.update(user, wrapper);
    }
}
