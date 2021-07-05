package cn.qaq.demo_auth.other;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncodeTest {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pw = "secret";
        System.out.println(passwordEncoder.encode(pw));
        UserDetails user = User.builder().username("user")
                .password(passwordEncoder.encode("secret1"))

                .roles("USER").build();
        System.out.println(user.getAuthorities());
    }
}
