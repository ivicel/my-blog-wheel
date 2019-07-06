package info.ivicel.blog.api.backend;

import info.ivicel.blog.common.RestResponse;
import info.ivicel.blog.core.exception.RestResultNotExistsRexception;
import info.ivicel.blog.core.model.domain.Category;
import info.ivicel.blog.core.model.dto.PostAdminDTO;
import info.ivicel.blog.core.service.CategoryService;
import info.ivicel.blog.core.service.PostService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

@RestController("backendCategory")
@RequestMapping("/api/admin/category")
public class CategoryEndpoint {
    private CategoryService categoryService;
    private PostService postService;

    @Autowired
    public CategoryEndpoint(CategoryService categoryService, PostService postService) {
        this.categoryService = categoryService;
        this.postService = postService;
    }

    /**
     * 分页查询 category
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<Category>> getByPage(@PageableDefault(sort = "createdDate") Pageable pageable) {
        return ResponseEntity.ok(categoryService.findAllPagable(pageable));
    }

    /**
     * 获取所有 category
     * @param sort
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAll(@SortDefault("createdDate") Sort sort) {
        return ResponseEntity.ok(categoryService.findAll(sort));
    }

    /**
     * 按 id 查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable("id") Long id) {
        Optional<Category> optional = categoryService.findById(id);
        return optional.map(ResponseEntity::ok).orElseThrow(() -> new RestResultNotExistsRexception("找不到分类"));
    }

    /**
     * 查询分类下的所有文章
     * @param id
     * @return
     */
    @GetMapping("/{id:[0-9]+}/posts")
    public ResponseEntity<List<PostAdminDTO>> getPostByCategoryName(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.findPostsByCategoryId(id));
    }

    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Category> cate = categoryService.findById(id);
        if (!cate.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestResponse.fail("分类不存在"));
        }

        if (cate.get().getPostCount() > 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestResponse.fail("分类下文章数量不为 0"));
        }

        categoryService.delete(cate.get());

        return ResponseEntity.ok(RestResponse.success("删除成功"));
    }

    /**
     * 更新分类
     * @param category
     * @return
     */
    @PutMapping("{id:[0-9]+}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody Category category) {
        category.setId(id);
        ResponseEntity entity = checkParentCategory(category);
        if (entity != null) {
            return entity;
        }

        try {
            categoryService.update(category);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(RestResponse.fail(String.format("分类名 %s 已经存在", category.getName())));
        }
        return ResponseEntity.ok(RestResponse.success("更新成功"));
    }

    /**
     * 添加新分类
     * @param category
     * @return
     */
    @PostMapping
    public ResponseEntity create(@RequestBody Category category) {
        ResponseEntity entity = checkParentCategory(category);
        if (entity != null) {
            return entity;
        }

        category.setId(null);
        category = categoryService.save(category);

        return ResponseEntity.ok(category);
    }

    private ResponseEntity checkParentCategory(Category category) {
        // 默认顶级分类
        if (category.getParentId() == null || category.getParentId().equals(category.getId())) {
            category.setParentId(0L);
        } else if (!categoryService.exist(category.getParentId())) {
            // 确保父级分类存在
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(RestResponse.fail("请求错误, 父级分类不存在"));
        }

        return null;
    }
}
