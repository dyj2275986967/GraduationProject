package com.huas.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.huas.entities.Major;
import com.huas.entities.UniversityDep;
import com.huas.entities.User;
import com.huas.provider.AdmisterUserProvider;
import com.huas.utils.PageModel;


public interface AdmisterUserMapper {

	
	//导入数据
      @InsertProvider(type = AdmisterUserProvider.class, method = "buildInsertUserMsgList")
	    int buildInsertStudentMsgList(List<User> list);
    //超级管理员查询所有的管理员信息
      @Select("SELECT u.user_id, u.name,u.login_name,u.pwd,u.dep,u.level,u.manage_id,m.name AS 'manager_name' FROM USER u,USER m WHERE u.`manage_id`=m.`user_id` limit #{pageStarNum} , #{pageModel.pageSize} ")
        List<User> selectAllMsgByAdmin(@Param("pageStarNum")Integer pageStarNum,@Param("pageModel")PageModel pageModel);
   

      //管理员查询 其生成账号下的二级管理员信息
      @Select("SELECT u.user_id, u.name,u.login_name,u.pwd,u.dep,u.level,u.manage_id,m.name AS 'manager_name' FROM USER u,USER m WHERE u.`manage_id`=m.`user_id`  AND  m.`user_id`=#{id} limit #{pageStarNum},#{pageModel.pageSize}")
      List<User> selectAllMsgByManager(@Param("id")Integer id,@Param("pageStarNum")Integer pageStarNum,@Param("pageModel")PageModel pageModel);

	  //查询总记录数
      @Select("SELECT count(u.name) FROM USER u,USER m WHERE u.`manage_id`=m.`user_id` ")
      int findSelectAllMsgByAdminNum();
    //查询总记录数
      @Select("SELECT count(*) FROM USER u,USER m WHERE u.`manage_id`=m.`user_id`  AND  m.`user_id`=#{id} ")
      int findselectAllMsgByManager(@Param("id")Integer id);
      
      //根据用户id删除 用户信息     
      @Delete("delete from user where user_id=#{id}")
      void deleteUserByUserId(@Param("id")Integer id);
       /**
        * 学院管理开始
        * @return
        */    
      
      //查询所有的学院信息
      @Select("SELECT * FROM university_dep limit #{pageStarNum} , #{pageModel.pageSize} ")
      List<UniversityDep> findAllDep(@Param("pageStarNum")Integer pageStarNum,@Param("pageModel")PageModel pageModel);
      @Select("SELECT count( *) FROM university_dep ")
      int  findAllDepNum();
      
      //添加学院
      @Insert("insert into university_dep  (dep_name) values(#{depName}) ")
      void insertDep(@Param("depName")String depName);
      
      //删除学院
      @Delete("delete from university_dep where dep_id=#{depId}")
      void deleteDep(@Param("depId")String depId);
      //更新学院 
      @Update("update university_dep set dep_name=#{depName} where dep_id=#{depId}  ")
      void updateDep(@Param("depName")String depName,@Param("depId")String depId);
      //通过学院id查专业信息
      @Select("SELECT * FROM major where dep_id=#{depId} limit #{pageStarNum} , #{pageModel.pageSize} ")
      List<Major> findAllMajor(@Param("depId")Integer depId,@Param("pageStarNum")Integer pageStarNum,@Param("pageModel")PageModel pageModel);
      @Select("SELECT count(*) FROM major where dep_id=#{depId} ")
      int  findAllMajorNum(@Param("depId")Integer depId);
      //添加学院
      @Insert("insert into major  (major_name,dep_id) values(#{majorName},#{depId}) ")
      void insertMajor(@Param("majorName")String majorName,@Param("depId")Integer depId);
      //修改
      @Insert("update major set major_name=#{majorName} where dep_id=#{majorId}  ")
      void updateMajor(@Param("majorName")String majorName,@Param("majorId")Integer majorId);
      
      
      /**
       * 学院管理结束
       * @return
       */
     
      
	
}
