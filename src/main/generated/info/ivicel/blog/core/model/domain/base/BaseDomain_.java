package info.ivicel.blog.core.model.domain.base;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BaseDomain.class)
public abstract class BaseDomain_ {

	public static volatile SingularAttribute<BaseDomain, Date> createdDate;
	public static volatile SingularAttribute<BaseDomain, Date> lastModifiedDate;
	public static volatile SingularAttribute<BaseDomain, Long> id;

	public static final String CREATED_DATE = "createdDate";
	public static final String LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String ID = "id";

}

