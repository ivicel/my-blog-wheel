package info.ivicel.blog.core.service;

import info.ivicel.blog.core.model.domain.Category;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface CategoryService {

    Page<Category> findAllPagable(Pageable pageable);

    List<Category> findAll(Sort sort);

    Set<Category> findByNameIn(Collection<String> names);

    Set<Category> addAll(Collection<Category> collect);

    Optional<Category> findById(Long id);

    Category save(Category category);

    Category update(Category category);

    void delete(Category category);

    boolean exist(Long id);

    Optional<Category> findByCategoryName(String name);
}
