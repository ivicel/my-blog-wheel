package info.ivicel.blog.api.backend;

import info.ivicel.blog.common.RestResponse;
import info.ivicel.blog.core.exception.RestResultNotExistsRexception;
import info.ivicel.blog.core.model.domain.Tag;
import info.ivicel.blog.core.model.dto.PostAdminDTO;
import info.ivicel.blog.core.service.PostService;
import info.ivicel.blog.core.service.TagService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("backendTag")
@RequestMapping("/api/admin/tag")
public class TagEndpoint {
    private TagService tagService;
    private PostService postService;

    @Autowired
    public TagEndpoint(TagService tagService, PostService postService) {
        this.tagService = tagService;
        this.postService = postService;
    }

    /**
     * 分页获取 tag
     * @param pageable
     * @return
     */
    @GetMapping
    public Page<Tag> getTagPageable(@PageableDefault(sort = "createdDate") Pageable pageable) {
        return tagService.findAllTag(pageable);
    }

    /**
     * 获取所有 tag
     * @param sort
     * @return
     */
    @GetMapping("/all")
    public List<Tag> getAll(@SortDefault("createdDate") Sort sort) {
        return tagService.findAll(sort);
    }

    /**
     * 获取指定 tag 下的所有 post
     * @param id
     * @return
     */
    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostAdminDTO>> getPostByTagName(@PathVariable("id") Long id) {
        List<PostAdminDTO> posts = postService.findPostsByTagId(id);
        return ResponseEntity.ok(posts);
    }

    /**
     * 获取 tag 详细信息
     * @param id
     * @return
     */
    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<Tag> getTagDetail(@PathVariable("id") Long id) {
        Optional<Tag> optional = tagService.findById(id);
        return optional.map(ResponseEntity::ok).orElseThrow(() -> new RestResultNotExistsRexception("标签不存在"));
    }

    /**
     * 更新 tag
     * @param tag
     * @return
     */
    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<Tag> update(@PathVariable("id") Long id, @RequestBody Tag tag) {
        tag.setId(id);
        tag = tagService.update(tag);

        return ResponseEntity.ok(tag);
    }

    /**
     * 添加新 tag
     * @param tag
     * @return
     */
    @PostMapping
    public ResponseEntity<Tag> create(@RequestBody Tag tag) {
        tag = tagService.save(tag);

        return ResponseEntity.ok(tag);
    }

    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Tag> optional = tagService.findById(id);
        if (!optional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(RestResponse.fail("标签不存在"));
        }

        if (optional.get().getAssociatedPostCount() > 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(RestResponse.fail("标签下文章数量不为 0"));
        }

        tagService.delete(optional.get());

        return ResponseEntity.ok(RestResponse.success("删除成功"));
    }
}
