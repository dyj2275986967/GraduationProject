package com.huas.utils;




import java.net.InetAddress;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import com.sun.mail.smtp.SMTPMessage;


/**
 * java 发邮件
 * @author 500R5H
 *
 */
public class EmailUtil {

	

	

	public static void sendEmain( String email,String yzm) {

		// TODO Auto-generated method stub

		try {
			// 1、创建Properties对象，封装邮件服务器的相关信息
			Properties props = new Properties();
			// 服务器地址
			props.setProperty("mail.smtp.host", "smtp.126.com");
			// 服务器需要鉴权
			props.setProperty("mail.smtp.auth", "true");

			// 2、创建Authenticator的实例，实现账户、密码的鉴权。
			Authenticator auth = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					// 使用账号 以及 授权码登录至邮件服务器，此处采用 126邮件服务器 授权码
					return new PasswordAuthentication("internetCompanyd@126.com", "dyj19971104");
				}
			};

			// 3、获得Session实例
			Session mailSession = Session.getInstance(props, auth);

			// 4、创建SMTPMessage，要提供session
			SMTPMessage msg = new SMTPMessage(mailSession);

			
			// 5、设置邮件标题，没有标题的邮件几乎都会被认为是垃圾邮件被系统退回。
			msg.setSubject("用户邮箱验证登陆，请勿回复");
			// 6、设置邮件内容
			msg.setContent("【学生综合素质管理系统】您的验证码为"+yzm+",60s内有效，请尽快验证。如非本人操作，请忽略本条邮件。" , "text/html;charset=UTF-8");

			// 设置接收者
			// 7、接收者类型由：TO(收件人)、CC(抄送)、BCC(密送)
			msg.setRecipient(RecipientType.TO, new InternetAddress(email));
			// 8、设置发送人
			msg.setFrom(new InternetAddress("internetCompanyd@126.com"));
			// 发送邮件
			Transport.send(msg);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	
	public static void sendEmainByForget(String loginName, String email,String userName,String pwd) {

		// TODO Auto-generated method stub

		try {
			// 1、创建Properties对象，封装邮件服务器的相关信息
			Properties props = new Properties();
			// 服务器地址
			props.setProperty("mail.smtp.host", "smtp.126.com");
			// 服务器需要鉴权
			props.setProperty("mail.smtp.auth", "true");

			// 2、创建Authenticator的实例，实现账户、密码的鉴权。
			Authenticator auth = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					// 使用账号 以及 授权码登录至邮件服务器，此处采用 126邮件服务器 授权码
					return new PasswordAuthentication("internetCompanyd@126.com", "dyj19971104");
				}
			};

			// 3、获得Session实例
			Session mailSession = Session.getInstance(props, auth);

			// 4、创建SMTPMessage，要提供session
			SMTPMessage msg = new SMTPMessage(mailSession);

			
			// 5、设置邮件标题，没有标题的邮件几乎都会被认为是垃圾邮件被系统退回。
			msg.setSubject("用户邮箱验证登陆，请勿回复");
			// 6、设置邮件内容
			msg.setContent("【学生综合素质管理系统】尊敬的"+userName+",恭喜您,修改密码成功,您的账号："+loginName+" 你的密码为:"+pwd+",注意保管。" , "text/html;charset=UTF-8");

			// 设置接收者
			// 7、接收者类型由：TO(收件人)、CC(抄送)、BCC(密送)
			msg.setRecipient(RecipientType.TO, new InternetAddress(email));
			// 8、设置发送人
			msg.setFrom(new InternetAddress("internetCompanyd@126.com"));
			// 发送邮件
			Transport.send(msg);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	

	

	
}
