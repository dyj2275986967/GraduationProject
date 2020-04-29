package com.huas.service;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.huas.entities.Major;
import com.huas.entities.UniversityDep;
import com.huas.entities.User;
import com.huas.utils.PageModel;

public interface AdmisterUserService {

	
	 
	
	     //插入用户信息
	     void insertUserMsg(String loginName,String pwd,Integer num,Integer level,Integer id);
	     //通过当前登录用户查询 其下的账号信息
	     List<User> selectAllUserMsg(PageModel pageModel,Integer id,Integer level);
	     //根据用户id删除用户信息
	     void deleteUserByUserId(Integer id);
          //查所有的院系
	      List<UniversityDep> findAllDep(PageModel pageModel);
	      //删除学院
	 
	      void deleteDep(String depId);
	      //添加学院
	      void insertDep(String depName);
	      //修改学院信息
	      void updateDep(String depName,String depId);
	      //通过学院id查询所有院系信息
	      List<Major> findAllMajor(Integer depId,PageModel pageModel);
	      //添加学院
	      void insertMajor(String majorName,String depId);
	      //修改学院
	      void updateMajor(String majorName,Integer majorId);
	      
	      
}
