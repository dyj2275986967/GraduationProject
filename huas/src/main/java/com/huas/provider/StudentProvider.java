package com.huas.provider;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import com.huas.entities.Student;
import com.huas.utils.PageModel;

/**
 * 学生信息管理动态sql
 * 
 * @author 500R5H
 *
 */
public class StudentProvider {

	/**
	 * 模糊查询加条件搜索 加分页的动态sql
	 * 
	 * @param name
	 * @return
	 */
	public static String buildSelectStudentMsg(
			@Param("universityDepId")final String universityDepId, @Param("majorId")final String majorId,  @Param("gradeClassId")final String gradeClassId, @Param("clazzId")final String clazzId) {
		return new SQL() {
			{
				SELECT("t.stu_id,t.stu_name,t.sex,t.phone,t.nation,c.class_id,c.class_name,j.major_id,j.major_name,g.grade_class_id,g.grade_class_name,d.dep_id,d.dep_name ");
				FROM(" student t ,clazz c,major j,university_dep d,grade_class g ");

				// 判断院系是否为空
				boolean depFlag = universityDepId != null && !"".equals(universityDepId);
				// 判断专业是否为空
				boolean majorFlag = majorId != null && !"".equals(majorId);
				// 判断班级是否为空
				boolean clazzFlag = clazzId != null && !"".equals(clazzId);
				// 判断年级是否为空
				boolean gradeClassFlag = gradeClassId != null && !"".equals(gradeClassId);

			
				//当院系和年级为空没有条件查询
				if(!depFlag&&!gradeClassFlag){
					
					WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  ");	
					
					
				}else if(!depFlag&&gradeClassFlag){
					WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%') AND g.grade_class_id=#{gradeClassId} ");	
					
				}else{
				
				
				// 当年级不为空时，带年级条件查询
				if (gradeClassFlag) {

					// 按年级查询班级信息 因为一个班级id对应了一个年级id所有不用把年级id再和班级id一起查了
					if (clazzFlag) {

						WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id AND g.grade_class_id=c.grade_class_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId}");
						// 按年级查询专业信息
					} else if (majorFlag) {

						WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id AND g.grade_class_id=c.grade_class_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND j.major_id=#{majorId} AND g.grade_class_id=#{gradeClassId}");

						// 按年级查询院系信息
					} else if (depFlag) {

						WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id AND g.grade_class_id=c.grade_class_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND  d.dep_id=#{universityDepId} AND g.grade_class_id=#{gradeClassId}");
					}

					// 当年级为空时,不带年级条件查询
				} else {
					// 如果来的的班级不为空只查班级
					if (clazzFlag) {

						WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id AND g.grade_class_id=c.grade_class_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId}");
						// 如果带来的专业不为空只查专业
					} else if (majorFlag) {

						WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id AND g.grade_class_id=c.grade_class_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND j.major_id=#{majorId}");

						// 如果带来的年级不为空只查年级
					} else if (depFlag) {

						WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id AND g.grade_class_id=c.grade_class_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND  d.dep_id=#{universityDepId}");
					}

				}

				
			}
				
			}
			
		//分页 :由于mybatisProvider 在用where 拼接sql时会自动加括号 因为sql底层是一个字符串 所以自作聪明 在某尾加 了  分页   	
		}.toString()+"limit #{pageStarNum} , #{pageModel.pageSize} ";
	}
	
	
	/**
	 * 查询总记录数
	 * @param universityDepId
	 * @param majorId
	 * @param gradeClassId
	 * @param clazzId
	 * @return
	 */
	public static String buildSelectStudentMsgTotalNum(
			@Param("universityDepId")final String universityDepId, @Param("majorId")final String majorId,  @Param("gradeClassId")final String gradeClassId, @Param("clazzId")final String clazzId) {
		return new SQL() {
			{
				SELECT("count(*) ");
				FROM(" student t ,clazz c,major j,university_dep d,grade_class g ");

				// 判断院系是否为空
				boolean depFlag = universityDepId != null && !"".equals(universityDepId);
				// 判断专业是否为空
				boolean majorFlag = majorId != null && !"".equals(majorId);
				// 判断班级是否为空
				boolean clazzFlag = clazzId != null && !"".equals(clazzId);
				// 判断年级是否为空
				boolean gradeClassFlag = gradeClassId != null && !"".equals(gradeClassId);

			
				//当院系和年级为空没有条件查询
				if(!depFlag&&!gradeClassFlag){
					
					WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  ");	
					
					
				}else if(!depFlag&&gradeClassFlag){
					WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%') AND g.grade_class_id=#{gradeClassId} ");	
					
				}else{
				
				
				// 当年级不为空时，带年级条件查询
				if (gradeClassFlag) {

					// 按年级查询班级信息 因为一个班级id对应了一个年级id所有不用把年级id再和班级id一起查了
					if (clazzFlag) {

						WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id AND g.grade_class_id=c.grade_class_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId}");
						// 按年级查询专业信息
					} else if (majorFlag) {

						WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id AND g.grade_class_id=c.grade_class_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND j.major_id=#{majorId} AND g.grade_class_id=#{gradeClassId}");

						// 按年级查询院系信息
					} else if (depFlag) {

						WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id AND g.grade_class_id=c.grade_class_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND  d.dep_id=#{universityDepId} AND g.grade_class_id=#{gradeClassId}");
					}

					// 当年级为空时,不带年级条件查询
				} else {
					// 如果来的的班级不为空只查班级
					if (clazzFlag) {

						WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id AND g.grade_class_id=c.grade_class_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId}");
						// 如果带来的专业不为空只查专业
					} else if (majorFlag) {

						WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id AND g.grade_class_id=c.grade_class_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND j.major_id=#{majorId}");

						// 如果带来的年级不为空只查年级
					} else if (depFlag) {

						WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id AND g.grade_class_id=c.grade_class_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND  d.dep_id=#{universityDepId}");
					}

				}

				
			}
		}
			
	
		}.toString();
	}
	
	/**
	 * 导出excel
	 * 
	 * @param name
	 * @return
	 */
	public static String deriveExceltStudentMsg(
			@Param("universityDepId")final String universityDepId, @Param("majorId")final String majorId,  @Param("gradeClassId")final String gradeClassId, @Param("clazzId")final String clazzId) {
		return new SQL() {
			{
				SELECT("t.stu_id,t.stu_name,t.sex,t.phone,t.nation,c.class_id,c.class_name,j.major_id,j.major_name,g.grade_class_id,g.grade_class_name,d.dep_id,d.dep_name ");
				FROM(" student t ,clazz c,major j,university_dep d,grade_class g ");

				// 判断院系是否为空
				boolean depFlag = universityDepId != null && !"".equals(universityDepId);
				// 判断专业是否为空
				boolean majorFlag = majorId != null && !"".equals(majorId);
				// 判断班级是否为空
				boolean clazzFlag = clazzId != null && !"".equals(clazzId);
				// 判断年级是否为空
				boolean gradeClassFlag = gradeClassId != null && !"".equals(gradeClassId);

			
				//当院系和年级为空没有条件查询
				if(!depFlag&&!gradeClassFlag){
					
					WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  ");	
					
					
				}else if(!depFlag&&gradeClassFlag){
					WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%') AND g.grade_class_id=#{gradeClassId} ");	
					
				}else{
				
				
				// 当年级不为空时，带年级条件查询
				if (gradeClassFlag) {

					// 按年级查询班级信息 因为一个班级id对应了一个年级id所有不用把年级id再和班级id一起查了
					if (clazzFlag) {

						WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id AND g.grade_class_id=c.grade_class_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId}");
						// 按年级查询专业信息
					} else if (majorFlag) {

						WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id AND g.grade_class_id=c.grade_class_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND j.major_id=#{majorId} AND g.grade_class_id=#{gradeClassId}");

						// 按年级查询院系信息
					} else if (depFlag) {

						WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id AND g.grade_class_id=c.grade_class_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND  d.dep_id=#{universityDepId} AND g.grade_class_id=#{gradeClassId}");
					}

					// 当年级为空时,不带年级条件查询
				} else {
					// 如果来的的班级不为空只查班级
					if (clazzFlag) {

						WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id AND g.grade_class_id=c.grade_class_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId}");
						// 如果带来的专业不为空只查专业
					} else if (majorFlag) {

						WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id AND g.grade_class_id=c.grade_class_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND j.major_id=#{majorId}");

						// 如果带来的年级不为空只查年级
					} else if (depFlag) {

						WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id AND g.grade_class_id=c.grade_class_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND  d.dep_id=#{universityDepId}");
					}

				}

				
			}
				
			}
			
		
		}.toString();
	}
	

	/**
	 * Excel导入数据 插入多条数据
	 * @param studentList
	 * @return
	 */
	 
	public static String buildInsertStudentMsgList(Map map) {
		List<Student> urlBlack = (List<Student>) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO student ");
        sb.append("(stu_id, stu_name, class_id, sex,nation,phone) ");
        sb.append("VALUES ");
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].stuId},#'{'list[{0}].stuName},#'{'list[{0}].classId},#'{'list[{0}].sex},#'{'list[{0}].nation},#'{'list[{0}].phone})");
        for (int i = 0; i < urlBlack.size(); i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < urlBlack.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();


	
	}
}
