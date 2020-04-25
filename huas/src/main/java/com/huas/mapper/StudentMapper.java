	package com.huas.mapper;

import java.util.List;

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
import com.huas.entities.GradeClass;
import com.huas.entities.Major;
import com.huas.entities.Student;
import com.huas.entities.UniversityDep;
import com.huas.provider.StudentProvider;
import com.huas.utils.PageModel;
public interface StudentMapper {

	/***********
	 * 分页相关 动态sql
	 **********
	 */

	// 查询所有的学生信息
   
	@SelectProvider(type = StudentProvider.class, method = "buildSelectStudentMsg")
	  List<Student> findAllStuMsg( 
			  @Param("keywords")String keywords,@Param("pageStarNum")Integer pageStarNum,@Param("pageModel")PageModel pageModel,@Param("universityDepId")String universityDepId,@Param("majorId")String majorId,@Param("gradeClassId")String gradeClassId,@Param("clazzId")String clazzId);
   //获取总记录数
	@SelectProvider(type = StudentProvider.class, method = "buildSelectStudentMsgTotalNum")
        int findTotalNum(
        	 @Param("keywords")String keywords,@Param("pageStarNum")Integer pageStarNum,@Param("pageModel")PageModel pageModel,@Param("universityDepId")String universityDepId,@Param("majorId")String majorId,@Param("gradeClassId")String gradeClassId,@Param("clazzId")String clazzId);
	
	/***********
	 * 分页相关结束
	 **********
	 */
	
	/***********
	 * 添加按钮动态选择相关
	 **********
	 */
	//通过学生院系号查询班级信息
	@Select("SELECT * FROM grade_class WHERE grade_class_id=#{gradeClassId}")
	
	GradeClass findGradeClassMsgByGradeClassId();
	//通过学生院系号查询班级信息
	@Select("SELECT * FROM University_dep WHERE dep_id=#{depId}")
	UniversityDep findUniversityDepMsgByDepId();
	
	
	//通过学生院系号查询班级信息
	@Results(
			@Result(column="dep_id",property="university",one=@One(select="com.huas.mapper.StudentMapper.findUniversityDepMsgByDepId",fetchType=FetchType.EAGER)))
	@Select("SELECT * FROM Major WHERE major_id=#{majorId} ")
	Major findMajorMsgByMajorId();
	
/*
 *班级院系专业之间的耦合 	
 */
   //查询院系信息
	@Results(
	 @Result(column="dep_id",property="majors",one=@One(select="com.huas.mapper.StudentMapper.findMajorMsgByDepId",fetchType=FetchType.EAGER)))
	@Select("select * from university_dep")
	List<UniversityDep> findAllUniversitys();
	 
	//通过院系id查询专业信息
	@Results({
		//将专业里的bean 补全 防止出现null 无关紧要
			@Result(column="dep_id",property="university",one=@One(select="com.huas.mapper.StudentMapper.findUniversityDepMsgByDepId",fetchType=FetchType.EAGER)),
	    //根据专业id 查询班级信息并将值注入进来
			@Result(column="major_id",property="clazzs",one=@One(select="com.huas.mapper.StudentMapper.findClazzMsgByMagorId",fetchType=FetchType.EAGER))})
	@Select("SELECT * FROM Major WHERE dep_id=#{depId}")
	Major findMajorMsgByDepId();
	//通过专页id查询班级信息
	@Results({
		//将专业里的bean 补全 防止出现null 无关紧要
		@Result(column="major_id",property="major",one=@One(select="com.huas.mapper.StudentMapper.findMajorMsgByMajorId",fetchType=FetchType.EAGER)),
	    // 将年级注入进来
		@Result(column="grade_class_id",property="gradeClass",one=@One(select="com.huas.mapper.StudentMapper.findGradeClassMsgByGradeClassId",fetchType=FetchType.EAGER)) })
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
			   @Result(column="grade_class_id",property="clazzs",one=@One(select="com.huas.mapper.StudentMapper.findClazzMsgByGradeClassId",fetchType=FetchType.EAGER)))
	List<GradeClass> findGradeClazzMsgByGradeClazzId();
	
	//通过年级id查询班级信息
	
	@Select("SELECT * FROM CLAZZ WHERE grade_class_id=#{gradeClassId}")
	//无关紧要  耦合程度太高    防止 Clazz对象里 出现 null
	@Results({
		@Result(column="major_id",property="major",one=@One(select="com.huas.mapper.StudentMapper.findMajorMsgByMajorId",fetchType=FetchType.EAGER)),	
		@Result(column="grade_class_id",property="gradeClass",one=@One(select="com.huas.mapper.StudentMapper.findGradeClassMsgByGradeClassId",fetchType=FetchType.EAGER)) })
	Clazz findClazzMsgByGradeClassId();
	
	
	
	
	//添加学生信息
	@Insert( "  INSERT INTO student  (stu_id,stu_name,sex,nation,class_id,phone) VALUES(#{stuId},#{stuName},#{sex},#{nation},#{classId},#{phone}) "   )
	void insertStuMsg(Student stu);
	//更新学生信息
	@Update("UPDATE  student SET stu_id = #{stu.stuId},class_id=#{stu.classId},stu_name=#{stu.stuName} ,sex=#{stu.sex},nation=#{stu.nation} WHERE stu_id=#{oldStuId}")
	void updataStuMsg(@Param("stu")Student stu,@Param("oldStuId")String oldStuId);
	//导出excel
	@SelectProvider(type = StudentProvider.class, method = "deriveExceltStudentMsg")
	  List<Student> deriveExceltStudentMsg( 
			  @Param("keywords")String keywords,@Param("pageStarNum")Integer pageStarNum,@Param("pageModel")PageModel pageModel,@Param("universityDepId")String universityDepId,@Param("majorId")String majorId,@Param("gradeClassId")String gradeClassId,@Param("clazzId")String clazzId);
   
	

	//查询所有的clazz
	@Select("SELECT *   FROM clazz ")
	 List<Clazz> findAllClazz();  
	
	
	//导入数据
       @InsertProvider(type = StudentProvider.class, method = "buildInsertStudentMsgList")
	    int buildInsertStudentMsgList(List<Student> list);

     //删除学生数据
	   @Delete("delete from student where stu_id =#{stuId}")
	   void deleteStudentMsgById(@Param("stuId")String stuId);
	   
}
