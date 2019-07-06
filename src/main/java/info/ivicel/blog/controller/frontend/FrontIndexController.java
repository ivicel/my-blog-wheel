package info.ivicel.blog.controller.frontend;

import info.ivicel.blog.core.model.domain.Post;
import info.ivicel.blog.core.model.domain.Tag;
import info.ivicel.blog.core.service.PostService;
import info.ivicel.blog.core.service.TagService;
import java.util.List;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Setter(onMethod = @__(@Autowired))
@Controller
@RequestMapping("/")
public class FrontIndexController {
    private PostService postService;
    private TagService tagService;

    @GetMapping
    public String index(@PageableDefault(page = 1) Pageable pageable, Model model) {
        pageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());

        Page<Post> posts = postService.findAllPost(pageable);
        List<Tag> tags = tagService.findTopAndSort(30, Sort.by(Order.desc("associatedPostCount")));

        model.addAttribute("posts", posts);
        model.addAttribute("tags", tags);

        return "index";
    }

    @GetMapping("/hello")
    public String hello() {
        return "my";
    }
}
