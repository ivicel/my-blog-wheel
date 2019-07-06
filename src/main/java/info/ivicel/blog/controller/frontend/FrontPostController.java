package info.ivicel.blog.controller.frontend;

import info.ivicel.blog.core.exception.PageNotFoundException;
import info.ivicel.blog.core.model.vo.PostVO;
import info.ivicel.blog.core.service.CategoryService;
import info.ivicel.blog.core.service.PostService;
import info.ivicel.blog.core.service.TagService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
public class FrontPostController {

    private PostService postService;
    private final TagService tagService;
    private final CategoryService categoryService;

    @Autowired
    public FrontPostController(PostService postService, TagService tagService, CategoryService categoryService) {
        this.postService = postService;
        this.tagService = tagService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public String getPost(@PathVariable("id") Long id, Model model) {
        PostVO post = getOrThrowNotFound(id, model);
        tagService.findByPostId(post.getId());

        postService.updateVisitCount(id);

        return "post";
    }

    public PostVO getOrThrowNotFound(Long id) {
        return getOrThrowNotFound(id, null);
    }

    public PostVO getOrThrowNotFound(Long id, Model model) {
        Optional<PostVO> post = postService.findPublishedPostViewById(id);
        return post.map(p -> {
            if (model != null) {
                model.addAttribute("post", p);
            }
            return p;
        }).orElseThrow(PageNotFoundException::new);
    }
}
