package site.metacoding.blogv3.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity // 해당 파일로 시큐리티가 활성화
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    // 인증 설정하는 메서드
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
        http.csrf().disable(); // 이거 안하면 postman 테스트 못함.

        http.authorizeRequests()
                .antMatchers("/s/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                // .usernameParameter("uname")
                // .passwordParameter("pwd")
                .loginPage("/login-form")
                .loginProcessingUrl("/login") // login 프로세스를 탄다.
                // .failureHandler(null)
                // .successHandler(null)
                .defaultSuccessUrl("/");

    }
}
