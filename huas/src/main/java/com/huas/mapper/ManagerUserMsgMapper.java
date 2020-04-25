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

}
