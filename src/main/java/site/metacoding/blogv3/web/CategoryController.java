package site.metacoding.blogv3.web;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv3.config.auth.SessionUser;
import site.metacoding.blogv3.domain.category.Category;
import site.metacoding.blogv3.domain.user.User;
import site.metacoding.blogv3.service.CategoryService;
import site.metacoding.blogv3.util.Script;
import site.metacoding.blogv3.dto.category.CategoryWriteReqDto;

@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final HttpSession session;
    private final CategoryService categoryService;

    @GetMapping("/s/category/writeForm")
    public String writeForm() {
        return "/category/writeForm";
    }

    @PostMapping("/s/category")
    public @ResponseBody String write(
            @Valid CategoryWriteReqDto categoryWriteReqDto,
            Errors errors,
            @AuthenticationPrincipal SessionUser loginUser) {

        User principal = loginUser.getUser();
        Category category = categoryWriteReqDto.toEntity(principal);

        categoryService.카테고리등록(category);

        return Script.href("/s/category/writeForm", "카테고리 등록 완료");
    }
}
