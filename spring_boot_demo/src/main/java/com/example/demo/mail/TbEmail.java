package com.example.demo.mail;

import java.util.Date;
import java.util.List;

public class TbEmail {

	// 发件人：邮箱地址
    private String emailAddress;

    // 发件人：邮箱密码（如果开通授权码此处就是授权码）
    private String emailPassword;

    // 发件人：邮箱服务器
    private String emailServer;

    // 发件人：邮箱服务器端口号
    private String emailPort;
    
    // 收件人邮箱地址(多个收件人以英文逗号拼接)
    private String recipients;
    
    // 邮件主题
    private String subject;
    
    // 邮件内容：纯文本
    private String content;
    
    // 邮件内容：附件(附件地址)
    private List<String> attachList;
    
    // 发件时间
    private Date sendDate;

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getEmailServer() {
		return emailServer;
	}

	public void setEmailServer(String emailServer) {
		this.emailServer = emailServer;
	}

	public String getEmailPort() {
		return emailPort;
	}

	public void setEmailPort(String emailPort) {
		this.emailPort = emailPort;
	}

	public String getRecipients() {
		return recipients;
	}

	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public List<String> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<String> attachList) {
		this.attachList = attachList;
	}

}