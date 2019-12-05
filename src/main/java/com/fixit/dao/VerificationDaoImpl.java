package com.fixit.dao;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.fixit.domain.vo.Verification;
import com.fixit.utility.FixitException;

@Repository("VerificationDao")
@Transactional
public class VerificationDaoImpl extends AbstractJpaDao<Verification> implements VerificationDao{

	
	@PersistenceContext(unitName = "fixit")
	private EntityManager entityManager;

	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(
			Arrays.asList(new Class<?>[] { Verification.class }));

	public VerificationDaoImpl() {
		super();
	}
	
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public Set<Class<?>> getTypes() {
		return dataTypes;
	}

	@Override
	public boolean canBeMerged(Verification o) {
		return true;
	}

	@Override
	@Transactional
	public Set<Verification> findVerificationByUserId(Integer userId) throws DataAccessException {
		try{
			Query query = createNamedQuery("findVerificationByUserId", -1, -1,userId);
			return new LinkedHashSet<Verification>(query.getResultList());
		}catch(Exception e){
			return null;	
		}
		
	}
	
	
	@Override
	@Transactional
	public int deleteVerificationByUserId(Integer userId) throws DataAccessException {
		try {
			Query query = createNamedQuery("deleteVerificationByUserId",-1, -1 ,userId );
			 return query.executeUpdate();
		 }catch (NoResultException nre) {
			return 0;
		}		
	}

	@Override
	public Verification findVerificationByHashCode(String hashCode , Calendar time) throws DataAccessException {
		try {
			Query query = createNamedQuery("findVerificationByHashCode",-1, -1 ,hashCode,time);
			return (Verification) query.getSingleResult();
		 }catch (NoResultException nre) {
			return null;
		}	
	}
	
	

}
