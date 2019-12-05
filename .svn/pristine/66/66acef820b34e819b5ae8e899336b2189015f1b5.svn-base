package com.fixit.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.dao.ChatGroupsDao;
import com.fixit.dao.ChatUserGroupDao;
import com.fixit.domain.vo.ChatGroups;
import com.fixit.domain.vo.ChatUserGroup;
import com.fixit.utility.FixitException;

@Service("ChatUserGroupService")
@Transactional
public class ChatUserGroupServiceImpl implements ChatUserGroupService {

	@Autowired
	private ChatUserGroupDao  chatUserGroupDao;
	
	@Override
	@com.google.inject.persist.Transactional
	public void saveChatUserGroup(List<Integer> addUserID,ChatGroups chatGroups) throws FixitException{
		try{
		ChatUserGroup chatUserGroup=new ChatUserGroup();
		for(Integer i:addUserID){
			chatUserGroup.setChatGroups(chatGroups);
			chatUserGroup.setUserId(i);
			chatUserGroup.setCreatedAt(Calendar.getInstance());
			chatUserGroupDao.store(chatUserGroup);
		}
		}catch(Exception e){
			throw new FixitException();
		}
			}
	
	@Override
	@Transactional
	public ChatUserGroup findChatUserGroupBasedOnIds(List<Integer> userIds) throws FixitException{
		ChatUserGroup chatUserGroup=null;
		try{
		chatUserGroup=	chatUserGroupDao.findChatUserGroupBasedOnIds(userIds);
		return  chatUserGroup;
		}catch(Exception e){
			throw new FixitException();
		}
	}
	
}
