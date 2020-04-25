package com.huas.mapper;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.FetchType;

import com.huas.entities.Clazz;
import com.huas.entities.Course;
import com.huas.entities.Grade;
import com.huas.entities.GradeClass;
import com.huas.entities.GradeDetail;
import com.huas.entities.GradeDetailWithTable;
import com.huas.entities.Major;
import com.huas.entities.Student;
import com.huas.entities.UniversityDep;
import com.huas.provider.GradeProvider;
import com.huas.provider.StudentProvider;
import com.huas.utils.PageModel;

public interface GradeMapper {

	//分页  模糊查询  条件搜索
	@SelectProvider(type = GradeProvider.class, method = "buildSelectStudentGradeMsg")
	  List<Grade> findAllStuMsg( 
			  @Param("keywords")String keywords,@Param("pageStarNum")Integer pageStarNum,@Param("pageModel")PageModel pageModel,@Param("universityDepId")String universityDepId,@Param("majorId")String majorId,@Param("gradeClassId")String gradeClassId,@Param("clazzId")String clazzId,@Param("courseId")String  courseId,@Param("stuId")String stuId);
    //获取总记录数
	@SelectProvider(type = GradeProvider.class, method = "buildSelectStudentGradeMsgTotalNum")
      int findTotalNum(
    		  @Param("keywords")String keywords,@Param("pageStarNum")Integer pageStarNum,@Param("pageModel")PageModel pageModel,@Param("universityDepId")String universityDepId,@Param("majorId")String majorId,@Param("gradeClassId")String gradeClassId,@Param("clazzId")String clazzId,@Param("courseId")String  courseId,@Param("stuId")String stuId);
	//Excel导出
	@SelectProvider(type = GradeProvider.class, method = "buildSelectStudentGradeMsgExport")
	  List<Grade> findAllStuMsgExportExcel( 
    		  @Param("keywords")String keywords,@Param("pageStarNum")Integer pageStarNum,@Param("pageModel")PageModel pageModel,@Param("universityDepId")String universityDepId,@Param("majorId")String majorId,@Param("gradeClassId")String gradeClassId,@Param("clazzId")String clazzId,@Param("courseId")String  courseId,@Param("stuId")String stuId);
	
	
	//将学生成绩导入grade
	@InsertProvider(type = GradeProvider.class, method = "buildInsertGradeMsgList")
	int buildInsertGradeList(List<Grade> list);
	
    //删除学生数据
	   @Delete("delete from grade where stu_id=#{stuId} and course_id= #{courseId}")
	   void deleteStudentGradeById(@Param("stuId")String stuId,@Param("courseId")Integer courseId);

	//插入学生成绩信息
	  @Insert("INSERT INTO grade (result,stu_id,course_id) VALUES(#{result},#{stuId},#{courseId}) ")
	  int insertIntoGrade(@Param("result")Integer result,@Param("stuId")String stuId,@Param("courseId")Integer courseId);
	 
	 //更新学生信息
	  @Update("UPDATE  grade SET result = #{result} WHERE stu_id=#{stuId} and course_id=#{courseId}")
	  void updateGradeResult(@Param("result")Integer result,@Param("stuId")String stuId,@Param("courseId")Integer courseId);
	  
	 /****************
	 *图表展示相关开始
	 ****************/
	  //成绩合格和不合格
	 @SelectProvider(type = GradeProvider.class, method = "buildStudentGradeMsgDetailsPassOrNotPass")
	  List<GradeDetail> buildStudentGradeMsgDetailsPassOrNotPass();
	  
	  
	  //成绩不合格
	 @SelectProvider(type = GradeProvider.class, method = "buildStudentGradeMsgDetailsbNotPass")
	  List<GradeDetail> buildStudentGradeMsgDetailsNotPass();
	 /****************
	 *图表展示相关结束
	 ****************/
	 /****************
	 *详情展示相关开始
	 ****************/
     //查询全部课程信息
	 @Select("SELECT course_name FROM  course WHERE major_id= #{majorId} ")
	 List<String> findCourseNameByMajorId(@Param("majorId")Integer majorId);
	 //查询学生成绩信息 和分页
	 @SelectProvider(type = GradeProvider.class, method = "buildStudentGradeDetials")
	 List<GradeDetailWithTable> findStudentAndCourseAndResult(@Param("pageStarNum")Integer pageStarNum,@Param("pageModel")PageModel pageModel,@Param("courseName")  List<String> courseName,@Param("majorId")  String majorId, @Param("gradeClassId")  String gradeClassId,
				@Param("clazzId")  String clazzId);
 
	 //查询总记录数
	 @SelectProvider(type = GradeProvider.class, method = "buildStudentGradeDetialsTotalNum")
	 int buildStudentGradeDetialsTotalNum(@Param("courseName")  List<String> courseName,@Param("majorId")  String majorId, @Param("gradeClassId")  String gradeClassId,
				@Param("clazzId")  String clazzId);
	
	 //导出Excel
	 @SelectProvider(type = GradeProvider.class, method = "buildStudentGradeExportExcel")
	 List<GradeDetailWithTable> buildStudentGradeExportExcel( @Param("courseName")  List<String> courseName,@Param("majorId")  String majorId, @Param("gradeClassId")  String gradeClassId,
				@Param("clazzId")  String clazzId);

	 /****************
	  *详情展示相关结束
	  ****************/
	 
	   
	 /****************
	 * 下拉框选择开始
	 ****************/
	//通过学生院系号查询班级信息
	@Select("SELECT * FROM grade_class WHERE grade_class_id=#{gradeClassId}")
	
	GradeClass findGradeClassMsgByGradeClassId();
	//通过学生院系号查询班级信息
	@Select("SELECT * FROM University_dep WHERE dep_id=#{depId}")
	UniversityDep findUniversityDepMsgByDepId();
	
	
	//通过学生院系号查询班级信息
	@Results(
			@Result(column="dep_id",property="university",one=@One(select="com.huas.mapper.GradeMapper.findUniversityDepMsgByDepId",fetchType=FetchType.EAGER)))
	@Select("SELECT * FROM Major WHERE major_id=#{majorId} ")
	Major findMajorMsgByMajorId();
	
/*
 *班级院系专业之间的耦合 	
 */
   //查询院系信息
	@Results(
	 @Result(column="dep_id",property="majors",one=@One(select="com.huas.mapper.GradeMapper.findMajorMsgByDepId",fetchType=FetchType.EAGER)))
	@Select("select * from university_dep")
	List<UniversityDep> findAllUniversitys();
	 
	//通过院系id查询专业信息
	@Results({
		//将专业里的bean 补全 防止出现null 无关紧要
			@Result(column="dep_id",property="university",one=@One(select="com.huas.mapper.GradeMapper.findUniversityDepMsgByDepId",fetchType=FetchType.EAGER)),
	    //根据专业id 查询班级信息并将值注入进来
			@Result(column="major_id",property="clazzs",one=@One(select="com.huas.mapper.GradeMapper.findClazzMsgByMagorId",fetchType=FetchType.EAGER)),
			 //根据专业id 查询班级信息并将值注入进来
			@Result(column="major_id",property="courses",one=@One(select="com.huas.mapper.GradeMapper.findCourseMsgByMajorId",fetchType=FetchType.EAGER))})
	@Select("SELECT * FROM Major WHERE dep_id=#{depId}")
	Major findMajorMsgByDepId();
	//通过专页id查询班级信息
	@Results({
		//将专业里的bean 补全 防止出现null 无关紧要
		@Result(column="major_id",property="major",one=@One(select="com.huas.mapper.GradeMapper.findMajorMsgByMajorId",fetchType=FetchType.EAGER)),
	    // 将年级注入进来
		@Result(column="grade_class_id",property="gradeClass",one=@One(select="com.huas.mapper.GradeMapper.findGradeClassMsgByGradeClassId",fetchType=FetchType.EAGER)),
		//封装学生信息
		@Result(column="class_id",property="stus",one=@One(select="com.huas.mapper.GradeMapper.findStudentByClassId",fetchType=FetchType.EAGER)),
		//property 实体类映射的属性  column 字段名
		@Result(property="classId",column = "class_id")})
	@Select("SELECT * FROM CLAZZ WHERE major_id=#{majorId}")
	Clazz findClazzMsgByMagorId();
	/*
	 *班级院系专业之间的耦合 	
	 */	
	/*
	 *年级班级之间的耦合 	
	 */	
	//查询所有的年级信息 并将班级信息注入到年级信息里
	@Select("SELECT * FROM GRADE_CLASS ")
	@Results(
			   @Result(column="grade_class_id",property="clazzs",one=@One(select="com.huas.mapper.GradeMapper.findClazzMsgByGradeClassId",fetchType=FetchType.EAGER)))
	List<GradeClass> findGradeClazzMsgByGradeClazzId();
	
	//通过年级id查询班级信息
	
	@Select("SELECT * FROM CLAZZ WHERE grade_class_id=#{gradeClassId}")
	//无关紧要  耦合程度太高    防止 Clazz对象里 出现 null
	@Results({
		@Result(column="major_id",property="major",one=@One(select="com.huas.mapper.GradeMapper.findMajorMsgByMajorId",fetchType=FetchType.EAGER)),	
		@Result(column="grade_class_id",property="gradeClass",one=@One(select="com.huas.mapper.GradeMapper.findGradeClassMsgByGradeClassId",fetchType=FetchType.EAGER))})
	Clazz findClazzMsgByGradeClassId();
	
	//根据专业信息查询课程信息
	@Select("SELECT * FROM course WHERE major_id=#{majorId} ")
	Course findCourseMsgByMajorId();
	
	
	@Select("SELECT * FROM student where class_id=#{classId}" )
	Student findStudentByClassId();
  
      
	 /****************
	  * 下拉框选择结束
	  ****************
	 */
   
}
