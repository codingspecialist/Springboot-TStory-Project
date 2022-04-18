package site.metacoding.blogv3.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import site.metacoding.blogv3.util.Script;

@Controller
public class CategoryController {

    @GetMapping("/s/category/writeForm")
    public String writeForm() {
        return "/category/writeForm";
    }

    @PostMapping("/s/category")
    public @ResponseBody String write() {

        return Script.href("/s/category/writeForm", "카테고리 등록 완료");
    }
}
