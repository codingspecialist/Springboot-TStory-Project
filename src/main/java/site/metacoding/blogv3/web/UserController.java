package site.metacoding.blogv3.web;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.blogv3.config.auth.SessionUser;
import site.metacoding.blogv3.service.UserService;
import site.metacoding.blogv3.dto.user.JoinReqDto;
import site.metacoding.blogv3.dto.user.PasswordResetReqDto;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    @PutMapping("/s/api/user/profile-img")
    public ResponseEntity<?> profileImgUpdate(
            @AuthenticationPrincipal SessionUser loginUser,
            MultipartFile profileImgFile) {
        // 위에서 받은 id를 사용하면 세션값과 비교해서 권한체크를 해줘야 한다.
        // 그냥 세션값을 사용하면 권한체크 필요없음.

        // 세션값 변경
        userService.프로파일이미지변경(loginUser.getUser(), profileImgFile, session);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/login-form")
    public String loginForm() {

        return "/user/loginForm";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "/user/joinForm";
    }

    @GetMapping("/s/user/{id}")
    public String updateForm(@PathVariable Integer id) {
        return "/user/updateForm";
    }

    @GetMapping("/user/password-reset-form")
    public String passwordResetForm() {
        return "/user/passwordResetForm";
    }

    @PostMapping("/user/password-reset")
    public String passwordReset(@Valid PasswordResetReqDto passwordResetReqDto, Errors errors) {

        userService.패스워드초기화(passwordResetReqDto);

        return "redirect:/login-form";
    }

    // ResponseEntity 는 @ResponseBody를 붙이지 않아도 data를 리턴한다.
    @GetMapping("/api/user/username-same-check")
    public ResponseEntity<?> usernameSameCheck(String username) {
        boolean isNotSame = userService.유저네임중복체크(username); // true (같지 않다)
        return new ResponseEntity<>(isNotSame, HttpStatus.OK);
    }

    @PostMapping("/join")
    public String join(@Valid JoinReqDto joinReqDto, Errors error) {

        userService.회원가입(joinReqDto.toEntity());

        return "redirect:/login-form";
    }
}
