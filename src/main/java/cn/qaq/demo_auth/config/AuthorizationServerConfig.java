package cn.qaq.demo_auth.config;

import cn.qaq.demo_auth.service.UserDetailsAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${security.oauth2.privatekey}")
    private String privateKey;
    @Value("${security.oauth2.publickey}")
    private String publicKey;

    @Autowired
    @Qualifier("authenticationManagerBean")
    AuthenticationManager authenticationManager;

    /**
     * redis factory, default lettue
     */
    @Autowired
    RedisConnectionFactory redisConnectionFactory;

//    @Autowired
//    TokenStore jwtTokenStore;

    @Autowired
    TokenStore redisTokenStore;

    /**
     * 自定义的用户认证服务
     */
    @Autowired
    UserDetailsAuthService userDetailsAuthService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()

                .withClient("client")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                .scopes("read", "write")
                .autoApprove(true)
//                .redirectUris("resource server")
                .secret(passwordEncoder().encode("password"))
        ;
//        clients.withClientDetails();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .checkTokenAccess("permitAll()")
//                .checkTokenAccess("hasAuthority('USER')")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                //token以jwt形式存储
//                .tokenStore(tokenStore)
                .tokenServices(tokenServices())
//                .accessTokenConverter(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsAuthService)
                .allowedTokenEndpointRequestMethods(HttpMethod.POST, HttpMethod.GET);

    }


    public AuthorizationServerTokenServices tokenServices() {
//        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        final DefaultTokenServices tokenService = new DefaultTokenServices();
//        tokenService.setTokenStore(jwtTokenStore);
        tokenService.setTokenStore(redisTokenStore);
        tokenService.setTokenEnhancer(jwtAccessTokenConverter());
        tokenService.setSupportRefreshToken(true);
        tokenService.setAccessTokenValiditySeconds(60 * 5);
        tokenService.setRefreshTokenValiditySeconds(60 * 10);
        tokenService.setReuseRefreshToken(false);
        return tokenService;
    }


//    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public TokenStore redisTokenStore() {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setPrefix("auth2-token:");
        return redisTokenStore;
    }

    /**
     * 授权服务公私钥配置与资源服务一致
     *
     * @return
     */
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        String key = Base64.getEncoder().encodeToString("accesskey".getBytes());
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        converter.setVerifier(new RsaVerifier(publicKey));
        return converter;
    }

    public void setKeyPair(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
//        Assert.state(privateKey instanceof RSAPrivateKey, "KeyPair must be an RSA ");
//        signer = new RsaSigner((RSAPrivateKey) privateKey);
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        verifier = new RsaVerifier(publicKey);
        String verifierKey = "-----BEGIN PUBLIC KEY-----\n"
                + Base64.getEncoder().encode(publicKey.getEncoded())
                + "\n-----END PUBLIC KEY-----";
    }
}
