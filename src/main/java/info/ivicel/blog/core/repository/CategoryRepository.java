package info.ivicel.blog.core.repository;

import info.ivicel.blog.core.model.domain.Category;
import info.ivicel.blog.core.repository.base.BaseRepository;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {
    Set<Category> findByNameIn(Collection<String> names);

    Optional<Category> findByName(String name);
}
