package com.fixit.service;

import java.util.Set;

import com.fixit.domain.bo.ChatMessageBo;
import com.fixit.domain.vo.ChatGroups;
import com.fixit.domain.vo.ChatMessage;
import com.fixit.domain.vo.User;
import com.fixit.utility.FixitException;

public interface ChatMessageService {

	public Set<ChatMessageBo> getAllChatMessagesBasedOnGroupId(Integer chatGroupId,User user) throws FixitException;
	
	public ChatMessage saveChatMessage(String message,User chatFrom,ChatGroups chatGroups) throws FixitException;
}
