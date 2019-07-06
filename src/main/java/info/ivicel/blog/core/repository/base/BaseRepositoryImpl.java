package info.ivicel.blog.core.repository.base;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.repository.NoRepositoryBean;
import th.co.geniustree.springdata.jpa.repository.support.JpaSpecificationExecutorWithProjectionImpl;


@NoRepositoryBean
public class BaseRepositoryImpl<T, ID extends Serializable> extends JpaSpecificationExecutorWithProjectionImpl<T, ID>
        implements BaseRepository<T, ID> {
    private JpaEntityInformation<T, ?> entityInformation;
    private EntityManager entityManager;

    private final ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
            EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
    }


    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);
    }

    @Override
    public List<T> findTopAndSort(int num, Sort sort) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityInformation.getJavaType());
        Root<T> root = query.from(entityInformation.getJavaType());
        query.select(root).orderBy(toOrders(sort, root, cb));
        TypedQuery<T> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(0);
        typedQuery.setMaxResults(num);

        return typedQuery.getResultList();
    }

    @Override
    public List<T> saveIgnoreDuplicate(Iterable<T> entities) {
//        entityInformation.getEntityName()

        return null;
    }
}
