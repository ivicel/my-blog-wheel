package info.ivicel.blog.common;

import lombok.Data;

@Data
public class BlogOptions {

    private String siteName = "我的博客";

    private String siteDescription = "博客描述";

    private String siteUrl = "http://localhost";

    private static final BlogOptions OPTIONS = new BlogOptions();

    public static BlogOptions getInstance() {
        return OPTIONS;
    }
}
