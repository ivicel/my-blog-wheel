package info.ivicel.blog.core.model.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Attachment.class)
public abstract class Attachment_ extends info.ivicel.blog.core.model.domain.base.BaseDomain_ {

	public static volatile SingularAttribute<Attachment, String> originalName;
	public static volatile SingularAttribute<Attachment, String> realName;
	public static volatile SingularAttribute<Attachment, String> path;
	public static volatile SingularAttribute<Attachment, String> location;
	public static volatile SingularAttribute<Attachment, String> thumbnailName;

	public static final String ORIGINAL_NAME = "originalName";
	public static final String REAL_NAME = "realName";
	public static final String PATH = "path";
	public static final String LOCATION = "location";
	public static final String THUMBNAIL_NAME = "thumbnailName";

}

