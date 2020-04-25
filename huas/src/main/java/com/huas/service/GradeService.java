package com.huas.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.huas.entities.Grade;
import com.huas.entities.GradeClass;
import com.huas.entities.GradeDetail;
import com.huas.entities.GradeDetailWithTable;
import com.huas.entities.Student;
import com.huas.entities.UniversityDep;
import com.huas.provider.GradeProvider;
import com.huas.utils.PageModel;

public interface GradeService {

	
	// 查询所有的成绩信息
      List<Grade> findAllStuGradeMsg(String keywords,PageModel pageModel, String universityDepId,  String majorId,  String gradeClassId,  String clazzId,String courseId,String stuId);
	// 导出Excel :成绩管理主界面
      void findAllStuGradeMsgExportExcel(String keywords,PageModel pageModel, String universityDepId,  String majorId,  String gradeClassId,  String clazzId,String courseId,String stuId,HttpServletResponse response);
      //导入Excel :成绩管理主界面
	  void buildInsertGradeMsgList(List<Grade> list); 
     
      //删除成绩信息
	  void deleteStudentGradeById(String stuId,Integer courseId);
	// 查询院系信息信息 
	  List<UniversityDep> findAllStuUniversityMsg();
    // 查询院系信息信息 
	  List<GradeClass> findGradeClazzMsgByGradeClazzId();
	  //插入学生成绩信息
	  void insertGradeMsg(Integer result,String stuId,Integer courseId);
   	  //更新学生信息
	 void updateGradeResult(Integer result,String stuId,Integer courseId);  
	  //成绩合格
	 
	  List<GradeDetail> buildStudentGradeMsgDetailsPassOrNotPass();

	  //成绩不合格
	 
	  List<GradeDetail> buildStudentGradeMsgDetailsNotPass();
	  
	  //表格详情展示
	  List<GradeDetailWithTable> findStudentAndCourseAndResult(  PageModel pageModel,List<String> courseName, String majorId,  String gradeClassId,
				 String clazzId);
	  //按学院查询课程信息
	  List<String> findCourseNameByMajorId(String majorId);
	  void buildStudentGradeExportExcel( String majorId,  String gradeClassId,
				 String clazzId,HttpServletResponse response);
	  
}
