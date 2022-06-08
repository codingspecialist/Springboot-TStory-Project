package site.metacoding.blogv3.config.auth;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import site.metacoding.blogv3.domain.user.User;

@Profile("test")
@Service
public class TestUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = User.builder()
                .id(1)
                .username("ssar")
                .password("1234")
                .email("ssar@nate.com")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        return new LoginUser(user);
    }

}
