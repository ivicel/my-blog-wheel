package info.ivicel.blog.core.repository.base;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutorWithProjection<T>,
        JpaSpecificationExecutor<T> {

    List<T> findTopAndSort(int num, Sort sort);

    List<T> saveIgnoreDuplicate(Iterable<T> entities);

    // <P> Page<P> findAllProjection(Specification<P> specification, Class<P> projectionType, Pageable pageable);
}
