package site.metacoding.blogv3.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.blogv3.domain.post.Post;
import site.metacoding.blogv3.domain.post.PostRepository;
import site.metacoding.blogv3.util.email.EmailUtil;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

    private final PostRepository postRepository;

    @GetMapping({ "/" })
    public String main(Model model) {

        List<Post> postsEntity = postRepository.mFindByPopular();
        model.addAttribute("posts", postsEntity);
        return "main";
    }
}
