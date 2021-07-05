package cn.qaq.demo_auth.mapper;

import cn.qaq.demo_auth.entity.UserDetailsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDetailsMapper {

    UserDetailsEntity selectUserByName(@Param("username") String username);
}
