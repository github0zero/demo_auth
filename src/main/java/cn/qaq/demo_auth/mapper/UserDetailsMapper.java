package cn.qaq.demo_auth.mapper;

import cn.qaq.demo_auth.entity.UserDetailsEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDetailsMapper {

    UserDetailsEntity selectUserByName(@Param("username") String username);

    @Insert("insert into user_details(uid,username,password,role) values(#{user.uid},#{user.username},#{user.password},#{user.role})")
    void insertUser(@Param("user") UserDetailsEntity user);
}
