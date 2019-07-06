package info.ivicel.blog.core.model.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends info.ivicel.blog.core.model.domain.base.BaseDomain_ {

	public static volatile SingularAttribute<User, String> nickName;
	public static volatile SingularAttribute<User, Integer> tryCount;
	public static volatile SingularAttribute<User, String> avatar;
	public static volatile SingularAttribute<User, String> secret;
	public static volatile SingularAttribute<User, String> passwordHash;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, Date> lastLoginFailedDate;
	public static volatile SingularAttribute<User, String> username;

	public static final String NICK_NAME = "nickName";
	public static final String TRY_COUNT = "tryCount";
	public static final String AVATAR = "avatar";
	public static final String SECRET = "secret";
	public static final String PASSWORD_HASH = "passwordHash";
	public static final String EMAIL = "email";
	public static final String LAST_LOGIN_FAILED_DATE = "lastLoginFailedDate";
	public static final String USERNAME = "username";

}

