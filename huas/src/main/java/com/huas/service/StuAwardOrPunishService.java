package com.huas.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.huas.entities.RewardOrPunish;
import com.huas.entities.Student;
import com.huas.utils.PageModel;

public interface StuAwardOrPunishService {	
	    // 查询所有的学生信息
		  List<RewardOrPunish> findAllStuMsg(String keywords,PageModel pageModel, String universityDepId,  String majorId,  String gradeClassId,  String clazzId);
		// 插入学生信息
		  void insert(String stuId,String awadrs,String reason,java.util.Date dataT);
        // Excel导出
		  void ExcelExport(HttpServletResponse response,String keywords,PageModel pageModel, String universityDepId,  String majorId,  String gradeClassId,  String clazzId);
        //通过学生id查询奖罚信息
		  List<RewardOrPunish>  findStuAwardOrPunishMsgByStuId(String stuId);;
		  //通过奖罚id修改奖罚信息			
	      void updateAwardOrPunishByid(Integer id,java.util.Date data,String reason);
			 //通过奖罚id删除奖罚信息
		  void deleteAwardOrPunishByid(Integer id);

}
