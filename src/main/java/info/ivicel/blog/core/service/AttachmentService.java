package info.ivicel.blog.core.service;

import info.ivicel.blog.core.model.domain.Attachment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AttachmentService {
    List<Attachment> saveAll(List<Attachment> attachments);

    Page<Attachment> findAllAttachment(Pageable pageable);

    void delete(Long id);
}
