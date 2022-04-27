package site.metacoding.blogv3.web;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv3.config.auth.LoginUser;
import site.metacoding.blogv3.domain.category.Category;
import site.metacoding.blogv3.domain.post.Post;
import site.metacoding.blogv3.handler.ex.CustomException;
import site.metacoding.blogv3.service.PostService;
import site.metacoding.blogv3.web.dto.post.PostRespDto;
import site.metacoding.blogv3.web.dto.post.PostWriteReqDto;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    // CategoryService 사용하지 말고
    // PostService 사용하세요. 이유는 나중에 category, post글 다 같이 가지고 가야 하기 때문임!!

    @GetMapping("/post/{id}")
    public String detail(@PathVariable Integer id, Model model, @AuthenticationPrincipal LoginUser loginUser) {

        Post postEntity = postService.게시글상세보기(id);
        model.addAttribute("post", postEntity);

        return "/post/detail";
    }

    @PostMapping("/s/post")
    public String write(PostWriteReqDto postWriteReqDto, @AuthenticationPrincipal LoginUser loginUser) {

        postService.게시글쓰기(postWriteReqDto, loginUser.getUser());

        return "redirect:/user/" + loginUser.getUser().getId() + "/post";
    }

    @GetMapping("/s/post/write-form")
    public String writeForm(@AuthenticationPrincipal LoginUser loginUser, Model model) {
        List<Category> categorys = postService.게시글쓰기화면(loginUser.getUser());

        if (categorys.size() == 0) {
            throw new CustomException("카테고리 등록이 필요해요");
        }

        model.addAttribute("categorys", categorys);
        return "/post/writeForm";
    }

    @GetMapping("/user/{pageOwnerId}/post")
    public String postList(Integer categoryId, @PathVariable Integer pageOwnerId,
            @AuthenticationPrincipal LoginUser loginUser,
            Model model,
            @PageableDefault(size = 3) Pageable pageable) {
        // SELECT * FROM category WHERE userId = :id
        // 카테고리 가져가세요!!
        PostRespDto postRespDto = null;

        if (categoryId == null) {
            postRespDto = postService.게시글목록보기(pageOwnerId, pageable);
        } else {
            postRespDto = postService.게시글카테고리별보기(pageOwnerId, categoryId, pageable);
        }

        model.addAttribute("postRespDto", postRespDto);
        return "/post/list";
    }
}
