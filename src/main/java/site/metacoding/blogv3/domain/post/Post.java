package site.metacoding.blogv3.domain.post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.*;

import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.blogv3.domain.category.Category;
import site.metacoding.blogv3.domain.user.User;
import site.metacoding.blogv3.util.UtilPost;

@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class) // 이 부분 추가
@Table(name = "post_tb")
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 60, nullable = false)
    private String title;

    @Lob
    @Column(nullable = true)
    private String content;

    @Column(length = 200, nullable = true)
    private String thumbnail;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    @CreatedDate // insert 할때만 동작
    private LocalDateTime createDate;
    @LastModifiedDate // update 할때만 동작
    private LocalDateTime updateDate;

    public String getFormatCreateDate(){
        return createDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getFormatContent() {
        return UtilPost.getContentWithoutImg(content);
    }

    @Builder
    public Post(Integer id, String title, String content, String thumbnail, User user, Category category, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.user = user;
        this.category = category;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
