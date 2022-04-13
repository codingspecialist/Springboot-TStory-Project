package site.metacoding.blogv3.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv3.domain.user.User;
import site.metacoding.blogv3.domain.user.UserRepository;
import site.metacoding.blogv3.handler.ex.CustomException;
import site.metacoding.blogv3.util.email.EmailUtil;
import site.metacoding.blogv3.web.dto.user.PasswordResetReqDto;

@RequiredArgsConstructor
@Service // IoC 등록
public class UserService {

    // DI
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailUtil emailUtil;

    @Transactional
    public void 패스워드초기화(PasswordResetReqDto passwordResetReqDto) {
        // 1. username, email 이 같은 것이 있는지 체크 (DB)
        Optional<User> userOp = userRepository.findByUsernameAndEmail(
                passwordResetReqDto.getUsername(),
                passwordResetReqDto.getEmail());

        // 2. 같은 것이 있다면 DB password 초기화 - BCrypt 해시 - update 하기 (DB)
        if (userOp.isPresent()) {
            User userEntity = userOp.get(); // 영속화
            String encPassword = bCryptPasswordEncoder.encode("9999");
            userEntity.setPassword(encPassword);
        } else {
            throw new CustomException("해당 이메일이 존재하지 않습니다.");
        }

        // 3. 초기화된 비밀번호 이메일로 전송
        emailUtil.sendEmail("getinthere@naver.com", "비밀번호 초기화", "초기화된 비밀번호 : 9999");
    } // 더티체킹 (update)

    @Transactional
    public void 회원가입(User user) {
        String rawPassword = user.getPassword(); // 1234
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 해쉬 알고리즘
        user.setPassword(encPassword);

        userRepository.save(user);
    }

    public boolean 유저네임중복체크(String username) {
        Optional<User> userOp = userRepository.findByUsername(username);

        if (userOp.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

}
