package info.ivicel.blog.core.service.impl;

import info.ivicel.blog.core.model.domain.Attachment;
import info.ivicel.blog.core.repository.AttachmentRepository;
import info.ivicel.blog.core.service.AttachmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService {
    private AttachmentRepository attachmentRepository;

    @Autowired
    public AttachmentServiceImpl(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    @Transactional
    public List<Attachment> saveAll(List<Attachment> attachments) {
        return attachmentRepository.saveAll(attachments);
    }

    @Override
    public Page<Attachment> findAllAttachment(Pageable pageable) {
        return attachmentRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        attachmentRepository.deleteById(id);
    }
}
