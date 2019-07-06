package info.ivicel.blog.core.model.domain;

import info.ivicel.blog.core.model.domain.base.BaseDomain;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment extends BaseDomain {

    private static final long serialVersionUID = -2301818933585277056L;
}
