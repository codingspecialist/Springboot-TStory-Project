package site.metacoding.blogv3.domain.love;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.blogv3.domain.post.Post;
import site.metacoding.blogv3.domain.user.User;

@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class) // 이 부분 추가
@Entity
@Table(name = "love_tb",
        uniqueConstraints = {
        @UniqueConstraint(name = "love_uk", columnNames = { "post_id", "user_id" })
})
public class Love {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    @CreatedDate // insert 할때만 동작
    private LocalDateTime createDate;
    @LastModifiedDate // update 할때만 동작
    private LocalDateTime updateDate;

    @Builder
    public Love(Integer id, Post post, User user, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
