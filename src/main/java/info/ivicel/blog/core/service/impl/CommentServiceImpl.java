package info.ivicel.blog.core.service.impl;

import info.ivicel.blog.core.model.domain.Comment;
import info.ivicel.blog.core.repository.CommentRepository;
import info.ivicel.blog.core.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    @Override
    public Page<Comment> findAllComment(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }
}
