package info.ivicel.blog.core.repository;

import info.ivicel.blog.core.model.domain.Tag;
import info.ivicel.blog.core.model.vo.TagVO;
import info.ivicel.blog.core.repository.base.BaseRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends BaseRepository<Tag, Long> {

    Set<Tag> findByPosts(Long id);

    @Query(value = "select * from tags order by associated_post_count desc limit :num", nativeQuery = true)
    List<Tag> findTopAndSortByAssociatedPostCountDesc(@Param("num") int num);

    Optional<TagVO> findByName(String name);

    Set<Tag> findByNameIn(Set<String> names);
}
