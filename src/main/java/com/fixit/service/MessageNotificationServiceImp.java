package com.fixit.service;

import java.util.Calendar;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.dao.ChatMessageDao;
import com.fixit.dao.MessageNotificationDao;
import com.fixit.domain.vo.ChatMessage;
import com.fixit.domain.vo.MessageNotification;
import com.fixit.domain.vo.User;
import com.fixit.utility.FixitException;

@Service("MessageNotificationService")
@Transactional
public class MessageNotificationServiceImp implements MessageNotificationService {

	@Autowired
	private MessageNotificationDao chatMessageDao;
	@Override
	public MessageNotification saveMessageNotification(ChatMessage message, int receiver, boolean readStatus)
			throws FixitException {
		MessageNotification messageNot=new MessageNotification();
		try{
		
			messageNot.setReceiverId(receiver);
			messageNot.setReadStatus(readStatus);
			messageNot.setMessage(message);
			messageNot.setCreatedAt(Calendar.getInstance());
			messageNot=chatMessageDao.store(messageNot);
		return messageNot;
		}catch(Exception e){
		throw new FixitException();	
		}
	}
	@Override
	public Set<MessageNotification> updateMessageNotification(int userId) throws FixitException {
		
		Set<MessageNotification> listUpdatedNotifications = chatMessageDao.updateMessageStatus(userId);
		return listUpdatedNotifications;
	}
	
	@Override
	public long getAllNotificationBasedOnId(int receiverId) throws FixitException {
		
		long messageCount = chatMessageDao.getAllNotificationBasedOnId(receiverId);
		return messageCount;
	}

}
