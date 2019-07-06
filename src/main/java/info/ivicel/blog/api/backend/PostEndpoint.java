package info.ivicel.blog.api.backend;

import static org.springframework.data.domain.Sort.Direction.DESC;

import info.ivicel.blog.common.RestResponse;
import info.ivicel.blog.core.exception.RestResultNotExistsRexception;
import info.ivicel.blog.core.model.domain.User;
import info.ivicel.blog.core.model.dto.PostAdminDTO;
import info.ivicel.blog.core.model.dto.PostEditDTO;
import info.ivicel.blog.core.model.dto.PostStatusDTO;
import info.ivicel.blog.core.model.dto.SearchDTO;
import info.ivicel.blog.core.model.vo.PostVO;
import info.ivicel.blog.core.repository.PostRepository;
import info.ivicel.blog.core.service.PostService;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("backendPost")
@RequestMapping("/api/admin/post")
public class PostEndpoint {
    private PostService postService;
    private PostRepository postRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public PostEndpoint(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    /**
     * 一次获取所有 post
     */
    @GetMapping("/all")
    public ResponseEntity<List<PostAdminDTO>> getAllPost(
            @SortDefault(sort = "createdDate", direction = DESC) Sort sort) {
        List<PostAdminDTO> posts = postService.findAllPostByAdmin(sort);

        return ResponseEntity.ok(posts);
    }

    /**
     * 分页获取
     */
    @GetMapping
    public ResponseEntity<Page<PostAdminDTO>> getPostPageable(
            SearchDTO searchDTO,
            @PageableDefault(sort = "createdDate") Pageable pageable) {
        return ResponseEntity.ok(postService.findAllWithAdmin(searchDTO, pageable));
    }

    /**
     * 获取单条 post
     */
    @GetMapping(value = "/{id:[0-9]+}")
    public ResponseEntity<PostVO> getPost(@PathVariable("id") Long id) {
        Optional<PostVO> optional = postService.findPostById(id);
        return optional.map(ResponseEntity::ok)
                .orElseThrow(() -> new RestResultNotExistsRexception("找不到文章"));
    }

    /**
     * 保存文章
     */
    @PostMapping
    public ResponseEntity create(Authentication auth, @Valid @RequestBody PostEditDTO postEditDTO) {
        postEditDTO.setUser((User) auth.getDetails());
        postService.createPost(postEditDTO);

        return ResponseEntity.ok(RestResponse.success("操作成功"));
    }

    /**
     * 更新文章
     */
    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity update(@PathVariable("id") Long id,
            Authentication auth, @Valid @RequestBody PostEditDTO postEditDTO) {
        postEditDTO.setUser((User) auth.getDetails());
        postService.updatePost(id, postEditDTO);

        return ResponseEntity.ok(RestResponse.success("更新成功"));
    }

    /**
     * 更新文章状态
     */
    @PutMapping("/status")
    public ResponseEntity updateStatus(@RequestBody PostStatusDTO postStatusDTO) {
        if (postStatusDTO.getIds().length > 0) {
            postService.updatePostStatus(postStatusDTO.getIds(), postStatusDTO.getStatus());
        } else if (postStatusDTO.getId() != null) {
            postService.updatePostStatus(postStatusDTO.getId(), postStatusDTO.getStatus());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestResponse.fail("文章不能为空"));
        }

        return ResponseEntity.ok(RestResponse.success("成功更新文章状态"));
    }

    /**
     * 根据文章 id 删除文章
     */
    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        postService.delete(id);

        return ResponseEntity.ok(RestResponse.success("删除文章成功"));
    }

    /**
     * 设置文章密码
     */
    @PutMapping("/{id:[0-9]+}/password")
    public ResponseEntity updatePassword(@PathVariable("id") Long id,
            @RequestParam("password") String password) {
        if (StringUtils.isEmpty(password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestResponse.fail("密码不能为空"));
        }
        postService.updatePassword(id, password);

        return ResponseEntity.ok(RestResponse.success(password));
    }

    @DeleteMapping("/{id:[0-9]+}/password")
    public ResponseEntity deletePassword(@PathVariable("id") Long id) {
        postService.deletePassword(id);
        return ResponseEntity.ok(RestResponse.success("删除密码成功"));
    }

    @GetMapping("/summary")
    public ResponseEntity getSummary() {
        Long count = postService.countAllPost();
        return ResponseEntity.ok(count);
    }
}
