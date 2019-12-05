package com.fixit.dao;

import java.util.List;
import java.util.Set;

import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import com.fixit.domain.vo.ChatUserGroup;
import com.fixit.utility.FixitException;


public interface ChatUserGroupDao extends JpaDao<ChatUserGroup> {

	
	public ChatUserGroup findChatUserGroupBasedOnIds(List<Integer> userIds) throws DataAccessException;
	

}
