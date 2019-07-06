package info.ivicel.blog.controller.frontend;

import static org.springframework.data.domain.Sort.Direction.DESC;

import info.ivicel.blog.core.exception.PageNotFoundException;
import info.ivicel.blog.core.model.domain.Tag;
import info.ivicel.blog.core.model.vo.TagVO;
import info.ivicel.blog.core.service.TagService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("frontTagController")
@RequestMapping("/tag")
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/all")
    public String findAll(@SortDefault(value = "associatedPostCount", direction = DESC) Sort sort,
            Model model) {
        List<Tag> tags = tagService.findAll(sort);
        model.addAttribute("tags", tags);

        return "tags";
    }

    @GetMapping("/{name}")
    public String getTag(@PathVariable("name") String name, Model model) {
        Optional<TagVO> tag = tagService.findByName(name);
        tag.map((t) -> {
            model.addAttribute("tag", t);
            return t;
        }).orElseThrow(PageNotFoundException::new);

        return "tag";
    }


}
