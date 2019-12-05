package com.fixit.utility;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import com.cribbstechnologies.clients.mandrill.exception.RequestFailedException;
import com.cribbstechnologies.clients.mandrill.model.MandrillTemplatedMessageRequest;
import com.cribbstechnologies.clients.mandrill.model.response.message.MessageResponse;
import com.cribbstechnologies.clients.mandrill.model.response.message.SendMessageResponse;
import com.cribbstechnologies.clients.mandrill.request.MandrillMessagesRequest;
import com.cribbstechnologies.clients.mandrill.request.MandrillRESTRequest;
import com.cribbstechnologies.clients.mandrill.util.MandrillConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmailUtility {

	private static final Logger myLogger = LoggerFactory.getLogger(EmailUtility.class);
	private static MandrillRESTRequest request = new MandrillRESTRequest();
	private static MandrillConfiguration config = new MandrillConfiguration();
	private static MandrillMessagesRequest messagesRequest = new MandrillMessagesRequest();
	private static HttpClient client;
	private static Properties props = new Properties();
	private static ObjectMapper mapper = new ObjectMapper();

	@BeforeClass
	public static void beforeClass() {
		try {
			props.load(EmailUtility.class.getClassLoader().getResourceAsStream("mandrill.properties"));
		} catch (IOException e) {
			// fail ("properties file not loaded");
		}
		config.setApiKey(props.getProperty("apiKey"));
		config.setApiVersion("1.0");
		config.setBaseURL("https://mandrillapp.com/api");
		request.setConfig(config);
		request.setObjectMapper(mapper);
		messagesRequest.setRequest(request);
	}

	@Before
	public void before() {
		client = new DefaultHttpClient();
		request.setHttpClient(client);
	}

	// sending Email Using Mandrill Api

	@Async
	public void sendEmail(MandrillTemplatedMessageRequest requests) {

		try {
			beforeClass();
			before();
			SendMessageResponse response = messagesRequest.sendTemplatedMessage(requests);

			List<MessageResponse> responseList = response.getList();
			for (Iterator<MessageResponse> iterator = responseList.iterator(); iterator.hasNext();) {
				MessageResponse messageResponse = (MessageResponse) iterator.next();
				
			}
			myLogger.info("email sent success");
		} catch (RequestFailedException e) {
			System.err.println("send email RequestFailed Exception");
			myLogger.error("send email RequestFaile Exception", e);
		} catch (Exception e) {
			System.err.println("emailTemplateSend exception");
			myLogger.error("emailTemplateSend exception", e);
		}

	}
}
