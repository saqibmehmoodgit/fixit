package com.fixit.service;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.dao.ChatGroupsDao;
import com.fixit.domain.bo.UserGroupsList;
import com.fixit.domain.vo.ChatGroups;
import com.fixit.domain.vo.ChatUserGroup;
import com.fixit.domain.vo.User;
import com.fixit.utility.FixitException;

@Service("ChatGroupsService")
@Transactional
public class ChatGroupsServiceImpl implements ChatGroupsService{
	
	@Autowired
	private ChatGroupsDao  chatGroupsDao;
	
	@Autowired
	private UserService userService;
	
	@Override
	@Transactional
	public ChatGroups saveChatGroup(int userid) throws FixitException{
		try{
		ChatGroups chatGroups=new ChatGroups();
		User user=userService.findUserById(userid);
		chatGroups.setChatGroupName(user.getUserName());
		chatGroups.setCreatedAt(Calendar.getInstance());
      chatGroups=chatGroupsDao.store(chatGroups);
		return chatGroups;
		}catch(Exception e){
			throw new FixitException();
		}
	}
	@Override
	@Transactional
	public Set<UserGroupsList> getAllChatGroups(int startIndex,int maxRows) throws FixitException{
		Set<ChatGroups> chatGroups=new LinkedHashSet<ChatGroups>();
		Set<UserGroupsList> userGroupList=new LinkedHashSet<UserGroupsList>();
		try{
			chatGroups=chatGroupsDao.getAllChatGroups(startIndex, maxRows);
			Iterator<ChatGroups> iterator=chatGroups.iterator();
			while(iterator.hasNext()){
				ChatGroups chatGroup=iterator.next();
				UserGroupsList userGroups=new UserGroupsList();
				userGroups.setUserId(chatGroup.getChatGroupId());
				userGroups.setUserName(chatGroup.getChatGroupName());
				userGroupList.add(userGroups);
			}
			return userGroupList;
		}catch(Exception e){
			throw new FixitException();
		}
	}
	
	@Override
	@Transactional
	public ChatGroups findChatGroupBasedOnGroupId(Integer groupId) throws FixitException{
		ChatGroups chatGroups=null;
		try{
			chatGroups=chatGroupsDao.findChatGroupBasedOnGroupId(groupId);

		return  chatGroups;
		}catch(Exception e){
			throw new FixitException();
		}
	}
	@Override
	public Set<UserGroupsList> FindChatGroupNameByAdminId(int adminId) throws FixitException {
		List<Object> objects = chatGroupsDao.FindChatGroupNameByAdminId(adminId);
		Set<UserGroupsList> userGroupsList = new LinkedHashSet<UserGroupsList>();
		for (Object o : objects) {
			Object[] cols = (Object[]) o;
			int chatGroupId = (int)cols[0];
			String chatGroupName =  (String)cols[1];
			UserGroupsList userGroups=new UserGroupsList();
			userGroups.setUserId(chatGroupId);
			userGroups.setUserName(chatGroupName);
			userGroupsList.add(userGroups);
		}
		return userGroupsList;
	}
}
