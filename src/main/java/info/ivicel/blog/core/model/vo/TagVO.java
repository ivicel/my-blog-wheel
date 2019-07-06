package info.ivicel.blog.core.model.vo;

import java.util.List;

public interface TagVO extends BaseVO {

    String getName();

    Long getAssociatedPostCount();

    List<BasePostVO> getPosts();
}
