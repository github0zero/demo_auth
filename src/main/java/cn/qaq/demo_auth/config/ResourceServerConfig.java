package cn.qaq.demo_auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${security.oauth2.privatekey}")
    private String privateKey;
    @Value("${security.oauth2.publickey}")
    private String publicKey;

    @Autowired
    TokenStore redisTokenStore;

//    @Autowired
//    TokenStore jwtTokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.tokenStore(jwtTokenStore);
        resources.tokenStore(redisTokenStore);
//        super.configure(resources);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        // 配置授权后可访问
        httpSecurity.authorizeRequests().antMatchers("/auth/test").permitAll()
//                .and().requestMatchers().antMatchers("auth/admin")
                .and().authorizeRequests().antMatchers("/auth/admin").access("hasRole('ADMIN')")
//                .and().authorizeRequests().antMatchers("/auth/admin").hasAuthority("write")
//                .and().requestMatchers().antMatchers("/auth/resource")
                .and().authorizeRequests().antMatchers("/auth/resource").access("hasRole ('USER')")
        ;
    }

//    private TokenStore jwtTokenStore() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        //资源服务公私钥配置与授权服务一致
//        converter.setSigningKey(privateKey);
//        converter.setVerifierKey(publicKey);
//        converter.setVerifier(new RsaVerifier(publicKey));
//        return new JwtTokenStore(converter);
//    }

//
//    @Bean
//    public RemoteTokenServices tokenServices() {
//        RemoteTokenServices tokenServices = new RemoteTokenServices();
//        tokenServices.setAccessTokenConverter(jwtAccessTokenConverter());
//        return tokenServices;
//    }
}
