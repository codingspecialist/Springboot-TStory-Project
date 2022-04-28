package site.metacoding.blogv3.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv3.config.auth.LoginUser;
import site.metacoding.blogv3.domain.user.User;
import site.metacoding.blogv3.handler.ex.CustomApiException;
import site.metacoding.blogv3.service.PostService;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @DeleteMapping("/s/api/post/{id}")
    public ResponseEntity<?> postDelete(@PathVariable Integer id, @AuthenticationPrincipal LoginUser loginUser) {

        User principal = loginUser.getUser();

        postService.게시글삭제(id, principal);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
