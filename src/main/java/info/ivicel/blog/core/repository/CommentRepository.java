package info.ivicel.blog.core.repository;

import info.ivicel.blog.core.model.domain.Comment;
import info.ivicel.blog.core.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends BaseRepository<Comment, Long> {

}
