package com.fixit.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cribbstechnologies.clients.mandrill.model.MandrillAttachment;
import com.cribbstechnologies.clients.mandrill.model.MandrillMessage;
import com.cribbstechnologies.clients.mandrill.model.MandrillRecipient;
import com.cribbstechnologies.clients.mandrill.model.MandrillTemplatedMessageRequest;
import com.cribbstechnologies.clients.mandrill.model.MergeVar;
import com.cribbstechnologies.clients.mandrill.model.MessageMergeVars;
import com.fixit.dao.QueryFilesDao;
import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.QueryAppliedFixers;
import com.fixit.domain.vo.QueryAudit;
import com.fixit.domain.vo.QueryExpire;
import com.fixit.domain.vo.QueryFiles;
import com.fixit.domain.vo.User;
import com.fixit.domain.vo.Verification;
import com.fixit.utility.DbConstants;
import com.fixit.utility.EmailUtility;
import com.fixit.utility.FixitException;
import com.fixit.utility.FixitVariables;
import com.fixit.utility.Utility;

@Service("EmailServcie")
@Transactional
public class EmailServiceImpl implements EmailServcie {

	@Autowired
	private EmailUtility emailUtility;

	@Autowired
	private UserService userService;

	@Autowired
	private QueryService queryService;

	@Autowired
	private QueryAuditService queryAuditService;

	@Autowired
	private QueryFilesDao queryFilesDao;
	
	

	private MandrillMessage getRequestBody(String fromName, String fromEmail, MandrillRecipient[] recipients,
			String subject, List<MergeVar> globalMergeVar, List<MessageMergeVars> recepientMergeVar,
			List<MandrillAttachment> attachments) {
		MandrillMessage body = new MandrillMessage();
		Map<String, String> headers = new HashMap<String, String>();
		body.setHeaders(headers);
		body.setFrom_email(fromEmail);
		body.setFrom_name(fromName);

		body.setTrack_clicks(true);
		body.setTrack_opens(true);

		body.setTo(recipients);

		body.setSubject(subject);

		if (null != globalMergeVar && globalMergeVar.size() > 0) {
			body.setGlobal_merge_vars(globalMergeVar);
		}
		if (null != recepientMergeVar && recepientMergeVar.size() > 0) {
			body.setMerge_vars(recepientMergeVar);
		}
		if (null != attachments && attachments.size() > 0) {
			body.setAttachments(attachments);
		}

		return body;
	}

	private List<MergeVar> createGlobalMergeVar(Map<String, String> globalMergeVarMap) {
		List<MergeVar> mergeVar = new ArrayList<MergeVar>();
		Set<String> keySet = globalMergeVarMap.keySet();
		for (String key : keySet) {
			mergeVar.add(new MergeVar(key, globalMergeVarMap.get(key)));
		}
		return mergeVar;
	}

	@Override
	public void emailToFixerForProfileActivation(Set<User> fixers, String baseUrl) throws FixitException {

		MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
		int recipientCount = 0;
		List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
		Map<String, String> receiptentParams = new HashMap<String, String>();
		MessageMergeVars messageMergeVars;
		MandrillRecipient[] recipients = new MandrillRecipient[fixers.size()];
		Map<String, String> globalMergeVarMap = new HashMap<String, String>();
		List<MergeVar> receiptentMergeVar = new ArrayList<MergeVar>();
		
		

		for (User user : fixers) {
			if (!DbConstants.EMAIL_ALERT.equals(user.getEmailAlert())) {
				continue;
			}
			

			if(FixitVariables.TEST)
			{
				recipients[recipientCount++] = new MandrillRecipient("ERP Fixer",
						FixitVariables.FIXER_EMAIL);
			}
			else
			{
				recipients[recipientCount++] = new MandrillRecipient(user.getFirstName() + " " + user.getLastName(),
					user.getEmail());
			}
			messageMergeVars = new MessageMergeVars();
			receiptentParams.put("userName", user.getFirstName());

			receiptentMergeVar = createGlobalMergeVar(receiptentParams);
			messageMergeVars.setRcpt(user.getEmail());
			messageMergeVars.setVars(receiptentMergeVar);
			receiptentMessageVars.add(messageMergeVars);

		}
		if (recipients.length > 0) {
			String subject = "Congratulations. You are now a Fixer!";
			String fromName = FixitVariables.ADMIN_USERNAME_FROM;
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
			request.setTemplate_name("fixer-approve");
			emailUtility.sendEmail(request);
		}
	}

	@Override
	public void emailToFixerForProfileRejection(Set<User> fixers) throws FixitException {

		MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
		int recipientCount = 0;
		List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
		Map<String, String> receiptentParams = new HashMap<String, String>();
		MessageMergeVars messageMergeVars;
		MandrillRecipient[] recipients = new MandrillRecipient[fixers.size()];
		Map<String, String> globalMergeVarMap = new HashMap<String, String>();
		List<MergeVar> receiptentMergeVar = new ArrayList<MergeVar>();
		
		
		

		for (User user : fixers) {
			if (!DbConstants.EMAIL_ALERT.equals(user.getEmailAlert())) {
				continue;
			}
			if(FixitVariables.TEST)
			{
				recipients[recipientCount++] = new MandrillRecipient("ERP Fixer",
						FixitVariables.FIXER_EMAIL);
			}else
			{
			recipients[recipientCount++] = new MandrillRecipient(user.getFirstName() + " " + user.getLastName(),
					user.getEmail());
			}
			messageMergeVars = new MessageMergeVars();
			receiptentParams.put("userName", user.getFirstName());

			receiptentMergeVar = createGlobalMergeVar(receiptentParams);
			messageMergeVars.setRcpt(user.getEmail());
			messageMergeVars.setVars(receiptentMergeVar);
			receiptentMessageVars.add(messageMergeVars);

		}
		if (recipients.length > 0) {
			String subject = "Your ERPfixers Application has been Rejected!";
			String fromName = FixitVariables.ADMIN_USERNAME_FROM;
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
			request.setTemplate_name("fixer-signup-reject");
			emailUtility.sendEmail(request);
		}
	}

	@Override
	public void emailToFixerWhoMatchQueryCatAndQueryExpire(Set<User> users, Query query,
			Set<QueryExpire> queryExpireDao, String baseUrl, List<String> fileList) throws FixitException {
		MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
		int recipientCount = 0;
		File file = null;
		List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
		Map<String, String> receiptentParams = new HashMap<String, String>();
		MessageMergeVars messageMergeVars;
		MandrillRecipient[] recipients = new MandrillRecipient[users.size()];
		Map<String, String> globalMergeVarMap = new HashMap<String, String>();
		List<MergeVar> receiptentMergeVar = new ArrayList<MergeVar>();
		
		

		for (User user : users) {
			if (!DbConstants.EMAIL_ALERT.equals(user.getEmailAlert())) {
				continue;
			}
			if(FixitVariables.TEST)
			{
				recipients[recipientCount++] = new MandrillRecipient("ERP Fixer",
						FixitVariables.FIXER_EMAIL);
			}else
			{
			recipients[recipientCount++] = new MandrillRecipient(user.getFirstName() + " " + user.getLastName(),
					user.getEmail());
			}
			messageMergeVars = new MessageMergeVars();
			receiptentParams.put("userName", user.getFirstName());
			if (queryExpireDao != null) {
				for (QueryExpire queryExpire : queryExpireDao) {
					if (queryExpire.getQueryId().equals(query.getQueryId())
							&& queryExpire.getFixerId().equals(user.getUserId())) {
						
						receiptentParams.put("queryLink", baseUrl + "email/newRequest?emailToFixerCode=" + queryExpire.getHashcode());
						receiptentParams.put("queryTitle", query.getQueryTitle());
						receiptentParams.put("queryContent", query.getQueryContent().replaceAll("\\n", "<br>"));
					}
				}
			}
			receiptentMergeVar = createGlobalMergeVar(receiptentParams);
			messageMergeVars.setRcpt(user.getEmail());
			messageMergeVars.setVars(receiptentMergeVar);
			receiptentMessageVars.add(messageMergeVars);

		}
		if (recipients.length > 0) {
			String subject = "New Request " + query.getQueryTitle() + " has been posted";
			String fromName = FixitVariables.ADMIN_USERNAME_FROM;
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;

			List<MandrillAttachment> attachments = new ArrayList<MandrillAttachment>();
			MandrillAttachment attach = null;
			if (null != fileList) {
				int lenght = fileList.size();
				for (int i = 0; i < lenght; i++) {
					byte[] byteArr = null;
					byte[] encoded;
					try {
						file = new File(fileList.get(i));

						byteArr = Utility.loadFile(fileList.get(i));

						encoded = Base64.encodeBase64(byteArr);
						String encodedContent = new String(encoded);
						MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
						String mimeType = fileTypeMap.getContentType(file);
						attach = new MandrillAttachment(mimeType, file.getName(), encodedContent);
						attachments.add(attach);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, attachments));
			request.setTemplate_name("new-request-posted-member");
			emailUtility.sendEmail(request);
		}
	}

	@Override
	@Transactional
	public void emailToFixerForNotFixed(User user, Query query, Set<QueryExpire> queryExpireDao, String baseUrl)
			throws FixitException {
		if (DbConstants.EMAIL_ALERT.equals(user.getEmailAlert())) {
			MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
			List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
			Map<String, String> receiptentParams = new HashMap<String, String>();
			MessageMergeVars messageMergeVars;
			MandrillRecipient[] recipients = new MandrillRecipient[1];
			Map<String, String> globalMergeVarMap = new HashMap<String, String>();
			List<MergeVar> receiptentMergeVar = new ArrayList<MergeVar>();

			if(FixitVariables.TEST)
			{
				recipients[0] = new MandrillRecipient("ERP Fixer",
						FixitVariables.FIXER_EMAIL);
			}
			else
			{
			recipients[0] = new MandrillRecipient(user.getFirstName() + " " + user.getLastName(), user.getEmail());
			}
			messageMergeVars = new MessageMergeVars();
			receiptentParams.put("userName", user.getFirstName());
			for (QueryExpire queryExpire : queryExpireDao) {
				if (queryExpire.getQueryId().equals(query.getQueryId())
						&& queryExpire.getFixerId().equals(user.getUserId())) {
					receiptentParams.put("queryLink",
							baseUrl + "email/notFixed?notFixedQuery=" + queryExpire.getHashcode());
				}
			}

			receiptentMergeVar = createGlobalMergeVar(receiptentParams);
			messageMergeVars.setRcpt(user.getEmail());
			messageMergeVars.setVars(receiptentMergeVar);
			receiptentMessageVars.add(messageMergeVars);

			String subject = "Request " + query.getQueryTitle() + " has been marked as “Not Fixed”";
			String fromName = FixitVariables.ADMIN_USERNAME_FROM;
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
			request.setTemplate_name("request-not-fixed");
			emailUtility.sendEmail(request);
		}
	}

	@Override
	@Transactional
	public void emailToMemberIssueAccepted(User member, Query query) throws FixitException {
		if (DbConstants.EMAIL_ALERT.equals(member.getEmailAlert())) {
			MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
			List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
			Map<String, String> receiptentParams = new HashMap<String, String>();
			MessageMergeVars messageMergeVars;
			MandrillRecipient[] recipients = new MandrillRecipient[1];
			Map<String, String> globalMergeVarMap = new HashMap<String, String>();
			List<MergeVar> receiptentMergeVar = new ArrayList<MergeVar>();

			if(FixitVariables.TEST)
			{
				recipients[0] = new MandrillRecipient("ERP Member",
						FixitVariables.MEMBER_EMAIL);
			}
			else
			{
			recipients[0] = new MandrillRecipient(member.getFirstName() + " " + member.getLastName(),
					member.getEmail());
			}
			messageMergeVars = new MessageMergeVars();
			receiptentParams.put("userName", member.getFirstName());
			receiptentMergeVar = createGlobalMergeVar(receiptentParams);
			messageMergeVars.setRcpt(member.getEmail());
			messageMergeVars.setVars(receiptentMergeVar);
			receiptentMessageVars.add(messageMergeVars);

			String subject = "Your request " + query.getQueryTitle() + " has been claimed";
			String fromName = FixitVariables.ADMIN_USERNAME_FROM;
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
			request.setTemplate_name("fixer-claimed-issue");
			emailUtility.sendEmail(request);
		}

	}

	@Override
	@Transactional
	public void emailToFixerRequestRejected(User fixer, Query query) throws FixitException {
		if (DbConstants.EMAIL_ALERT.equals(fixer.getEmailAlert())) {
			MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
			List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
			Map<String, String> receiptentParams = new HashMap<String, String>();
			MessageMergeVars messageMergeVars;
			MandrillRecipient[] recipients = new MandrillRecipient[1];
			Map<String, String> globalMergeVarMap = new HashMap<String, String>();
			List<MergeVar> receiptentMergeVar = new ArrayList<MergeVar>();

			if(FixitVariables.TEST)
			{
				recipients[0] = new MandrillRecipient("ERP Fixer",
						FixitVariables.FIXER_EMAIL);
			}
			else
			{
			recipients[0] = new MandrillRecipient(fixer.getFirstName() + " " + fixer.getLastName(), fixer.getEmail());
			}
			messageMergeVars = new MessageMergeVars();
			receiptentParams.put("userName", fixer.getFirstName());
			receiptentMergeVar = createGlobalMergeVar(receiptentParams);
			messageMergeVars.setRcpt(fixer.getEmail());
			messageMergeVars.setVars(receiptentMergeVar);
			receiptentMessageVars.add(messageMergeVars);

			String subject = "Request " + query.getQueryTitle() + " has been reassigned";
			String fromName = FixitVariables.ADMIN_USERNAME_FROM;
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
			request.setTemplate_name("fixer-rejected");
			emailUtility.sendEmail(request);
		}

	}

	@Override
	@Transactional
	public void emailToFixerForNotReplyingAfterNotFixed(User fixer, Query query) throws FixitException {
		if (DbConstants.EMAIL_ALERT.equals(fixer.getEmailAlert())) {
			MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
			List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
			Map<String, String> receiptentParams = new HashMap<String, String>();
			MessageMergeVars messageMergeVars;
			MandrillRecipient[] recipients = new MandrillRecipient[1];
			Map<String, String> globalMergeVarMap = new HashMap<String, String>();
			List<MergeVar> receiptentMergeVar = new ArrayList<MergeVar>();
			if(FixitVariables.TEST)
			{
				recipients[0] = new MandrillRecipient("ERP Fixer",
						FixitVariables.FIXER_EMAIL);
			}
			else
			{
			recipients[0] = new MandrillRecipient(fixer.getFirstName() + " " + fixer.getLastName(), fixer.getEmail());
			}
			messageMergeVars = new MessageMergeVars();
			receiptentParams.put("userName", fixer.getFirstName());
			receiptentMergeVar = createGlobalMergeVar(receiptentParams);
			messageMergeVars.setRcpt(fixer.getEmail());
			messageMergeVars.setVars(receiptentMergeVar);
			receiptentMessageVars.add(messageMergeVars);

			String subject = "Request " + query.getQueryTitle() + " has been reassigned";
			String fromName = FixitVariables.ADMIN_USERNAME_FROM;
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
			request.setTemplate_name("fixer_notresponded_tomember_notfixed_request");
			emailUtility.sendEmail(request);
		}

	}

	@Override
	@Transactional
	public void emailToFixerRequestFixed(User fixer, Query query) throws FixitException {
		if (DbConstants.EMAIL_ALERT.equals(fixer.getEmailAlert())) {
			MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
			List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
			Map<String, String> receiptentParams = new HashMap<String, String>();
			MessageMergeVars messageMergeVars;
			MandrillRecipient[] recipients = new MandrillRecipient[1];
			Map<String, String> globalMergeVarMap = new HashMap<String, String>();
			List<MergeVar> receiptentMergeVar = new ArrayList<MergeVar>();
			if(FixitVariables.TEST)
			{
				recipients[0] = new MandrillRecipient("ERP Fixer",
						FixitVariables.FIXER_EMAIL);
			}
			else
			{
			recipients[0] = new MandrillRecipient(fixer.getFirstName() + " " + fixer.getLastName(), fixer.getEmail());
			}
			messageMergeVars = new MessageMergeVars();
			receiptentParams.put("userName", fixer.getFirstName());
			receiptentMergeVar = createGlobalMergeVar(receiptentParams);
			messageMergeVars.setRcpt(fixer.getEmail());
			messageMergeVars.setVars(receiptentMergeVar);
			receiptentMessageVars.add(messageMergeVars);

			String subject = "Request " + query.getQueryTitle() + " has been marked as “Fixed”";
			String fromName = FixitVariables.ADMIN_USERNAME_FROM;
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
			request.setTemplate_name("request-fixed");
			emailUtility.sendEmail(request);
		}

	}

	@Override
	@Transactional
	public void emailToFixerRequestRejectedNonResponse(User fixer, Query query) throws FixitException {
		if (DbConstants.EMAIL_ALERT.equals(fixer.getEmailAlert())) {
			MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
			List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
			Map<String, String> receiptentParams = new HashMap<String, String>();
			MessageMergeVars messageMergeVars;
			MandrillRecipient[] recipients = new MandrillRecipient[1];
			Map<String, String> globalMergeVarMap = new HashMap<String, String>();
			List<MergeVar> receiptentMergeVar = new ArrayList<MergeVar>();
			if(FixitVariables.TEST)
			{
				recipients[0] = new MandrillRecipient("ERP Fixer",
						FixitVariables.FIXER_EMAIL);
			}
			else
			{
			recipients[0] = new MandrillRecipient(fixer.getFirstName() + " " + fixer.getLastName(), fixer.getEmail());
			}
			messageMergeVars = new MessageMergeVars();
			receiptentParams.put("userName", fixer.getFirstName());
			receiptentMergeVar = createGlobalMergeVar(receiptentParams);
			messageMergeVars.setRcpt(fixer.getEmail());
			messageMergeVars.setVars(receiptentMergeVar);
			receiptentMessageVars.add(messageMergeVars);

			String subject = "Request " + query.getQueryTitle() + " reassigned due to non-response";
			String fromName = FixitVariables.ADMIN_USERNAME_FROM;
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
			request.setTemplate_name("fixer-rejected-due-non-response");
			emailUtility.sendEmail(request);
		}

	}

	@Override
	@Transactional
	public void emailToFixerDeactivated(User fixer) throws FixitException {
		if (DbConstants.EMAIL_ALERT.equals(fixer.getEmailAlert())) {
			MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
			List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
			Map<String, String> receiptentParams = new HashMap<String, String>();
			MessageMergeVars messageMergeVars;
			MandrillRecipient[] recipients = new MandrillRecipient[1];
			Map<String, String> globalMergeVarMap = new HashMap<String, String>();
			List<MergeVar> receiptentMergeVar = new ArrayList<MergeVar>();
			if(FixitVariables.TEST)
			{
				recipients[0] = new MandrillRecipient("ERP Fixer",
						FixitVariables.FIXER_EMAIL);
			}
			else
			{
			recipients[0] = new MandrillRecipient(fixer.getFirstName() + " " + fixer.getLastName(), fixer.getEmail());
			}
			messageMergeVars = new MessageMergeVars();
			receiptentParams.put("userName", fixer.getFirstName());
			receiptentMergeVar = createGlobalMergeVar(receiptentParams);
			messageMergeVars.setRcpt(fixer.getEmail());
			messageMergeVars.setVars(receiptentMergeVar);
			receiptentMessageVars.add(messageMergeVars);
			String subject = "Profile deactivated due to non-response";
			String fromName = FixitVariables.ADMIN_USERNAME_FROM;
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
			request.setTemplate_name("fixer-deactivated");
			emailUtility.sendEmail(request);
		}

	}

	@Override
	@Transactional
	public void emailToMemberAllFixerNotAcceptedQuery(Query query, Set<QueryExpire> queryExpireDao, String baseUrl)
			throws FixitException {
		if (DbConstants.EMAIL_ALERT.equals(query.getUser().getEmailAlert())) {
			MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
			List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
			Map<String, String> receiptentParams = new HashMap<String, String>();
			MessageMergeVars messageMergeVars;
			MandrillRecipient[] recipients = new MandrillRecipient[1];
			Map<String, String> globalMergeVarMap = new HashMap<String, String>();
			List<MergeVar> receiptentMergeVar = new ArrayList<MergeVar>();
			messageMergeVars = new MessageMergeVars();
			receiptentParams.put("userName", query.getUser().getFirstName());

			for (QueryExpire queryExpire : queryExpireDao) {
				if (queryExpire.getQueryId().equals(query.getQueryId())
						&& queryExpire.getFixerId().equals(query.getUser().getUserId())) {
					receiptentParams.put("queryLink",
							baseUrl + "email/unClaimed?adminUnClaimed=" + queryExpire.getHashcode());
				}
			}

			if(FixitVariables.TEST)
			{
				recipients[0] = new MandrillRecipient("ERP Member",
						FixitVariables.MEMBER_EMAIL);
			}
			else
			{
			recipients[0] = new MandrillRecipient(query.getUser().getFirstName() + " " + query.getUser().getLastName(),
					query.getUser().getEmail());
			}
			receiptentMergeVar = createGlobalMergeVar(receiptentParams);
			messageMergeVars.setRcpt(query.getUser().getEmail());
			messageMergeVars.setVars(receiptentMergeVar);
			receiptentMessageVars.add(messageMergeVars);
			String subject = "Posted Request " + query.getQueryTitle() + " remains unclaimed";
			String fromName = FixitVariables.ADMIN_USERNAME_FROM;
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
			request.setTemplate_name("no-fixer-claimed-issue");
			emailUtility.sendEmail(request);
		}
	}

	@Override
	@Transactional
	public void emailToMemberNotSureCat(Query query, Set<QueryExpire> queryExpires, String baseUrl)
			throws FixitException {
		if (DbConstants.EMAIL_ALERT.equals(query.getUser().getEmailAlert())) {
			MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
			List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
			MandrillRecipient[] recipients = new MandrillRecipient[1];
			Map<String, String> globalMergeVarMap = new HashMap<String, String>();
			globalMergeVarMap.put("userName", query.getUser().getFirstName());

			for (QueryExpire queryExpire : queryExpires) {
				if (queryExpire.getQueryId().equals(query.getQueryId())
						&& queryExpire.getFixerId().equals(query.getUser().getUserId())) {
					globalMergeVarMap.put("queryLink",
							baseUrl + "email/notSure?adminNotSure=" + queryExpire.getHashcode());
				}
			}
			if(FixitVariables.TEST)
			{
				recipients[0] = new MandrillRecipient("ERP Member",
						FixitVariables.MEMBER_EMAIL);
			}
			else
			{
			recipients[0] = new MandrillRecipient(query.getUser().getFirstName() + " " + query.getUser().getLastName(),
					query.getUser().getEmail());
			}
			String subject = "Category of request " + query.getQueryTitle() + " remains unassigned";
			String fromName = FixitVariables.ADMIN_USERNAME_FROM;
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
			request.setTemplate_name("selected-not-sure-category");
			emailUtility.sendEmail(request);
		}
	}

	@Override
	@Transactional
	public void emailToAdminForFixerApproval(User user, String baseUrl) throws FixitException {
		if (!DbConstants.EMAIL_ALERT.equals(user.getEmailAlert())) {
			return;
		}
		MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
		List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
		MandrillRecipient[] recipients = new MandrillRecipient[1];
		Map<String, String> globalMergeVarMap = new HashMap<String, String>();
		globalMergeVarMap.put("userName", FixitVariables.ADMIN_USERNAME_TO);
		globalMergeVarMap.put("queryLink", baseUrl + "email/approveFixer");
		recipients[0] = new MandrillRecipient(FixitVariables.ADMIN_USERNAME_TO, FixitVariables.ADMIN_EMAIL_TO);
		String subject = "New Fixer " + user.getFirstName() + " " + user.getLastName() + "Request To Approve!";
		String fromName = FixitVariables.ADMIN_USERNAME_FROM;
		String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
		request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
				createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
		request.setTemplate_name("fixer-approve-request");
		emailUtility.sendEmail(request);
	}

	@Override
	@Transactional
	public void emailToMembersFixerNotAcceptedQuery(Set<Query> queries, Set<QueryExpire> queryExpireDao, String baseUrl)
			throws FixitException {
		MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
		int recipientCount = 0;
		List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
		Map<String, String> receiptentParams = new HashMap<String, String>();
		MessageMergeVars messageMergeVars;
		MandrillRecipient[] recipients = new MandrillRecipient[queries.size()];
		Map<String, String> globalMergeVarMap = new HashMap<String, String>();
		List<MergeVar> receiptentMergeVar = new ArrayList<MergeVar>();
		
		
	

		for (Query query : queries) {
			if (!DbConstants.EMAIL_ALERT.equals(query.getUser().getEmailAlert())) {
				continue;
			}
			if(FixitVariables.TEST)
			{
				recipients[recipientCount++] = new MandrillRecipient("ERP Member",
						FixitVariables.MEMBER_EMAIL);
			}
			else
			{
			recipients[recipientCount++] = new MandrillRecipient(
					query.getUser().getFirstName() + " " + query.getUser().getLastName(), query.getUser().getEmail());
			}
			messageMergeVars = new MessageMergeVars();
			receiptentParams.put("userName", query.getUser().getFirstName());
			for (QueryExpire queryExpire : queryExpireDao) {
				if (queryExpire.getQueryId().equals(query.getQueryId())
						&& queryExpire.getFixerId().equals(query.getUser().getUserId())) {
					receiptentParams.put("queryLink",
							baseUrl + "email/memberRequestNewFixer?memberRequestNewFixer=" + queryExpire.getHashcode());
				}
			}
			receiptentMergeVar = createGlobalMergeVar(receiptentParams);
			messageMergeVars.setRcpt(query.getUser().getEmail());
			messageMergeVars.setVars(receiptentMergeVar);
			receiptentMessageVars.add(messageMergeVars);

		}
		if (recipients.length > 0) {
			String subject = "Selected Fixer has not responded";
			String fromName = FixitVariables.ADMIN_USERNAME_FROM;
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
			request.setTemplate_name("selected-fixer-not-responded");
			emailUtility.sendEmail(request);
		}

	}

	@Override
	@Transactional
	public void emailToMembersToRespondFixer(Set<Query> queries, Set<QueryExpire> queryExpireDao, String baseUrl)
			throws FixitException {
		MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
		int recipientCount = 0;
		List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
		Map<String, String> receiptentParams = new HashMap<String, String>();
		MessageMergeVars messageMergeVars;
		MandrillRecipient[] recipients = new MandrillRecipient[queries.size()];
		Map<String, String> globalMergeVarMap = new HashMap<String, String>();
		List<MergeVar> receiptentMergeVar = new ArrayList<MergeVar>();

		
		for (Query query : queries) {
			if (DbConstants.EMAIL_ALERT.equals(query.getUser().getEmailAlert())) {
				continue;
			}
			if(FixitVariables.TEST)
			{
				recipients[recipientCount++] = new MandrillRecipient("ERP Member",
						FixitVariables.MEMBER_EMAIL);
			}
			else
			{
			recipients[recipientCount++] = new MandrillRecipient(
					query.getUser().getFirstName() + " " + query.getUser().getLastName(), query.getUser().getEmail());
			}
			messageMergeVars = new MessageMergeVars();
			receiptentParams.put("userName", query.getUser().getFirstName());
			for (QueryExpire queryExpire : queryExpireDao) {
				if (queryExpire.getQueryId().equals(query.getQueryId())
						&& queryExpire.getFixerId().equals(query.getUser().getUserId())) {
					receiptentParams.put("queryLink",
							baseUrl + "email/memberRespond?memberRespond=" + queryExpire.getHashcode());
				}
			}
			receiptentMergeVar = createGlobalMergeVar(receiptentParams);
			messageMergeVars.setRcpt(query.getUser().getEmail());
			messageMergeVars.setVars(receiptentMergeVar);
			receiptentMessageVars.add(messageMergeVars);

		}
		if (recipients.length > 0) {
			String subject = "Have we solved your ERPfixers request?";
			String fromName = FixitVariables.ADMIN_USERNAME_FROM;
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
			request.setTemplate_name("member-not-marked-fixed-notfixed");
			emailUtility.sendEmail(request);
		}

	}

	@Override
	@Transactional
	public void emailToMemberChatMessage(QueryAudit queryAudit, User fixer, String baseUrl) throws FixitException {
		User member = userService.findUserById(queryAudit.getCustomerId());
		if (DbConstants.EMAIL_ALERT.equals(member.getEmailAlert())) {
			MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
			MandrillRecipient[] recipients = new MandrillRecipient[1];
			Map<String, String> globalMergeVarMap = new HashMap<String, String>();

			Query query = queryAudit.getQuery();

			globalMergeVarMap.put("userName", member.getFirstName());
			globalMergeVarMap.put("messageContent", queryAudit.getMessage().replaceAll("\\n", "<br>"));
			globalMergeVarMap.put("queryLink", baseUrl + "email/memberChat?memberChat=" + query.getHashcode()+"&senderId="+fixer.getUserId());
			
			
			if(FixitVariables.TEST)
			{
				recipients[0] = new MandrillRecipient("ERP Member",
						FixitVariables.MEMBER_EMAIL);
			}
			else
			{
			recipients[0] = new MandrillRecipient(member.getFirstName() + " " + member.getLastName(),
					member.getEmail());
			}
			String subject = query.getQueryTitle();
			String fromName = fixer.getFirstName() + " " + fixer.getLastName() + " via ERPfixers";
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			List<MandrillAttachment> attachments = new ArrayList<MandrillAttachment>();

			

			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), null, attachments));
			request.setTemplate_name("message");
			emailUtility.sendEmail(request);
		}
	}

	@Override
	@Transactional
	public void emailToMemberChatAttachMentMessage(QueryAudit queryAudit, User fixer, String baseUrl,
			MultipartFile file) throws FixitException {
		if (DbConstants.EMAIL_ALERT.equals(fixer.getEmailAlert())) {
			MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
			MandrillRecipient[] recipients = new MandrillRecipient[1];
			Map<String, String> globalMergeVarMap = new HashMap<String, String>();

			User member = userService.findUserById(queryAudit.getCustomerId());
			Query query = queryAudit.getQuery();

			globalMergeVarMap.put("userName", member.getFirstName());
			globalMergeVarMap.put("messageContent", "Hi, Please find the attached document.");
			globalMergeVarMap.put("queryLink", baseUrl + "email/memberChat?memberChat=" + query.getHashcode());
			
			if(FixitVariables.TEST)
			{
				recipients[0] = new MandrillRecipient("ERP Member",
						FixitVariables.MEMBER_EMAIL);
			}
			else
			{
			recipients[0] = new MandrillRecipient(member.getFirstName() + " " + member.getLastName(),
					member.getEmail());
			}
			String subject = query.getQueryTitle();
			String fromName = fixer.getFirstName() + " " + fixer.getLastName() + " via ERPfixers";
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			List<MandrillAttachment> attachments = new ArrayList<MandrillAttachment>();
			MandrillAttachment attach = null;
			byte[] byteArr = null;
			byte[] encoded;
			try {
				byteArr = file.getBytes();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			encoded = Base64.encodeBase64(byteArr);
			String encodedContent = new String(encoded);

			attach = new MandrillAttachment(file.getContentType(), file.getOriginalFilename(), encodedContent);
			attachments.add(attach);
			

			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), null, attachments));
			request.setTemplate_name("message");
			emailUtility.sendEmail(request);
		}
	}

	@Override
	@Transactional
	public void emailToFixerChatMessage(QueryAudit queryAudit, User member, String baseUrl) throws FixitException {
		User fixer = userService.findUserById(queryAudit.getFixerId());

		if (DbConstants.EMAIL_ALERT.equals(fixer.getEmailAlert())) {
			MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
			MandrillRecipient[] recipients = new MandrillRecipient[1];
			Map<String, String> globalMergeVarMap = new HashMap<String, String>();

			Query query = queryAudit.getQuery();

			globalMergeVarMap.put("userName", fixer.getFirstName());
			globalMergeVarMap.put("messageContent", queryAudit.getMessage().replaceAll("\\n", "<br>"));
			globalMergeVarMap.put("queryLink", baseUrl + "email/fixerChat?fixerChat=" + query.getHashcode()+"&senderId="+member.getUserId());
			
			if(FixitVariables.TEST)
			{
				recipients[0] = new MandrillRecipient("ERP Fixer",
						FixitVariables.FIXER_EMAIL);
			}
			else
			{
				recipients[0] = new MandrillRecipient(fixer.getFirstName() + " " + fixer.getLastName(), fixer.getEmail());
			}
			String subject = query.getQueryTitle();
			String fromName = member.getFirstName() + " " + member.getLastName() + " via ERPfixers";
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			List<MandrillAttachment> attachments = new ArrayList<MandrillAttachment>();
			

			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), null, attachments));
			request.setTemplate_name("message");
			emailUtility.sendEmail(request);
		}
	}

	@Override
	@Transactional
	public void emailToFixerChatAttachmentMessage(QueryAudit queryAudit, User member, String baseUrl,
			MultipartFile file) throws FixitException {
		if (DbConstants.EMAIL_ALERT.equals(member.getEmailAlert())) {
			MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
			MandrillRecipient[] recipients = new MandrillRecipient[1];
			Map<String, String> globalMergeVarMap = new HashMap<String, String>();

			User fixer = userService.findUserById(queryAudit.getFixerId());
			Query query = queryAudit.getQuery();

			globalMergeVarMap.put("userName", fixer.getFirstName());
			globalMergeVarMap.put("messageContent", "Hi, Please find the attached document.");
			globalMergeVarMap.put("queryLink", baseUrl + "email/fixerChat?fixerChat=" + query.getHashcode());
			
			if(FixitVariables.TEST)
			{
				recipients[0] = new MandrillRecipient("ERP Fixer",
						FixitVariables.FIXER_EMAIL);
			}else
			{
			recipients[0] = new MandrillRecipient(fixer.getFirstName() + " " + fixer.getLastName(), fixer.getEmail());
			}
			String subject = query.getQueryTitle();
			String fromName = member.getFirstName() + " " + member.getLastName() + " via ERPfixers";
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			List<MandrillAttachment> attachments = new ArrayList<MandrillAttachment>();
			MandrillAttachment attach = null;
			byte[] byteArr = null;
			byte[] encoded;
			try {
				byteArr = file.getBytes();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			encoded = Base64.encodeBase64(byteArr);
			String encodedContent = new String(encoded);

			attach = new MandrillAttachment(file.getContentType(), file.getOriginalFilename(), encodedContent);
			attachments.add(attach);
			

			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), null, attachments));
			request.setTemplate_name("message");
			emailUtility.sendEmail(request);
		}
	}

	private static byte[] loadFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}
		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		if (offset < bytes.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}

		is.close();
		return bytes;
	}

	@Override
	@Transactional
	public void resetpasswordmail(User user, Verification verification, String baseUrl) throws FixitException {
		MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
		List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
		MandrillRecipient[] recipients = new MandrillRecipient[1];
		Map<String, String> globalMergeVarMap = new HashMap<String, String>();
		globalMergeVarMap.put("userName", user.getFirstName());
		globalMergeVarMap.put("resetPasswordLink", baseUrl + "/resetpassword?hashCode=" + verification.getHashCode());
		
		if(FixitVariables.TEST)
		{
			
			if(user.getUserType().equals("C") )
			{
				recipients[0] = new MandrillRecipient("ERP Member",
						FixitVariables.MEMBER_EMAIL);
			}
			else
			{
				recipients[0] = new MandrillRecipient("ERP Fixer",
						FixitVariables.FIXER_EMAIL);
			}
		}
		else
		{
		recipients[0] = new MandrillRecipient(user.getFirstName() + " " + user.getLastName(), user.getEmail());
		}
		String subject = "Reset Your Password!";
		String fromName = FixitVariables.ADMIN_USERNAME_FROM;
		String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
		request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
				createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
		request.setTemplate_name("reset-password");
		emailUtility.sendEmail(request);

	}

	@Override
	@Transactional
	public void emailToAllUsers(Set<User> users, String message, String subject, String baseUrl, String userType)
			throws FixitException {
		MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
		int recipientCount = 0;
		List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
		Map<String, String> receiptentParams = new HashMap<String, String>();
		MessageMergeVars messageMergeVars;
		MandrillRecipient[] recipients = new MandrillRecipient[users.size()];
		Map<String, String> globalMergeVarMap = new HashMap<String, String>();
		List<MergeVar> receiptentMergeVar = new ArrayList<MergeVar>();

		
		
		
		for (User user : users) {
			
			if(FixitVariables.TEST)
			{
				
				if(userType.equals("C") )
				{
					recipients[recipientCount++] = new MandrillRecipient("ERP Member",
							FixitVariables.MEMBER_EMAIL);
				}
				else
				{
					recipients[recipientCount++] = new MandrillRecipient("ERP Fixer",
							FixitVariables.FIXER_EMAIL);
				}
			}
			else
			{
			recipients[recipientCount++] = new MandrillRecipient(user.getFirstName() + " " + user.getLastName(),
					user.getEmail());
			}
			messageMergeVars = new MessageMergeVars();
			receiptentParams.put("userName", user.getFirstName());
			receiptentParams.put("messageContent", message.replaceAll("\\n", "<br>"));
			receiptentParams.put("subject", subject);
			receiptentMergeVar = createGlobalMergeVar(receiptentParams);
			messageMergeVars.setRcpt(user.getEmail());
			messageMergeVars.setVars(receiptentMergeVar);
			receiptentMessageVars.add(messageMergeVars);

		}
		if (recipients.length > 0) {
			String fromName = FixitVariables.ADMIN_USERNAME_FROM;
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
			if (userType.equals("C"))
			{
				request.setTemplate_name("mail-to-all-members");
			}
			else
			{
				request.setTemplate_name("mail-to-all-fixers");
			}
			emailUtility.sendEmail(request);
		}
	}

	@Override
	@Transactional
	public void emailToMemberFixerApplyRequest(String base, Query query, User member, User fixer)
			throws FixitException {
		if (DbConstants.EMAIL_ALERT.equals(member.getEmailAlert())) {
			MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
			List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
			MandrillRecipient[] recipients = new MandrillRecipient[1];
			Map<String, String> globalMergeVarMap = new HashMap<String, String>();
			globalMergeVarMap.put("userName", member.getFirstName());
			globalMergeVarMap.put("fixerName", fixer.getFirstName() + " " + fixer.getLastName());
			globalMergeVarMap.put("queryLink", base + "email/fixerApply?fixerApplyCode=" + query.getHashcode());
			if(FixitVariables.TEST )
			{
				recipients[0] = new MandrillRecipient("ERP Member",
						FixitVariables.MEMBER_EMAIL);
			}
			else
			{
			recipients[0] = new MandrillRecipient(member.getFirstName() + " " + member.getLastName(),
					member.getEmail());
			}
			String subject = "A Fixer Has Applied For Your Request: " + query.getQueryTitle();
			String fromName = FixitVariables.ADMIN_USERNAME_FROM;
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
			request.setTemplate_name("fixer-apply-request");
			emailUtility.sendEmail(request);
		}
	}

	@Override
	@Transactional
	public void emailToFixerMemberApproveRequest(String base, Query query, User fixer) throws FixitException {
		if (DbConstants.EMAIL_ALERT.equals(fixer.getEmailAlert())) {
			MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
			List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
			MandrillRecipient[] recipients = new MandrillRecipient[1];
			Map<String, String> globalMergeVarMap = new HashMap<String, String>();
			globalMergeVarMap.put("userName", fixer.getFirstName());
			
			if(FixitVariables.TEST )
			{
				recipients[0] = new MandrillRecipient("ERP Fixer",
						FixitVariables.FIXER_EMAIL);
			}
			else
			{
				recipients[0] = new MandrillRecipient(fixer.getFirstName() + " " + fixer.getLastName(), fixer.getEmail());
			}
			String subject = "You’ve been Selected For  Member Request: " + query.getQueryTitle();
			String fromName = FixitVariables.ADMIN_USERNAME_FROM;
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
			request.setTemplate_name("member-approve-request");
			emailUtility.sendEmail(request);
		}
	}

	@Override
	@Transactional
	public void emailToFixersMemberDisapproveRequest(String base, Query query, int fixerId) throws FixitException {
		MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
		int recipientCount = 0;
		List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
		Map<String, String> receiptentParams = new HashMap<String, String>();
		MessageMergeVars messageMergeVars;
		Set<QueryAppliedFixers> fixersList = query.getQueryAppliedFixers();
		MandrillRecipient[] recipients = new MandrillRecipient[fixersList.size() - 1];
		Map<String, String> globalMergeVarMap = new HashMap<String, String>();
		List<MergeVar> receiptentMergeVar = new ArrayList<MergeVar>();
		
		for (QueryAppliedFixers fixer : fixersList) {
			if (fixer.getUserFixer().getUserId() != fixerId) {
				if (!DbConstants.EMAIL_ALERT.equals(fixer.getUserFixer().getEmailAlert())) {
					continue;
				}
				if(FixitVariables.TEST )
				{
					recipients[recipientCount++] = new MandrillRecipient("ERP Fixer",
							FixitVariables.FIXER_EMAIL);
				}
				else
				{
				recipients[recipientCount++] = new MandrillRecipient(
						fixer.getUserFixer().getFirstName() + " " + fixer.getUserFixer().getLastName(),
						fixer.getUserFixer().getEmail());
				}
				messageMergeVars = new MessageMergeVars();
				receiptentParams.put("userName", fixer.getUserFixer().getFirstName());
				receiptentMergeVar = createGlobalMergeVar(receiptentParams);
				messageMergeVars.setRcpt(fixer.getUserFixer().getEmail());
				messageMergeVars.setVars(receiptentMergeVar);
				receiptentMessageVars.add(messageMergeVars);
			}

		}

		String subject = "Update on Member Request: " + query.getQueryTitle();
		String fromName = FixitVariables.ADMIN_USERNAME_FROM;
		String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;

		request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
				createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
		request.setTemplate_name("member-disapprove-request");
		emailUtility.sendEmail(request);
	}

	@Override
	@Transactional
	public void emailToFixerAlertRequestDeadline(String base, Query query, User fixer) throws FixitException {
		if (DbConstants.EMAIL_ALERT.equals(fixer.getEmailAlert())) {
			MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
			List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
			MandrillRecipient[] recipients = new MandrillRecipient[1];
			Map<String, String> globalMergeVarMap = new HashMap<String, String>();
			globalMergeVarMap.put("userName", fixer.getFirstName());
			if(FixitVariables.TEST )
			{
				recipients[0] = new MandrillRecipient("ERP Fixer",
						FixitVariables.FIXER_EMAIL);
			}
			else
			{
			recipients[0] = new MandrillRecipient(fixer.getFirstName() + " " + fixer.getLastName(), fixer.getEmail());
			}
			String subject = "The Deadline for " + query.getQueryTitle() + " is in 6 hours";
			String fromName = FixitVariables.ADMIN_USERNAME_FROM;
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
			request.setTemplate_name("deadline-request-alert");
			emailUtility.sendEmail(request);
		}
	}

	@Override
	public void emailToAppliedFixersMemberDisapproveRequest(String base, Query query, User fixer)
			throws FixitException {

		MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
		int recipientCount = 0;
		List<MessageMergeVars> receiptentMessageVars = new ArrayList<MessageMergeVars>();
		Map<String, String> receiptentParams = new HashMap<String, String>();
		MessageMergeVars messageMergeVars;
		// Set<QueryAppliedFixers> fixersList = query.getQueryAppliedFixers();
		MandrillRecipient[] recipients = new MandrillRecipient[1];
		Map<String, String> globalMergeVarMap = new HashMap<String, String>();
		List<MergeVar> receiptentMergeVar = new ArrayList<MergeVar>();

		if(FixitVariables.TEST )
		{
			recipients[0] = new MandrillRecipient("ERP Fixer",
					FixitVariables.FIXER_EMAIL);
		}
		else
		{
		recipients[0] = new MandrillRecipient(fixer.getFirstName() + " " + fixer.getLastName(),
				fixer.getEmail());
		}
		messageMergeVars = new MessageMergeVars();
		receiptentParams.put("userName", fixer.getFirstName());
		receiptentMergeVar = createGlobalMergeVar(receiptentParams);
		messageMergeVars.setRcpt(fixer.getEmail());
		messageMergeVars.setVars(receiptentMergeVar);
		receiptentMessageVars.add(messageMergeVars);
		String subject = "Update on Member Request: " + query.getQueryTitle();
		String fromName = FixitVariables.ADMIN_USERNAME_FROM;
		String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;

		request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
				createGlobalMergeVar(globalMergeVarMap), receiptentMessageVars, null));
		request.setTemplate_name("member-disapprove-request");
		emailUtility.sendEmail(request);

	}

	@Override
	public void emailToUserChatMessageByAdmin(String msg, User user, String baseUrl) throws FixitException {

		if (DbConstants.EMAIL_ALERT.equals(user.getEmailAlert())) {
			MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
			MandrillRecipient[] recipients = new MandrillRecipient[1];
			Map<String, String> globalMergeVarMap = new HashMap<String, String>();

			

			globalMergeVarMap.put("userName", user.getFirstName());
			globalMergeVarMap.put("messageContent", msg.replaceAll("\\n", "<br>"));
			globalMergeVarMap.put("queryLink", baseUrl);
			if(FixitVariables.TEST)
			{
				if(user.getUserType().equals("C"))
				{
					recipients[0] = new MandrillRecipient("ERP Member",
							FixitVariables.MEMBER_EMAIL);
				}
				else
				{
					recipients[0] = new MandrillRecipient("ERP Fixer",
							FixitVariables.FIXER_EMAIL);
				}
			}
			else
			{
				recipients[0] = new MandrillRecipient(user.getFirstName() + " " + user.getLastName(), user.getEmail());
			}
			
			String subject = "Message from Admin";
			String fromName = "Admin" + " via ERPfixers";
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			List<MandrillAttachment> attachments = new ArrayList<MandrillAttachment>();

			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
					createGlobalMergeVar(globalMergeVarMap), null, attachments));
			request.setTemplate_name("message");
			emailUtility.sendEmail(request);
		}

	}

	@Override
	public void emailToAdminChatMessageByUser(String msg, User user, String baseUrl) throws FixitException {

		if (DbConstants.EMAIL_ALERT.equals(user.getEmailAlert())) {
			MandrillTemplatedMessageRequest request = new MandrillTemplatedMessageRequest();
			MandrillRecipient[] recipients = new MandrillRecipient[1];
			Map<String, String> globalMergeVarMap = new HashMap<String, String>();

			String subject = "Message from " + user.getFirstName() + " " + user.getLastName();
			String fromName = user.getFirstName() + " " + user.getLastName() + " via ERPfixers";
			String fromEmail = FixitVariables.ADMIN_EMAIL_FROM;
			
			globalMergeVarMap.put("userName", "Admin");
			globalMergeVarMap.put("messageContent", msg.replaceAll("\\n", "<br>"));
			globalMergeVarMap.put("queryLink", baseUrl + "/admin/adminChatDetail?userId=" + user.getUserId());
			recipients[0] = new MandrillRecipient(user.getFirstName() + " " + user.getLastName(),FixitVariables.ADMIN_EMAIL_FROM);
			
			List<MandrillAttachment> attachments = new ArrayList<MandrillAttachment>();

			request.setMessage(getRequestBody(fromName, fromEmail, recipients, subject,
			createGlobalMergeVar(globalMergeVarMap), null, attachments));
			request.setTemplate_name("message");
			emailUtility.sendEmail(request);
		}

	}
}
