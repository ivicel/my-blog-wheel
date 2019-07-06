package info.ivicel.blog.core.model.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Tag.class)
public abstract class Tag_ extends info.ivicel.blog.core.model.domain.base.BaseDomain_ {

	public static volatile SingularAttribute<Tag, Long> associatedPostCount;
	public static volatile SingularAttribute<Tag, String> name;
	public static volatile ListAttribute<Tag, Post> posts;

	public static final String ASSOCIATED_POST_COUNT = "associatedPostCount";
	public static final String NAME = "name";
	public static final String POSTS = "posts";

}

