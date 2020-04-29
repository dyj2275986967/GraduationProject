package com.huas.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

public class PhoneUtil {

public static void sendPhoneMsg(String code,String phoneNumber){
	 
	 String host = "https://zwp.market.alicloudapi.com";
	    String path = "/sms/sendv2";
	    String method = "GET";
	    String appcode = "25e9ae017b4a49ce43";//删了一坨字母
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("content", "【学生综合素质管理系统】您的验证码为"+code+" ,60s内有效,请尽快验证,如非本人操作,请忽略本条短信。");
	    querys.put("mobile", phoneNumber);

	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	
	    	//获取response的body
	    	//System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}


public static void sendPhoneMsgBycreatePwd(String loginName,String pwd,String phoneNumber){
	 
	 String host = "https://zwp.market.alicloudapi.com";
	    String path = "/sms/sendv2";
	    String method = "GET";
	    String appcode = "25e9ae017b4a49ce80749f634518f4";//删了2个字母
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("content", "【学生综合素质管理系统】修改密码成功,您的账号："+loginName+" 你的密码为:"+pwd+",妥善保管。");
	    querys.put("mobile", phoneNumber);

	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	
	    	//获取response的body
	    	//System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}




}
