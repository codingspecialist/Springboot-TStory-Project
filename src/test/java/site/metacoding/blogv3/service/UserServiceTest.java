package site.metacoding.blogv3.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import site.metacoding.blogv3.domain.user.User;
import site.metacoding.blogv3.domain.user.UserRepository;
import site.metacoding.blogv3.domain.visit.Visit;
import site.metacoding.blogv3.domain.visit.VisitRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private VisitRepository visitRepository;

    // Controller, Filter, Repository 이런 건 무거우니까 가짜로 Mock한다.
    // Util 적인 것들은 @Spy로 주입하면 된다.
    @Spy
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    private UserService userService;

    // 테스트 필요 없음.
    public void 유저네임중복체크_테스트() {
        // given

        // stub

        // when

        // then

    }

    @Test
    public void 회원가입_테스트() {
        // given 1
        User givenUser = User.builder()
                .username("ssar")
                .password("1234")
                .email("ssar@nate.com")
                .build();

        // stub 1
        User mockUserEntity = User.builder()
                .id(1)
                .username("ssar")
                .password("1234")
                .email("ssar@nate.com")
                .profileImg(null)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        when(userRepository.save(givenUser)).thenReturn(mockUserEntity);

        // given 2
        Visit givenVisit = Visit.builder()
                .totalCount(0L)
                .user(mockUserEntity)
                .build();

        // stub 2
        Visit mockVisitEntity = Visit.builder()
                .id(1)
                .totalCount(0L)
                .user(mockUserEntity)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        when(visitRepository.save(givenVisit)).thenReturn(mockVisitEntity);

        // when
        User userEntity = userService.회원가입(givenUser);

        // then
        assertEquals(givenUser.getEmail(), userEntity.getEmail());
    }

    public void 프로파일이미지변경_테스트() {

    }

    public void 패스워드초기화_테스트() {

    }

}
