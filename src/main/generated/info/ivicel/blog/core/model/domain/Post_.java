package info.ivicel.blog.core.model.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Post.class)
public abstract class Post_ extends info.ivicel.blog.core.model.domain.base.BaseDomain_ {

	public static volatile SingularAttribute<Post, Long> visitCount;
	public static volatile ListAttribute<Post, Comment> comments;
	public static volatile SingularAttribute<Post, String> bodyHtml;
	public static volatile SetAttribute<Post, Category> categories;
	public static volatile SingularAttribute<Post, String> title;
	public static volatile SingularAttribute<Post, String> body;
	public static volatile SingularAttribute<Post, String> url;
	public static volatile SingularAttribute<Post, String> passwordHash;
	public static volatile SingularAttribute<Post, PostStatus> status;
	public static volatile SingularAttribute<Post, Long> commentCount;
	public static volatile SetAttribute<Post, Tag> tags;

	public static final String VISIT_COUNT = "visitCount";
	public static final String COMMENTS = "comments";
	public static final String BODY_HTML = "bodyHtml";
	public static final String CATEGORIES = "categories";
	public static final String TITLE = "title";
	public static final String BODY = "body";
	public static final String URL = "url";
	public static final String PASSWORD_HASH = "passwordHash";
	public static final String STATUS = "status";
	public static final String COMMENT_COUNT = "commentCount";
	public static final String TAGS = "tags";

}

