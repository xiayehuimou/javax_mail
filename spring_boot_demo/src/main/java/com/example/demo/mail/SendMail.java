package com.example.demo.mail;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendMail {
	private static Logger logger = LoggerFactory.getLogger(SendMail.class);
	// 邮件发送开关
	private final static String MAIL_SWITCH = "on";
	// 发送邮件验证
	private final static String SMTP_AUTH_VALUE = "true";
	// 发送邮件协议
	private final static String SMTP_PROTOCOL = "smtp";

	/**
	 * 
	 * 发送普通文本邮件
	 * @param email
	 * @return
	 */
	public ResultBean sendMail(TbEmail email) {
		ResultBean bean = new ResultBean();
		Transport tran = null;
		try {
			// 判断邮件发送开关是否开启
            if (!"on".equals(MAIL_SWITCH)) {
                logger.debug("邮件发送开关处于关闭状态，不发送邮件");
                
                bean.setStatus(false);
                bean.setMessages("邮件发送开关处于关闭状态，不发送邮件");
                return bean;
            }
			// 1. 创建参数配置, 用于连接邮件服务器的参数配置
			Properties prop = new Properties();
			// 身份验证：true需要 ，false不需要
			prop.put("mail.smtp.auth", SMTP_AUTH_VALUE); 
			// 发件人的邮箱服务器地址
			prop.put("mail.smtp.host", email.getEmailServer());
			// 发件人的邮箱服务器端口号
			prop.put("mail.smtp.port", email.getEmailPort());
			// 使用的协议（JavaMail规范要求）
			prop.put("mail.transport.protocol", SMTP_PROTOCOL);
			
			// PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
	        //     如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
	        //     取消下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
	        /*
	        // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
	        //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
	        //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
	        final String smtpPort = "465";
	        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        prop.put("mail.smtp.socketFactory.fallback", "false");
	        prop.put("mail.smtp.socketFactory.port", smtpPort);
	        */
			
			// 2. 根据配置创建会话对象, 用于和邮件服务器交互
			Session session = Session.getInstance(prop, new LoginMailAuthenticator(email.getEmailAddress(), email.getEmailPassword()));
			// 邮件服务器进行验证
			tran = session.getTransport(SMTP_PROTOCOL);
			// 建立服务器连接
			// 这里连接服务器的用户名可能需要是完整的邮箱地址（含域名）、也可能不需要域名（原因未知），所以进行2次连接尝试
			try { 
				// 使用完整域名进行邮件服务器连接
				tran.connect(email.getEmailServer(), email.getEmailAddress(), email.getEmailPassword());
				logger.debug("使用含域名的用户名进行邮件服务器连接成功");
			} catch (Throwable e) {
				logger.debug("使用完整域名的用户名进行邮件服务器连接失败", e);

				logger.debug("开始使用不含域名的用户名进行邮件服务器连接");
				String shortUserName = email.getEmailAddress().split("@")[0];
				tran.connect(email.getEmailServer(), shortUserName,  email.getEmailPassword());
				logger.debug("使用不含域名的用户名进行邮件服务器连接成功");
				bean.setMessages("邮件发送失败");
				bean.setStatus(true);
				return bean;
			}
			// 设置为debug模式, 可以查看详细的发送 log
	        // session.setDebug(true);
			// 3.创建邮件
			Message msg = new MimeMessage(session);
			// 设置发件人地址
	        msg.setFrom(new InternetAddress(email.getEmailAddress()));
	        /**
	         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
	         * MimeMessage.RecipientType.TO:发送
	         * MimeMessage.RecipientType.CC：抄送
	         * MimeMessage.RecipientType.BCC：密送
	         */
	        // 设置收件人信息(多个收件人,拼接)
	        String recipients = email.getRecipients();
	        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
			// 邮件主题
			msg.setSubject(email.getSubject());
			// 邮件正文（可以使用html标签）
			// 添加纯文本
			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(email.getContent(), "text/html;charset=UTF-8");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(bodyPart);
			// 添加附件
			if(CollectionUtils.isNotEmpty(email.getAttachList())) {
				addTach(email.getAttachList(),multipart);
			}
			// 设置邮件内容
			msg.setContent(multipart);
			// 发件时间
			msg.setSentDate(email.getSendDate());
			// 4.发送邮件
			tran.sendMessage(msg, msg.getAllRecipients());
			logger.info("邮件发送完毕");
		} catch (Throwable e) {
			logger.error("发送邮件通知失败", e);
			bean.setMessages("邮件发送失败");
			bean.setStatus(true);
			return bean;
		} finally {
			if (tran != null) {
				try {
					// 5.关闭邮件连接
					tran.close();
				} catch (MessagingException e) {
					logger.error("邮件服务器连接关闭失败", e);
				}
			}
		}
		return bean;
	}
	
	/**
	 * 
	 * 添加多个附件
	 * 
	 * @param attachList
	 * @param multipart
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public void addTach(List<String> attachList,Multipart multipart) throws Exception {
		for (String attach : attachList) {
			MimeBodyPart mailArchieve = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(attach);
			mailArchieve.setDataHandler(new DataHandler(fds));
			mailArchieve.setFileName(MimeUtility.encodeText(fds.getName(), "UTF-8", "B"));
			multipart.addBodyPart(mailArchieve);
		}
	}
	
}
