package com.fixit.domain.bo;

public class QueryAuditBo {
private int queryId;
private int customerId;
private int fixerId;
private String message;
private String msgFrom;
private String status;
private String queryTitle;
private String readStatus;
public int getQueryId() {
	return queryId;
}
public void setQueryId(int queryId) {
	this.queryId = queryId;
}
public int getCustomerId() {
	return customerId;
}
public void setCustomerId(int customerId) {
	this.customerId = customerId;
}
public int getFixerId() {
	return fixerId;
}
public void setFixerId(int fixerId) {
	this.fixerId = fixerId;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getMsgFrom() {
	return msgFrom;
}
public void setMsgFrom(String msgFrom) {
	this.msgFrom = msgFrom;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getQueryTitle() {
	return queryTitle;
}
public void setQueryTitle(String queryTitle) {
	this.queryTitle = queryTitle;
}
public String getReadStatus() {
	return readStatus;
}
public void setReadStatus(String readStatus) {
	this.readStatus = readStatus;
}

}
