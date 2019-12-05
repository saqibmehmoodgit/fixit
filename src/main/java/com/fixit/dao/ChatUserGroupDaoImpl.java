package com.fixit.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
import com.fixit.domain.vo.ChatUserGroup;
import com.fixit.domain.vo.User;
import com.fixit.domain.vo.UserCategory;

@Repository("ChatUserGroupDao")
@Transactional
public class ChatUserGroupDaoImpl extends AbstractJpaDao<ChatUserGroup> implements ChatUserGroupDao {

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
	public boolean canBeMerged(ChatUserGroup o) {
		
		return true;
	}

	@Override
	@Transactional
	public ChatUserGroup findChatUserGroupBasedOnIds(List<Integer> userIds) throws DataAccessException {
		try {
			
			if(userIds!=null & userIds.size()==0){
				 return null;
			}
			long listSize = userIds.size();
			Query query = createNamedQuery("findChatUserGroupBasedOnIds", -1, -1, userIds, listSize);
			return (ChatUserGroup) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}catch(Exception e){
			return null;
		}
	}

}
