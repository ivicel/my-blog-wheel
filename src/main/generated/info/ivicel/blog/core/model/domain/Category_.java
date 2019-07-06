package info.ivicel.blog.core.model.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Category.class)
public abstract class Category_ extends info.ivicel.blog.core.model.domain.base.BaseDomain_ {

	public static volatile SingularAttribute<Category, String> name;
	public static volatile SingularAttribute<Category, String> slugName;
	public static volatile SingularAttribute<Category, String> description;
	public static volatile SingularAttribute<Category, Long> postCount;
	public static volatile ListAttribute<Category, Post> posts;
	public static volatile SingularAttribute<Category, Long> parentId;

	public static final String NAME = "name";
	public static final String SLUG_NAME = "slugName";
	public static final String DESCRIPTION = "description";
	public static final String POST_COUNT = "postCount";
	public static final String POSTS = "posts";
	public static final String PARENT_ID = "parentId";

}

