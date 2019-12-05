package com.fixit.service;

import java.util.Set;

import com.fixit.domain.bo.ChatMessageBo;
import com.fixit.domain.vo.ChatGroups;
import com.fixit.domain.vo.ChatMessage;
import com.fixit.domain.vo.MessageNotification;
import com.fixit.domain.vo.User;
import com.fixit.utility.FixitException;

public interface MessageNotificationService {

	public MessageNotification saveMessageNotification(ChatMessage message,int receiver,boolean readStatus) throws FixitException;
	public Set<MessageNotification> updateMessageNotification(int userId) throws FixitException;
	public long getAllNotificationBasedOnId(int userId) throws FixitException;
}
