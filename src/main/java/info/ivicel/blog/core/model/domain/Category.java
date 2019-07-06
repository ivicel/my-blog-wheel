package info.ivicel.blog.core.model.domain;

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
@Table(name = "categories")
public class Category extends BaseDomain {

    private static final long serialVersionUID = 161915469902483403L;

    /**
     * 分类名
     */
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    /**
     * 分类别名
     */
    private String slugName;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 父级分类
     */
    @Column(name = "parent_id", columnDefinition = "bigint(20) default 0")
    private Long parentId;

    /**
     * 分类下文章数量
     */
    @JsonProperty(access = Access.READ_ONLY)
    @Column(name = "post_count", columnDefinition = "int(6) default 0")
    private Long postCount = 0L;

    @ManyToMany(mappedBy = "categories")
    private List<Post> posts;
}
