package info.ivicel.blog.core.service;

import info.ivicel.blog.core.model.domain.Tag;
import info.ivicel.blog.core.model.vo.TagVO;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface TagService {
    List<Tag> findAll(Sort sort);

    Page<Tag> findAllTag(Pageable pageable);

    Set<Tag> findByPostId(Long id);

    List<Tag> findTopAndSort(int num, Sort sort);

    Optional<TagVO> findByName(String name);

    Set<Tag> findByNameIn(Set<String> names);

    Set<Tag> addAll(Set<Tag> tags);

    Optional<Tag> findById(Long id);

    Tag update(Tag tag);

    Tag save(Tag tag);

    void delete(Tag tag);
}
