package info.ivicel.blog.core.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public interface BaseVO {
    Long getId();

    @JsonFormat(pattern = "yyyy-MM-dd")
    Date getCreatedDate();

    @JsonFormat(pattern = "yyyy-MM-dd")
    Date getLastModifiedDate();
}
