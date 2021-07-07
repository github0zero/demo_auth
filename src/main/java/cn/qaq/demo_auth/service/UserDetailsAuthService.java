package cn.qaq.demo_auth.service;

import cn.qaq.demo_auth.entity.UserDetailsEntity;
import cn.qaq.demo_auth.mapper.UserDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class UserDetailsAuthService implements UserDetailsService {

    @Autowired
    UserDetailsMapper userDetailsMapper;

    @Autowired
    Environment environment;

    @Autowired
    TokenEndpoint tokenEndpoint;
//    @Autowired
//    PasswordEncoder passwordEncoder;

    /**
     * 实现UserDetailsService中的loadUserByUsername方法，用于加载用户数据
     */
    @Override
    public UserDetails loadUserByUsername(String s) {
        UserDetailsEntity userDetailsEntity = userDetailsMapper.selectUserByName(s);
        if (null == userDetailsEntity) {
            throw new UsernameNotFoundException("user not found");
        }
        List<GrantedAuthority> roles = stream(userDetailsEntity.getRole().split(","))
                .map(x -> new SimpleGrantedAuthority("ROLE_" + x))
                .collect(Collectors.toList());

        UserDetails user = User.builder().username("admin")
                .password("secret")
                .roles("USER").build();
        UserDetails userDetails = User.builder().username(userDetailsEntity.getUsername())
                .password(userDetailsEntity.getPassword())
                /**
                 *  .role(S)    -> GrantedAuthority("ROLE_"+S)
                 *  .authorities(S)  -> GrantedAuthority(S)
                 */
                .roles(userDetailsEntity.getRole())
//                .authorities(roles)
                .build();
        return userDetails;
    }
}
