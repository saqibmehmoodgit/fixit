package com.fixit.dao;

import java.util.Arrays;
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

import com.fixit.domain.vo.ChatGroups;
import com.fixit.domain.vo.ChatMessage;
import com.fixit.domain.vo.MessageNotification;
import com.fixit.domain.vo.User;
import com.fixit.domain.vo.UserCategory;

@Repository("MessageNotificationDao")
@Transactional
public class MessageNotificationDaoImpl extends AbstractJpaDao<MessageNotification> implements MessageNotificationDao{


	@PersistenceContext(unitName = "fixit")
	private EntityManager entityManager;

	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(
			Arrays.asList(new Class<?>[] { UserCategory.class }));
	
	@Override
	public EntityManager getEntityManager() {
		
		return entityManager;
	}

	@Override
	public Set<Class<?>> getTypes() {
	
		return dataTypes;
	}

	@Override
	public boolean canBeMerged(MessageNotification o) {
		
		return true;
	}



	@Override
	@Transactional
	public long getAllNotificationBasedOnId(Integer notificationId) throws DataAccessException {
		 try {
			 Query query = createNamedQuery("getAllNotificatiosBasedOnGroupId",-1, -1,notificationId);
				 return (long) (query.getSingleResult());
		 } 
		 catch(Exception e){
		return 0;
		 }
	}

	@Override
	@Transactional
	public Set<MessageNotification> updateMessageStatus(int userId) {
		
		 try {
			 Query query = createNamedQuery("updatedMessageStatuses",-1, -1,userId);
				 query.executeUpdate();
				 return null;
		 } 
		 catch(Exception e){
		return null;
		 }
	}

	
	
}
