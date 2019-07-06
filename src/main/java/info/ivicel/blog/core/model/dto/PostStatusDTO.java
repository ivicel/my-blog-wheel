package info.ivicel.blog.core.model.dto;

import info.ivicel.blog.core.model.domain.PostStatus;
import lombok.Data;

@Data
public class PostStatusDTO {
    private Long[] ids = new Long[0];

    private Long id;

    private PostStatus status;
}
