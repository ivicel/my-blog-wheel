package info.ivicel.blog.core.model.dto;

import info.ivicel.blog.core.model.domain.PostStatus;
import info.ivicel.blog.core.model.vo.BasePostVO;

public interface PostAdminDTO extends BasePostVO {
    String getPasswordHash();

    PostStatus getStatus();

    Long getVisitCount();

    Long getCommentCount();
}
