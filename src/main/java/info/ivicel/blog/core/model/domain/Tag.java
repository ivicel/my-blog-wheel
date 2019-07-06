package info.ivicel.blog.core.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import info.ivicel.blog.core.model.domain.base.BaseDomain;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tags")
public class Tag extends BaseDomain {

    private static final long serialVersionUID = 4579621315866375488L;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * 关联 post 的数量
     */
    @JsonProperty(access = Access.READ_ONLY)
    @Column(name = "associated_post_count", columnDefinition = "int default 0")
    private Long associatedPostCount = 0L;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    public List<Post> posts;
}
