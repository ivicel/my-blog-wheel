package info.ivicel.blog.controller.frontend;

import info.ivicel.blog.core.model.domain.PostStatus;
import info.ivicel.blog.core.model.vo.BasePostVO;
import info.ivicel.blog.core.service.PostService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("frontArchiveController")
@RequestMapping("/archives")
public class ArchiveController {

    private PostService postService;

    @Autowired
    public ArchiveController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String archives(Model model) {
        List<BasePostVO> posts = postService.findAllByStatusOrderByCreatedDateDesc(
                PostStatus.PUBLISHED);
        model.addAttribute("posts", posts);

        return "archives";
    }
}
