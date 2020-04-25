package com.huas.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.huas.entities.GradeClass;
import com.huas.entities.Student;
import com.huas.entities.UniversityDep;
import com.huas.utils.PageModel;


public interface StudentMessageService {

	
	// 查询所有的学生信息
	  List<Student> findAllStuMsg(String keywords,PageModel pageModel, String universityDepId,  String majorId,  String gradeClassId,  String clazzId);
	// 查询院系信息信息 
	  List<UniversityDep> findAllStuUniversityMsg();
		// 查询院系信息信息 
	  List<GradeClass> findGradeClazzMsgByGradeClazzId();
	  //添加学生信息
	  void insertStudentMsg(Student stu);
	  //更新学生信息
	  void updataStudentMsg(Student stu,String oldStuId);
	  //导出excel
	  void deriveExceltStudentMsg(String keywords,PageModel pageModel, String universityDepId,  String majorId,  String gradeClassId,  String clazzId,HttpServletResponse response);
	  
	  //导入Excel
	  void buildInsertStudentMsgList(List<Student> list);
      //删除学生信息
	  void deleteStudentBystuId(String stuId);
}
