package info.ivicel.blog.core.service;

import info.ivicel.blog.core.model.domain.Post;
import info.ivicel.blog.core.model.domain.PostStatus;
import info.ivicel.blog.core.model.dto.PostAdminDTO;
import info.ivicel.blog.core.model.dto.PostEditDTO;
import info.ivicel.blog.core.model.dto.SearchDTO;
import info.ivicel.blog.core.model.vo.BasePostVO;
import info.ivicel.blog.core.model.vo.PostVO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface PostService {

    Page<Post> findAllPost(Pageable pageable);

    List<PostAdminDTO> findAllPostByAdmin(Sort sort);

    Optional<PostVO> findPublishedPostViewById(Long id);

    Optional<PostVO> findPostById(Long id);

    List<BasePostVO> findAllByStatusOrderByCreatedDateDesc(PostStatus status);

    Page<PostAdminDTO> findAllWithAdmin(SearchDTO searchDTO, Pageable pageable);

    Post createPost(PostEditDTO postEditDTO);

    void delete(Long id);

    List<PostAdminDTO> findPostsByCategoryName(String name);

    List<PostAdminDTO> findPostsByTagName(String name);

    Post updatePost(Long id, PostEditDTO postEditDTO);

    void updatePostStatus(Long[] id, PostStatus status);

    void updatePostStatus(Long id, PostStatus status);

    void deletePassword(Long id);

    void updatePassword(Long id, String password);

    List<PostAdminDTO> findPostsByCategoryId(Long id);

    List<PostAdminDTO> findPostsByTagId(Long id);

    Long countAllPost();

    void updateVisitCount(Long id);
}
