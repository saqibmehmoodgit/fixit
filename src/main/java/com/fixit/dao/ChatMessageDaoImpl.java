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
import com.fixit.domain.vo.User;
import com.fixit.domain.vo.UserCategory;

@Repository("ChatMessageDao")
@Transactional
public class ChatMessageDaoImpl extends AbstractJpaDao<ChatMessage> implements ChatMessageDao{


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
	public boolean canBeMerged(ChatMessage o) {
		
		return true;
	}

	@Override
	@Transactional
	public Set<ChatMessage> getAllChatMessagesBasedOnGroupId(Integer chatGroupId) throws DataAccessException{
		 try {
			 Query query = createNamedQuery("getAllChatMessagesBasedOnGroupId",-1, -1,chatGroupId);
				 return new LinkedHashSet<ChatMessage>(query.getResultList());
		 } 
		 catch(Exception e){
		return null;
		 }
	}
	
}
