package info.ivicel.blog.core.model.vo;

import info.ivicel.blog.core.model.domain.PostStatus;
import java.util.Set;

public interface PostVO extends BasePostVO {

    String getBody();

    String getBodyHtml();

    String getPasswordHash();

    PostStatus getStatus();

    Set<TagView> getTags();

    interface TagView {
        Long getId();

        String getName();
    }

    Set<CategoryView> getCategories();

    interface CategoryView {
        Long getId();

        String getName();
    }
}
