package site.metacoding.blogv3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import site.metacoding.blogv3.config.auth.SessionUser;
import site.metacoding.blogv3.domain.user.User;

import javax.servlet.http.HttpSession;

@EnableWebSecurity // 해당 파일로 시큐리티가 활성화
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.formLogin()
                .loginPage("/login-form")
                .loginProcessingUrl("/login")
                .successHandler((request, response, authentication) -> {
                    SessionUser loginUser = (SessionUser) authentication.getPrincipal();
                    User principal = loginUser.getUser();
                    HttpSession session = request.getSession();
                    session.setAttribute("principal", principal);
                    response.sendRedirect("/user/" + principal.getId() + "/post");
                })
                .failureHandler((request, response, exception) -> {
                    System.out.println(exception.getMessage());
                    response.sendRedirect("/login-form");
                });


        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()  // 누구나 h2-console 접속 허용
                .antMatchers("/s/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .csrf()
                .ignoringAntMatchers("/h2-console/**")
                .disable();
    }
}
