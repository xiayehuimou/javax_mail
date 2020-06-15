package com.example.demo.mail;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;

public class MessageUtil {
	
	private static MessageSourceAccessor messageSourceAccessor;
	
	public static MessageSourceAccessor geMessageSourceAccessort() {
		if(messageSourceAccessor == null){
			messageSourceAccessor = new MessageSourceAccessor((MessageSource)SpringUtil.getBean("messageSource"));
		}
		return  messageSourceAccessor;
	}
	
	public static final String getMessage(String code, String... args){
		MessageSourceAccessor msa = geMessageSourceAccessort();
		if(msa != null){
			return msa.getMessage(code, args, code);
		}else{
			return code;
		}
	}
}
