package com.fixit.service;

import java.util.List;

import com.fixit.domain.vo.ChatGroups;
import com.fixit.domain.vo.ChatUserGroup;
import com.fixit.utility.FixitException;

public interface ChatUserGroupService {

	public void saveChatUserGroup(List<Integer> addUserID,ChatGroups chatGroups) throws FixitException;
	
	public ChatUserGroup findChatUserGroupBasedOnIds(List<Integer> userIds) throws FixitException;
	

}
