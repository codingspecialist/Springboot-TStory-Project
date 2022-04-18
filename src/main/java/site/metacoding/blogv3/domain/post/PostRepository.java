package site.metacoding.blogv3.domain.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query(value = "SELECT * FROM post WHERE userId = :userId", nativeQuery = true)
    List<Post> findByUserId(@Param("userId") Integer userId);
}
