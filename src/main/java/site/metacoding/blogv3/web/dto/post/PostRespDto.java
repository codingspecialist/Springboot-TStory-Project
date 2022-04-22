package site.metacoding.blogv3.web.dto.post;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.blogv3.domain.category.Category;
import site.metacoding.blogv3.domain.post.Post;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostRespDto {
    private Page<Post> posts;
    private List<Category> categorys;
    private Integer userId;
    private Integer prev;
    private Integer next;
    private List<Integer> pageNumbers;
}
