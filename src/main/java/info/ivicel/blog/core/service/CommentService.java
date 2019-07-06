package info.ivicel.blog.core.service;

import info.ivicel.blog.core.model.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    Page<Comment> findAllComment(Pageable pageable);
}
