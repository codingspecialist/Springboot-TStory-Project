package site.metacoding.blogv3.config.auth;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv3.domain.user.User;
import site.metacoding.blogv3.domain.user.UserRepository;

@RequiredArgsConstructor
@Service // IoC 컨테이너 등록됨.
public class SessionService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOp = userRepository.findByUsername(username);
        if (userOp.isPresent()) {
            System.out.println("유저 찾음");
            return new SessionUser(userOp.get());
        }
        return null;
    }

}
