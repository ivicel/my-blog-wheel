package info.ivicel.blog.api.backend;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import info.ivicel.blog.common.RestResponse;
import info.ivicel.blog.core.model.domain.Attachment;
import info.ivicel.blog.core.service.AttachmentService;
import info.ivicel.blog.utils.CommonUtil;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController("backendAttachment")
@RequestMapping("/api/admin/attachment")
public class AttachmentEndpoint {
    private static final String THUMB_PREFIX = "Thumb_";
    private static final Set<String> ALL_SUPPORTED_IMAGE_TYPE = new HashSet<>(
            Arrays.asList(IMAGE_GIF_VALUE, IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE));

    private AttachmentService attachmentService;
    private HttpServletRequest request;

    @Autowired
    public AttachmentEndpoint(AttachmentService attachmentService, HttpServletRequest request) {
        this.attachmentService = attachmentService;
        this.request = request;
    }

    @GetMapping
    public ResponseEntity getAttachments(
            @PageableDefault(sort = "createdDate", direction = DESC) Pageable pageable) {
        Page<Attachment> page = attachmentService.findAllAttachment(pageable);
        page.getContent().forEach(t -> t.setPath(request.getContextPath() + t.getPath()));

        return ResponseEntity.ok(page);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity upload(@RequestParam("images") MultipartFile[] images) {
        File parentPath;
        try {
            parentPath = CommonUtil.getParentPath();
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RestResponse.fail("上传失败"));
        }

        Map<String, RestResponse> resultMap = new HashMap<>();
        List<Attachment> attachments = new LinkedList<>();
        for (MultipartFile image : images) {
            String originalFilename = image.getOriginalFilename();
            if (originalFilename == null) {
                originalFilename = image.getName();
            }

            // 检查上传文件类型
            if (!ALL_SUPPORTED_IMAGE_TYPE.contains(image.getContentType())) {
                resultMap.put(originalFilename, RestResponse.fail("不支持的文件类型"));
                continue;
            }

            try {
                String realName = CommonUtil.getImageRealName(originalFilename);
                File fullImagePath = new File(parentPath, realName);
                image.transferTo(fullImagePath);

                File thumbnailImagePath = new File(parentPath, THUMB_PREFIX + realName);
                CommonUtil.createThumbnail(fullImagePath, thumbnailImagePath);

                attachments.add(assembleAttachment(realName, thumbnailImagePath.getName(), originalFilename));
                resultMap.put(originalFilename, RestResponse.success("上传成功"));

            } catch (IOException e) {
                log.error("上传失败: " + originalFilename);
                resultMap.put(originalFilename, RestResponse.fail(e.getMessage()));
            }
        }

        attachments = attachmentService.saveAll(attachments);

        return ResponseEntity.ok(resultMap);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        attachmentService.delete(id);
        return ResponseEntity.ok(RestResponse.success("删除成功"));
    }

    private Attachment assembleAttachment(String realName, String thumbnail,
            String originalFilename) {
        Attachment attach = new Attachment();
        attach.setPath(String.format("/uploads%s", CommonUtil.getDateBasedPath()));
        attach.setRealName(realName);
        attach.setThumbnailName(thumbnail);
        attach.setOriginalName(originalFilename);
        attach.setLocation("server");

        return attach;
    }
}
