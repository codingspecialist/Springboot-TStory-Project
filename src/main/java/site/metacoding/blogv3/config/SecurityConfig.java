package site.metacoding.blogv3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import site.metacoding.blogv3.handler.LoginSuccessHandler;

@EnableWebSecurity // 해당 파일로 시큐리티가 활성화
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // 이거 안하면 postman 테스트 못함.

        http.authorizeRequests()
                .antMatchers("/s/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                // .usernameParameter("uname")
                // .passwordParameter("pwd")
                .loginPage("/login-form")
                .loginProcessingUrl("/login")
                // .failureHandler(null)
                // .defaultSuccessUrl("/")
                .successHandler(new LoginSuccessHandler());

    }
}
