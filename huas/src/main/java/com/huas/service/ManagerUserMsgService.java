package com.huas.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.multipart.MultipartFile;

import com.huas.entities.UniversityDep;

public interface ManagerUserMsgService {
      // 查询所有学院信息
	 List<UniversityDep> findDepMsg();
	//更新用户信息
   	void updateMsg(String sex,String name,String dep,String signature,Integer userId);
    //更新user头像
   	void updateMsgTx(String src,Integer id);
   	
    //通过用户id插入手机号
   	String insertPhoneOrEmail(String phoneOrEmail,String check,String yzm);


}
