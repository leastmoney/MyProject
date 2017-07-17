package cn.itcast.jx.mail;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
public class MailTest03 {
	public static void main(String[] args) throws Exception {
		
		mailUtils("a1054740130@163.com", "1054740130@qq.com", "一起学习", "d://dog.jpg", "福利.jsp");
	}
	
	
	/**
	 * @Description:这是一个通过程序发送邮件的功能,目前只支持126.com,163.com的邮箱
	 * @param from 从哪个邮箱发出 出发点
	 * @param to 要送到那个邮箱 目的地
	 * @param subject 抄本
	 * @param File    你的资源完整路径
	 * @param Name	  希望对方下载附件时的名字
	 * @throws Exception
	 * @version 1.0
	 * @author 未来的程序员 Mr.钱
	 * @time 2017年7月3日下午8:08:55
	 */
	@Test
	@SuppressWarnings("resource")
	public static void mailUtils(String from,String to, String subject,String File,String Name) throws Exception{
		//发送电子邮件--采用spring发送复杂邮件
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		
		//获取bean 
		JavaMailSender mailSender = (JavaMailSender) ctx.getBean("mailSender");
		
		//创建邮件
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		//发送复杂邮件---必须记住复杂邮件助手
		//第一个参数：邮件
		//第二个参数：是否是复杂邮件 true：复杂邮件  false：不是复杂邮件
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
		
		//设置发送地址
		helper.setCc("a1054740130@163.com");
		helper.setFrom("a1054740130@163.com");
		//设置接收地址
		helper.setTo("1054740130@qq.com");
		//设置标题
		helper.setSubject("我的天,为什么不行呢");
		//设置内容--采用HTML的方式加图片
		//第一个参数：文本内容
		//第二个参数：是否是html,默认值不是
		helper.setText("<html><head><title>发送图片</title></head><body><h1>图片精选/h1><img src=cid:picture></body></html>",true);
		
		FileSystemResource fsr = new FileSystemResource(new File("d://Nice Bowl!11.png"));
		
		
		//将图片资源放到文件中
		//第一个参数：cid的值
		//第二个参数：FileSystemResource资源
		helper.addInline("picture", fsr);
		//带附件
		/*
		 * 第一个参数,附件的名字
		 * 第二个参数.附件资源
		 */
		helper.addAttachment("福利.png",fsr);
		//执行发送
		mailSender.send(mimeMessage);
		
		System.out.println("发送成功");
	}
}

