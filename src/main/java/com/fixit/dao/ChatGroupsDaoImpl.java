package com.fixit.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
import com.fixit.domain.vo.ChatMessage;
import com.fixit.domain.vo.ChatUserGroup;
import com.fixit.domain.vo.UserCategory;

@Repository("ChatGroupsDao")
@Transactional
public class ChatGroupsDaoImpl extends AbstractJpaDao<ChatGroups> implements ChatGroupsDao {

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
	public boolean canBeMerged(ChatGroups o) {
		
		return true;
	}

	@Override
	@Transactional
	public Set<ChatGroups> getAllChatGroups(int startIndex, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("getAllChatGroups", startIndex, maxRows);
			return new LinkedHashSet<ChatGroups>(query.getResultList());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public ChatGroups findChatGroupBasedOnGroupId(Integer groupId) throws DataAccessException {
		try {

			Query query = createNamedQuery("findChatGroupBasedOnGroupId", -1, -1, groupId);
			return (ChatGroups) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<Object> FindChatGroupNameByAdminId(Integer userId) throws DataAccessException {
		try {
			String nativeQuery = "select  distinct result.chat_group_id , result.name from "
					+ "(SELECT DISTINCT chatUserGroup.chat_group_id ,chatMessage.chat_message, "
					+ " (select chat_groups.chat_group_name from chat_groups where chat_groups.chat_group_id=chatUserGroup.chat_group_id) as name "
					+ " FROM  chatuser_group chatUserGroup  Inner JOIN chat_message chatMessage "
					+ " ON chatUserGroup.chat_group_id = chatMessage.chat_group_id  WHERE  chatUserGroup.user_id = "
					+ userId + 
					" order by chatMessage.updated_at desc ) as result";
			javax.persistence.Query query = entityManager.createNativeQuery(nativeQuery);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}

	}

}
