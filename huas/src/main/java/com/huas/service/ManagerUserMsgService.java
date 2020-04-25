package com.huas.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.huas.entities.UniversityDep;

public interface ManagerUserMsgService {
      // 查询所有学院信息
	 List<UniversityDep> findDepMsg();
	//更新用户信息
   	void updateMsg(String sex,String name,String dep,String signature,Integer userId);
    //
   	String updUserProfile(MultipartFile newProfile);

}
