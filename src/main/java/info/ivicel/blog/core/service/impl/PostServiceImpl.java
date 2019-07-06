package info.ivicel.blog.core.service.impl;

import info.ivicel.blog.core.model.domain.Category;
import info.ivicel.blog.core.model.domain.Post;
import info.ivicel.blog.core.model.domain.PostStatus;
import info.ivicel.blog.core.model.domain.Tag;
import info.ivicel.blog.core.model.dto.PostAdminDTO;
import info.ivicel.blog.core.model.dto.PostEditDTO;
import info.ivicel.blog.core.model.dto.SearchDTO;
import info.ivicel.blog.core.model.vo.BasePostVO;
import info.ivicel.blog.core.model.vo.PostVO;
import info.ivicel.blog.core.repository.PostRepository;
import info.ivicel.blog.core.service.CategoryService;
import info.ivicel.blog.core.service.PostService;
import info.ivicel.blog.core.service.TagService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("postServcie")
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, CategoryService categoryService, TagService tagService,
            PasswordEncoder passwordEncoder) {
        this.postRepository = postRepository;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<Post> findAllPost(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public List<PostAdminDTO> findAllPostByAdmin(Sort sort) {
        return postRepository.findPostAdminProjectionBy(sort);
    }

    @Override
    public Optional<PostVO> findPublishedPostViewById(Long id) {
        return postRepository.findByIdAndStatus(id, PostStatus.PUBLISHED);
    }

    @Override
    public Optional<PostVO> findPostById(Long id) {
        return postRepository.findDTOById(id, PostVO.class);
    }

    @Override
    public List<BasePostVO> findAllByStatusOrderByCreatedDateDesc(PostStatus status) {
        return postRepository.findAllByStatusOrderByCreatedDateDesc(status);
    }

    @Override
    public Page<PostAdminDTO> findAllWithAdmin(SearchDTO searchDTO, Pageable pageable) {
        // return postRepository.findPostAdminProjectionBy((Specification<PostAdminDTO>) (root, query, cb) -> {
        //     String keyword = searchDTO.getKeyword();
        //     if (StringUtils.isNotEmpty()) {
        //
        //     }
        // }, pageable);
        return postRepository.findAll((root, query, cb) -> {
            String keyword = searchDTO.getKeyword();
            String status = searchDTO.getStatus();
            Long id = searchDTO.getCategoryId();
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNoneEmpty(keyword)) {
                predicates.add(cb.like(root.get("title"), "%" + keyword + "%"));
            }


            if (StringUtils.isNotEmpty(status)) {
                predicates.add(cb.equal(root.get("status"), PostStatus.get(status)));
            }

            if (id != null) {
                Join<Post, Category> join = root.join("category");
                predicates.add(cb.equal(join.get("id"), id));
            }

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));

        }, PostAdminDTO.class, pageable);
    }

    @Override
    @Transactional
    public Post createPost(PostEditDTO postEditDTO) {
        Post post = handlePost(postEditDTO);
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<PostAdminDTO> findPostsByCategoryName(String name) {
        return postRepository.findPostAdminProjectionByCategoriesName(name);
    }

    @Override
    public List<PostAdminDTO> findPostsByTagName(String name) {
        return postRepository.findPostAdminProjectionByTagsName(name);
    }

    @Override
    @Transactional
    public Post updatePost(Long id, PostEditDTO postEditDTO) {
        Post post = transformToPost(postEditDTO);
        // 别忘了设置 post id
        post.setId(id);

        return postRepository.save(post);
    }

    @Override
    @Transactional
    public void updatePostStatus(Long[] ids, PostStatus status) {
        postRepository.updatePostStatusById(ids, status);
    }

    @Override
    @Transactional
    public void updatePostStatus(Long id, PostStatus status) {
        postRepository.updatePostStatusById(id, status);
    }

    @Override
    @Transactional
    public void deletePassword(Long id) {
        updatePassword(id, null);
    }

    @Override
    @Transactional
    public void updatePassword(Long id, String password) {
        if (StringUtils.isNotEmpty(password)) {
            password = passwordEncoder.encode(password);
        }
        postRepository.updatePostPasswordById(id, password);
    }

    @Override
    public List<PostAdminDTO> findPostsByCategoryId(Long id) {
        return postRepository.findPostAdminProjectionByCategoriesId(id);
    }

    @Override
    public List<PostAdminDTO> findPostsByTagId(Long id) {
        return postRepository.findPostAdminProjectionByTagsId(id);
    }

    @Override
    public Long countAllPost() {
        return postRepository.count();
    }

    @Override
    @Transactional
    public void updateVisitCount(Long id) {
        postRepository.updateVisitCount(id);
    }

    private Post handlePost(PostEditDTO postEditDTO) {
        // 处理分类, 查出已有的, 添加新分类
        Set<Category> categories = getCategories(postEditDTO.getCategories());

        // 处理 tag, 查旧添新
        Set<Tag> tags = getTags(postEditDTO.getTags());

        Post post = transformToPost(postEditDTO);
        post.setCategories(categories);
        post.setTags(tags);

        return post;
    }

    private Post transformToPost(PostEditDTO postEditDTO) {
        Post post = new Post();
        post.setTitle(postEditDTO.getTitle());
        post.setBody(postEditDTO.getBody());
        post.setBodyHtml(postEditDTO.getBodyHtml());
        post.setUrl(postEditDTO.getUrl());
        post.setStatus(postEditDTO.getStatus());
        if (StringUtils.isNotEmpty(postEditDTO.getPassword().trim())) {
            post.setPasswordHash(passwordEncoder.encode(postEditDTO.getPassword().trim()));
        }

        return post;
    }

    private Set<Category> getCategories(Set<String> names) {
        // 根据名称过滤掉已存在的 category
        Set<Category> categories = categoryService.findByNameIn(names);
        names.removeAll(categories.stream().map(Category::getName).collect(Collectors.toSet()));

        // 添加新的 category
        categories.addAll(categoryService.addAll(names.stream().map(n -> {
            Category cate = new Category();
            cate.setName(n);
            return cate;
        }).collect(Collectors.toSet())));

        return categories;
    }

    private Set<Tag> getTags(Set<String> names) {
        Set<Tag> tags = tagService.findByNameIn(names);
        names.removeAll(tags.stream().map(Tag::getName).collect(Collectors.toSet()));
        tags.addAll(tagService.addAll(names.stream().map(n -> {
            Tag tag = new Tag();
            tag.setName(n);
            return tag;
        }).collect(Collectors.toSet())));

        tags.forEach(t -> t.setAssociatedPostCount(t.getAssociatedPostCount() + 1));

        return tags;
    }
}
