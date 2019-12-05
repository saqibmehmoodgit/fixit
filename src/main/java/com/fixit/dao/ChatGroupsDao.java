package com.fixit.dao;

import java.util.List;
import java.util.Set;

import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import com.fixit.domain.vo.ChatGroups;
import com.fixit.utility.FixitException;


public interface ChatGroupsDao extends JpaDao<ChatGroups>  {

	public Set<ChatGroups> getAllChatGroups(int startIndex,int maxRows) throws DataAccessException;
	
	public ChatGroups findChatGroupBasedOnGroupId(Integer groupId) throws DataAccessException;
	
	public List<Object> FindChatGroupNameByAdminId(Integer userId) throws DataAccessException;

}
