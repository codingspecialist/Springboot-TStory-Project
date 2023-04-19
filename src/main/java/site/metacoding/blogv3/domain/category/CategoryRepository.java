package site.metacoding.blogv3.domain.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT ct FROM Category ct WHERE ct.user.id = :userId")
    List<Category> findByUserId(@Param("userId") Integer userId);
}
