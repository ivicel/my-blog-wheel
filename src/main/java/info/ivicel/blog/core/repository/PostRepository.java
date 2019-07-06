package info.ivicel.blog.core.repository;

import info.ivicel.blog.core.model.domain.Post;
import info.ivicel.blog.core.model.domain.PostStatus;
import info.ivicel.blog.core.model.dto.PostAdminDTO;
import info.ivicel.blog.core.model.vo.BasePostVO;
import info.ivicel.blog.core.model.vo.PostVO;
import info.ivicel.blog.core.repository.base.BaseRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends BaseRepository<Post, Long> {

    Page<Post> findAllByStatus(PostStatus published, Pageable pageable);

    Optional<PostVO> findByIdAndStatus(Long id, PostStatus status);

    List<BasePostVO> findAllByStatusOrderByCreatedDateDesc(PostStatus status);

    List<PostAdminDTO> findPostAdminProjectionBy(Sort sort);

    Page<PostAdminDTO> findPostAdminProjectionBy(Specification<PostAdminDTO> specification, Pageable pageable);

    Optional<PostVO> findDTOById(Long id, Class<PostVO> clazz);

    List<PostAdminDTO> findPostAdminProjectionByCategoriesName(String name);

    List<PostAdminDTO> findPostAdminProjectionByTagsName(String name);

    @Modifying
    @Query("update Post p set p.status = :status where p.id in (:id)")
    void updatePostStatusById(@Param("id") Long[] id, @Param("status") PostStatus status);

    @Modifying
    @Query("update Post p set p.status = :status where p.id = :id")
    void updatePostStatusById(@Param("id") Long id, @Param("status") PostStatus status);

    @Modifying
    @Query("update Post p set p.passwordHash = :password where p.id = :id")
    void updatePostPasswordById(@Param("id") Long id, @Param("password") String password);

    List<PostAdminDTO> findPostAdminProjectionByCategoriesId(Long id);

    List<PostAdminDTO> findPostAdminProjectionByTagsId(Long id);

    @Modifying
    @Query("update Post p set p.visitCount = p.visitCount + 1 where p.id = :id")
    void updateVisitCount(@Param("id") Long id);
}
