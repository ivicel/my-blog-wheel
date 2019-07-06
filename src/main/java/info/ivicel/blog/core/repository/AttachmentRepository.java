package info.ivicel.blog.core.repository;

import info.ivicel.blog.core.model.domain.Attachment;
import info.ivicel.blog.core.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends BaseRepository<Attachment, Long> {
}
