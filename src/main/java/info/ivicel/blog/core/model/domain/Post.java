package info.ivicel.blog.core.model.domain;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static javax.persistence.ConstraintMode.NO_CONSTRAINT;
import static javax.persistence.EnumType.STRING;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.ivicel.blog.core.model.domain.base.BaseDomain;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post extends BaseDomain {

    private static final long serialVersionUID = 1375333515639184472L;

    /**
     * 文章标题
     */
    private String title;

    /**
     * todo: 文章链接, 默认 title i18n
     */
    private String url;

    /**
     * 文章 Markdown 格式
     */
    @Lob
    @JsonIgnore
    private String body;

    /**
     * 文章 HTML 格式
     */
    @Lob
    @JsonIgnore
    private String bodyHtml;

    /**
     * 文章密码
     */
    private String passwordHash;

    @Enumerated(STRING)
    private PostStatus status;

    private Long visitCount;

    private Long commentCount;

    /**
     * 关联 tag
     */
    @JsonProperty(access = WRITE_ONLY)
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id", columnDefinition = "bigint not null"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", columnDefinition = "bigint not null"),
            indexes = {@Index(columnList = "post_id"), @Index(columnList = "tag_id")},
            foreignKey = @ForeignKey(NO_CONSTRAINT),
            inverseForeignKey = @ForeignKey(NO_CONSTRAINT))
    private Set<Tag> tags = new HashSet<>();

    /**
     * 关联评论
     */
    @JsonProperty(access = WRITE_ONLY)
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "post_comment",
            joinColumns = @JoinColumn(name = "post_id", columnDefinition = "bigint not null"),
            inverseJoinColumns = @JoinColumn(name = "comment_id", columnDefinition = "bigint not null"),
            indexes = {@Index(columnList = "post_id"), @Index(columnList = "comment_id")},
            foreignKey = @ForeignKey(NO_CONSTRAINT),
            inverseForeignKey = @ForeignKey(NO_CONSTRAINT))
    private List<Comment> comments = new ArrayList<>();

    /**
     * 关联分类, 多个分类
     */
    @JsonProperty(access = WRITE_ONLY)
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "post_category",
            joinColumns = @JoinColumn(name = "post_id", columnDefinition = "bigint not null"),
            inverseJoinColumns = @JoinColumn(name = "category_id", columnDefinition = "bigint not null"),
            indexes = {@Index(columnList = "post_id"), @Index(columnList = "category_id")},
            foreignKey = @ForeignKey(NO_CONSTRAINT),
            inverseForeignKey = @ForeignKey(NO_CONSTRAINT))
    private Set<Category> categories = new HashSet<>();
}
