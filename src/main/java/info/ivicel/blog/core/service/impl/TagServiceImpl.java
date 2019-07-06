package info.ivicel.blog.core.service.impl;

import info.ivicel.blog.core.model.domain.Tag;
import info.ivicel.blog.core.model.vo.TagVO;
import info.ivicel.blog.core.repository.TagRepository;
import info.ivicel.blog.core.service.TagService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("tagService")
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> findAll(Sort sort) {
        return tagRepository.findAll(sort);
    }

    @Override
    public Page<Tag> findAllTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Set<Tag> findByPostId(Long id) {
        // return tagRepository.findByPostId(id);
    return null;
    }

    @Override
    public List<Tag> findTopAndSort(int num, Sort sort) {
        // return tagRepository.findTopAndSortByAssociatedPostCountDesc(num);
        return tagRepository.findTopAndSort(num, sort);
    }

    @Override
    public Optional<TagVO> findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Set<Tag> findByNameIn(Set<String> names) {
        return tagRepository.findByNameIn(names);
    }

    @Override
    @Transactional
    public Set<Tag> addAll(Set<Tag> tags) {
        // saveAll 默认一个一个的插入数据, 并不是 batch insert, 对于一个博客来说, 这性能损失并没什么
        return new HashSet<>(tagRepository.saveAll(tags));
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return tagRepository.findById(id);
    }

    @Override
    @Transactional
    public Tag update(Tag tag) {
        return save(tag);
    }

    @Override
    @Transactional
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    @Transactional
    public void delete(Tag tag) {
        tagRepository.delete(tag);
    }
}
