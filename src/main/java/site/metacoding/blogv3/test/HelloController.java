package site.metacoding.blogv3.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
