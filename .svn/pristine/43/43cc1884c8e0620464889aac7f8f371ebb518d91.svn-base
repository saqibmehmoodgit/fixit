package com.fixit.dao;

import java.util.Set;

import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import com.fixit.domain.vo.ChatMessage;
import com.fixit.domain.vo.MessageNotification;

public interface MessageNotificationDao extends JpaDao<MessageNotification> {
	public long getAllNotificationBasedOnId(Integer receiverId) throws DataAccessException;

	public Set<MessageNotification> updateMessageStatus(int userId);

}
		