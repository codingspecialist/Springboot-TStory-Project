package site.metacoding.blogv3.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv3.config.auth.LoginUser;
import site.metacoding.blogv3.service.PostService;
import site.metacoding.blogv3.web.dto.post.PostRespDto;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    // CategoryService 사용하지 말고
    // PostService 사용하세요. 이유는 나중에 category, post글 다 같이 가지고 가야 하기 때문임!!

    @GetMapping("/user/{id}/post")
    public String postList(@PathVariable Integer id, @AuthenticationPrincipal LoginUser loginUser, Model model) {

        // SELECT * FROM category WHERE userId = :id
        // 카테고리 가져가세요!!
        PostRespDto postRespDto = postService.게시글목록보기(id);
        model.addAttribute("postRespDto", postRespDto);
        return "/post/list";
    }
}
