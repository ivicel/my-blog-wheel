package info.ivicel.blog.core.model.domain;

import info.ivicel.blog.core.model.domain.base.BaseDomain;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "attachments")
public class Attachment extends BaseDomain {
    private static final long serialVersionUID = 5582913931775399312L;

    private String realName;

    private String originalName;

    private String thumbnailName;

    private String path;

    private String location;
}
