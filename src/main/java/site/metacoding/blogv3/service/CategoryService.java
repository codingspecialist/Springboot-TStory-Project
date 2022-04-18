package site.metacoding.blogv3.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv3.domain.category.Category;
import site.metacoding.blogv3.domain.category.CategoryRepository;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    // rollbackFor 어떤 예외가 발생했을 때 롤백할지를 정할 수 있다.
    @Transactional
    public void 카테고리등록(Category category) {
        categoryRepository.save(category);
    }

}
