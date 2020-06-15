package com.example.demo.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 
 * 功能描述:邮件登录验证
 * @author
 */
public class LoginMailAuthenticator extends Authenticator{
    //用户名
    private String userName;
    //密码
    private String password;
    
    public LoginMailAuthenticator(String userName, String password){
        super();
        this.userName = userName;
        this.password = password;
    }
    
    public PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(userName, password);
    }

}
