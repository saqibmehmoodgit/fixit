package com.fixit.dao;

import java.util.Set;

import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import com.fixit.domain.vo.ChatMessage;
import com.fixit.utility.FixitException;


public interface ChatMessageDao  extends JpaDao<ChatMessage>  {

	public Set<ChatMessage> getAllChatMessagesBasedOnGroupId(Integer chatGroupId) throws DataAccessException;
}
