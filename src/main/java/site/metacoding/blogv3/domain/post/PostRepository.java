package site.metacoding.blogv3.domain.post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "SELECT * FROM post_tb ORDER BY id DESC LIMIT 0,9", nativeQuery = true)
    List<Post> mFindByPopular();

    @Query("SELECT p FROM Post p WHERE p.user.id = :userId AND p.category.id = :categoryId ORDER BY p.id DESC")
    Page<Post> findByUserIdAndCategoryId(@Param("userId") Integer userId, @Param("categoryId") Integer categoryId,
            Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.user.id = :userId ORDER BY p.id DESC")
    Page<Post> findByUserId(@Param("userId") Integer userId, Pageable pageable);

    @Modifying // INSERT, UPDATE, DELETE
    @Query(value = "INSERT INTO post_tb(category_id, title, content, user_id, thumbnail, createDate, updateDate) VALUES(:categoryId, :title, :content, :userId, :thumbnail, now(), now())", nativeQuery = true)
    void mSave(Integer categoryId, Integer userId, String title, String content, String thumbnail);
}
