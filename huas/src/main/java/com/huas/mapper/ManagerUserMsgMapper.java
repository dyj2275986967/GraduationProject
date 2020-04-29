package com.huas.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.huas.entities.UniversityDep;

public interface ManagerUserMsgMapper {
	//查找学院信息
	@Select("select * from university_dep")
	List<UniversityDep> findDepMsg();
	// 更新user信息
    @Update("UPDATE USER SET NAME=#{name},sex=#{sex},dep=#{dep},signature=#{signature} WHERE user_id=#{userId}")
	void updateMsg(@Param("sex")String sex,@Param("name")String name,@Param("dep")String dep,@Param("signature")String signature,@Param("userId")Integer userId);
 
    //更新user头像
    @Update("UPDATE USER SET img=#{src} WHERE user_id=#{id}")
    void updateMsgTx(@Param("src")String src,@Param("id")Integer id);
    
    //通过用户id插入手机号
    @Update("UPDATE USER SET phone=#{phone} WHERE user_id=#{id}")
    void insertPhone(@Param("phone")String phone,@Param("id")Integer id);
    //通过用户id插入邮箱号
    @Update("UPDATE USER SET email=#{email} WHERE user_id=#{id}")
    void insertEmail(@Param("email")String email,@Param("id")Integer id);
}
