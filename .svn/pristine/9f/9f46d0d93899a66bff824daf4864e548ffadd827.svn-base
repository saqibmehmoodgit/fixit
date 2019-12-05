package com.fixit.service;

import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.fixit.domain.bo.UserGroupsList;
import com.fixit.domain.vo.ChatGroups;
import com.fixit.domain.vo.ChatUserGroup;
import com.fixit.utility.FixitException;

public interface ChatGroupsService {

	public ChatGroups saveChatGroup(int userid) throws FixitException;
	
	public Set<UserGroupsList> getAllChatGroups(int startIndex,int maxRows) throws FixitException;
	
	public ChatGroups findChatGroupBasedOnGroupId(Integer groupId) throws FixitException;

	public Set<UserGroupsList> FindChatGroupNameByAdminId(int adminId) throws FixitException;

}
