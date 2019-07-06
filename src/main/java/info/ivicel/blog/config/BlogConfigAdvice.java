package info.ivicel.blog.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class BlogConfigAdvice {
    private Map<String, Object> options = new HashMap<>();

    @ModelAttribute("BlogOptions")
    // public BlogOptions options() {
    //     return BlogOptions.getInstance();
    // }
    public Map<String, Object> options() {
        options.put("site_url", "localhost:8088");
        options.put("site_name", "我的博客");
        options.put("site_description", "博客描述博客描述博客描述博客描述博客描述博客描述博客描述博客描述");

        options.put("hux_home_cover", "/static/images/header.jpg");

        return options;
    }
}
