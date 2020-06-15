package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.mail.SendMail;
import com.example.demo.mail.TbEmail;

@RestController
public class TestController {

	@RequestMapping("/testMail")
	public String testMail(HttpServletRequest request) throws Exception {
		SendMail sendMail = new SendMail();
		TbEmail tbEmail = new TbEmail();
		tbEmail.setEmailAddress("xxx@xx.com");
		tbEmail.setEmailPassword("xxxx");
		//tbEmail.setEmailServer("mail.hazq.com");
		tbEmail.setEmailServer("xx.xx.xx.xx");
		tbEmail.setEmailPort("xx");
		tbEmail.setRecipients("xx.com,bb.com");
		tbEmail.setSendDate(new Date());
		tbEmail.setSubject("测试title");
		tbEmail.setContent("");
		List<String> attachList = new ArrayList<String>();
		// 附件的地址
		//attachList.add(new String("F:\\plye\\workspace\\workspce_springboot_demo\\spring_boot_demo\\src\\main\\resources\\static\\Penguins.jpg"));
		//attachList.add("F:/plye/workspace/workspce_springboot_demo/spring_boot_demo/src/main/resources/static/开发任务计划表.xlsx");
		tbEmail.setAttachList(attachList);
		sendMail.sendMail(tbEmail);
		return "test";
	}
}
