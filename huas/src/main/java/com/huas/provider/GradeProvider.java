package com.huas.provider;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import com.huas.entities.ChooseCourse;
import com.huas.entities.Grade;
import com.huas.entities.Student;

/**
 * 信息管理动态sql
 * 
 * @author 500R5H
 *
 */
public class GradeProvider {

	/**
	 * 模糊查询加条件搜索 加分页的动态sql
	 * 
	 * @param name
	 * @return
	 */
	public static String buildSelectStudentGradeMsg(@Param("universityDepId") final String universityDepId,
			@Param("majorId") final String majorId, @Param("gradeClassId") final String gradeClassId,
			@Param("clazzId") final String clazzId, @Param("courseId") final String courseId,
			@Param("stuId") final String stuId) {
		return new SQL() {
			{
				SELECT("gr.result,gr.course_id,co.course_name ,t.stu_id,t.stu_name,t.sex,c.class_id,c.class_name,j.major_id,j.major_name,g.grade_class_id,g.grade_class_name,d.dep_id,d.dep_name ");
				FROM(" student t ,clazz c,major j,university_dep d,grade_class g ,course co,grade gr");

				// 判断院系是否为空
				boolean depFlag = universityDepId != null && !"".equals(universityDepId);
				// 判断专业是否为空
				boolean majorFlag = majorId != null && !"".equals(majorId);
				// 判断班级是否为空
				boolean clazzFlag = clazzId != null && !"".equals(clazzId);
				// 判断年级是否为空
				boolean gradeClassFlag = gradeClassId != null && !"".equals(gradeClassId);
				// 判断课程是否为空
				boolean courseIdFlag = courseId != null && !"".equals(courseId);
				// 判断学号是否为空
				boolean stuIdFlag = courseId != null && !"".equals(stuId);

				// 当院系和年级为空没有条件查询
				if (!depFlag && !gradeClassFlag) {

					WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name   LIKE  CONCAT('%',#{keywords},'%')  ");

				} else if (!depFlag && gradeClassFlag) {
					WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name   LIKE  CONCAT('%',#{keywords},'%') AND g.grade_class_id=#{gradeClassId}  ");

				} else {

					// 当年级不为空时，带年级条件查询
					if (gradeClassFlag) {

						// 按年级查询班级信息 因为一个班级id对应了一个年级id所有不用把年级id再和班级id一起查了
						if (clazzFlag) {
							// 如果学号不为空 课程不为空
							if (courseIdFlag && stuIdFlag) {

								WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId} AND t.stu_id=#{stuId} AND co.course_id=#{courseId}");
								// 如果学号不为空
							} else if (stuIdFlag) {
								WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId} AND t.stu_id=#{stuId} ");
								// 如果课程不为空
							} else if (courseIdFlag) {

								WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId}  AND co.course_id=#{courseId}");
							} else {

								WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId}");

							}

							// 按年级查询专业信息
						} else if (majorFlag) {
							// 如果课程不为空 课程专业一起查
							if (courseIdFlag) {

								WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND j.major_id=#{majorId} AND g.grade_class_id=#{gradeClassId}  AND co.course_id=#{courseId}");
							} else {

								WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name    LIKE  CONCAT('%',#{keywords},'%')   AND j.major_id=#{majorId} AND g.grade_class_id=#{gradeClassId}");

							}

							// 按年级查询院系信息
						} else if (depFlag) {

							WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND  d.dep_id=#{universityDepId} AND g.grade_class_id=#{gradeClassId}");
						}

						// 当年级为空时,班级不可能带信息过来 所有不查班级
					} else {
						if (majorFlag) {

							// 如果课程不为空 课程专业一起查
							if (courseIdFlag) {

								WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND j.major_id=#{majorId}   AND co.course_id=#{courseId}");
							} else {

								WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id    AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND j.major_id=#{majorId} ");

							}

						} else if (depFlag) {

							WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name    LIKE  CONCAT('%',#{keywords},'%')   AND  d.dep_id=#{universityDepId}");
						}

					}

				}

			}

			// 分页 :由于mybatisProvider 在用where 拼接sql时会自动加括号 因为sql底层是一个字符串 所以自作聪明
			// 在某尾加 了 分页
		}.toString() + "limit #{pageStarNum} , #{pageModel.pageSize} ";
	}

	/**
	 * 查询总记录数
	 * 
	 * @param name
	 * @return
	 */
	public static String buildSelectStudentGradeMsgTotalNum(@Param("universityDepId") final String universityDepId,
			@Param("majorId") final String majorId, @Param("gradeClassId") final String gradeClassId,
			@Param("clazzId") final String clazzId, @Param("courseId") final String courseId,
			@Param("stuId") final String stuId) {
		return new SQL() {
			{
				SELECT("count(*)");
				FROM(" student t ,clazz c,major j,university_dep d,grade_class g ,course co,grade gr");

				// 判断院系是否为空
				boolean depFlag = universityDepId != null && !"".equals(universityDepId);
				// 判断专业是否为空
				boolean majorFlag = majorId != null && !"".equals(majorId);
				// 判断班级是否为空
				boolean clazzFlag = clazzId != null && !"".equals(clazzId);
				// 判断年级是否为空
				boolean gradeClassFlag = gradeClassId != null && !"".equals(gradeClassId);
				// 判断课程是否为空
				boolean courseIdFlag = courseId != null && !"".equals(courseId);
				// 判断学号是否为空
				boolean stuIdFlag = courseId != null && !"".equals(stuId);

				// 当院系和年级为空没有条件查询
				if (!depFlag && !gradeClassFlag) {

					WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name   LIKE  CONCAT('%',#{keywords},'%')  ");

				} else if (!depFlag && gradeClassFlag) {
					WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name   LIKE  CONCAT('%',#{keywords},'%') AND g.grade_class_id=#{gradeClassId}  ");

				} else {

					// 当年级不为空时，带年级条件查询
					if (gradeClassFlag) {

						// 按年级查询班级信息 因为一个班级id对应了一个年级id所有不用把年级id再和班级id一起查了
						if (clazzFlag) {

							// 如果学号不为空 课程不为空
							if (courseIdFlag && stuIdFlag) {

								WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId} AND t.stu_id=#{stuId} AND co.course_id=#{courseId}");
								// 如果学号不为空
							} else if (stuIdFlag) {
								WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId} AND t.stu_id=#{stuId} ");
								// 如果课程不为空
							} else if (courseIdFlag) {

								WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId}  AND co.course_id=#{courseId}");
							} else {

								WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId}");

							}

							// 按年级查询专业信息
						} else if (majorFlag) {

							WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id   AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND j.major_id=#{majorId} AND g.grade_class_id=#{gradeClassId}");

							// 按年级查询院系信息
						} else if (depFlag) {

							WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND  d.dep_id=#{universityDepId} AND g.grade_class_id=#{gradeClassId}");
						}

						// 当年级为空时,不带年级条件查询
					} else {
						// 如果来的的班级不为空只查班级
						if (clazzFlag) {

							WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId}");
							// 如果带来的专业不为空只查专业
						} else if (majorFlag) {

							WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND j.major_id=#{majorId}");

							// 如果带来的年级不为空只查年级
						} else if (depFlag) {

							WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name    LIKE  CONCAT('%',#{keywords},'%')   AND  d.dep_id=#{universityDepId}");
						}

					}

				}

			}

			// 分页 :由于mybatisProvider 在用where 拼接sql时会自动加括号 因为sql底层是一个字符串 所以自作聪明
			// 在某尾加 了 分页
		}.toString();
	}

	/**
	 * excel导出
	 * 
	 * @param name
	 * @return
	 */
	public static String buildSelectStudentGradeMsgExport(@Param("universityDepId") final String universityDepId,
			@Param("majorId") final String majorId, @Param("gradeClassId") final String gradeClassId,
			@Param("clazzId") final String clazzId, @Param("courseId") final String courseId,
			@Param("stuId") final String stuId) {
		return new SQL() {
			{
				SELECT("gr.result,gr.course_id,co.course_name ,t.stu_id,t.stu_name,t.sex,c.class_id,c.class_name,j.major_id,j.major_name,g.grade_class_id,g.grade_class_name,d.dep_id,d.dep_name ");
				FROM(" student t ,clazz c,major j,university_dep d,grade_class g ,course co,grade gr");

				// 判断院系是否为空
				boolean depFlag = universityDepId != null && !"".equals(universityDepId);
				// 判断专业是否为空
				boolean majorFlag = majorId != null && !"".equals(majorId);
				// 判断班级是否为空
				boolean clazzFlag = clazzId != null && !"".equals(clazzId);
				// 判断年级是否为空
				boolean gradeClassFlag = gradeClassId != null && !"".equals(gradeClassId);
				// 判断课程是否为空
				boolean courseIdFlag = courseId != null && !"".equals(courseId);
				// 判断学号是否为空
				boolean stuIdFlag = courseId != null && !"".equals(stuId);

				// 当院系和年级为空没有条件查询
				if (!depFlag && !gradeClassFlag) {

					WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name   LIKE  CONCAT('%',#{keywords},'%')  ");

				} else if (!depFlag && gradeClassFlag) {
					WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name   LIKE  CONCAT('%',#{keywords},'%') AND g.grade_class_id=#{gradeClassId}  ");

				} else {

					// 当年级不为空时，带年级条件查询
					if (gradeClassFlag) {

						// 按年级查询班级信息 因为一个班级id对应了一个年级id所有不用把年级id再和班级id一起查了
						if (clazzFlag) {
							// 如果学号不为空 课程不为空
							if (courseIdFlag && stuIdFlag) {

								WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId} AND t.stu_id=#{stuId} AND co.course_id=#{courseId}");
								// 如果学号不为空
							} else if (stuIdFlag) {
								WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId} AND t.stu_id=#{stuId} ");
								// 如果课程不为空
							} else if (courseIdFlag) {

								WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId}  AND co.course_id=#{courseId}");
							} else {

								WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId}");

							}

							// 按年级查询专业信息
						} else if (majorFlag) {
							// 如果课程不为空 课程专业一起查
							if (courseIdFlag) {

								WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND j.major_id=#{majorId} AND g.grade_class_id=#{gradeClassId}  AND co.course_id=#{courseId}");
							} else {

								WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name    LIKE  CONCAT('%',#{keywords},'%')   AND j.major_id=#{majorId} AND g.grade_class_id=#{gradeClassId}");

							}

							// 按年级查询院系信息
						} else if (depFlag) {

							WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND  d.dep_id=#{universityDepId} AND g.grade_class_id=#{gradeClassId}");
						}

						// 当年级为空时,班级不可能带信息过来 所有不查班级
					} else {
						if (majorFlag) {

							// 如果课程不为空 课程专业一起查
							if (courseIdFlag) {

								WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND j.major_id=#{majorId}   AND co.course_id=#{courseId}");
							} else {

								WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id    AND t.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND j.major_id=#{majorId} ");

							}

						} else if (depFlag) {

							WHERE("t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id  AND  g.grade_class_id=c.grade_class_id  AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id  AND t.stu_name    LIKE  CONCAT('%',#{keywords},'%')   AND  d.dep_id=#{universityDepId}");
						}

					}

				}

			}

			// 分页 :由于mybatisProvider 在用where 拼接sql时会自动加括号 因为sql底层是一个字符串 所以自作聪明
			// 在某尾加 了 分页
		}.toString();
	}

	/**
	 * Excel导入数据 插入多条数据:
	 * 
	 * @param studentList
	 * @return
	 */

	public static String buildInsertGradeMsgList(Map map) {
		List<Grade> urlBlack = (List<Grade>) map.get("list");
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO grade ");
		sb.append("(stu_id,course_id, result) ");
		sb.append("VALUES ");
		MessageFormat mf = new MessageFormat("(#'{'list[{0}].stuId},#'{'list[{0}].courseId},#'{'list[{0}].result})");
		for (int i = 0; i < urlBlack.size(); i++) {
			sb.append(mf.format(new Object[] { i }));
			if (i < urlBlack.size() - 1) {
				sb.append(",");
			}
		}
		return sb.toString();

	}

	/**
	 * 详情页面展示 sql 成功合格和不合格 信息
	 * 
	 * @return
	 */

	public static String buildStudentGradeMsgDetailsPassOrNotPass() {
		return new SQL() {
			{
				SELECT("SUM(CASE WHEN gr.`result`>=60 THEN 1 ELSE 0 END) AS pass,SUM(CASE WHEN gr.`result`>=60 THEN 0 ELSE 1 END) AS notpass,d.`dep_name` AS NAME");
				FROM(" student t ,clazz c,major j,university_dep d,grade_class g ,course co,grade gr");

				WHERE(" t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id AND g.grade_class_id=c.grade_class_id AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id ");

				GROUP_BY("d.`dep_id`");

			}
		}.toString();
	}

	/**
	 * 详情页面展示 sql 成功不合格
	 * 
	 * @return
	 */

	public static String buildStudentGradeMsgDetailsbNotPass() {
		return new SQL() {
			{
				SELECT("COUNT(*) AS 'value',d.dep_name AS 'name'");
				FROM(" student t ,clazz c,major j,university_dep d,grade_class g ,course co,grade gr");

				WHERE(" t.class_id=c.class_id AND c.major_id=j.major_id AND j.dep_id=d.dep_id AND g.grade_class_id=c.grade_class_id AND t.stu_id=gr.stu_id AND co.course_id=gr.course_id AND gr.`result`<60");

				GROUP_BY("d.`dep_id`");

			}
		}.toString();
	}

	/**
	 * 详情展示相关开始：根据 marjorId ,classId,gradeClassId 查询学生的成绩
	 * 
	 * @return
	 */
	public static String buildStudentGradeDetials(@Param("courseName") final List<String> courseName,@Param("majorId") final String majorId, @Param("gradeClassId") final String gradeClassId,
			@Param("clazzId") final String clazzId) {
		// 判断专业是否为空
	//	boolean majorFlag = majorId != null && !"".equals(majorId);
		// 判断班级是否为空
		boolean clazzFlag = clazzId != null && !"".equals(clazzId);
		// 判断年级是否为空
		boolean gradeClassFlag = gradeClassId != null && !"".equals(gradeClassId);
		StringBuilder sb = new StringBuilder();
		//定义这个是为了呼应 GradeDetailWithTable 实体类 中定义 的存放 数据库的 属性 
		  String str = "ABCDEFGHIJKLMNPQRSTUVWXYZ".toLowerCase();
		   
		
		sb.append("SELECT  s.stu_id,s.stu_name , ");
		// {0}占位符 当'{0} ={0} 失效 当 ''{0}'' 时 就相当于 'asd' 
		for (int i = 0; i < courseName.size(); i++) {
			sb.append(MessageFormat.format("SUM(CASE WHEN co.course_name=''{0}'' THEN g.result ELSE 0 END) AS '"+str.charAt(i)+"'",
					courseName.get(i)));
			if (i < courseName.size() - 1) {
				sb.append(",");
			}
		}
         //后面一定要有空格  否则 会报错
		sb.append(", z.class_name ");
		
		sb.append("FROM  grade g,student s,major m ,clazz z,course co ,grade_class gc ");

		// 判断年级是否为空
		// 如果有年级 就带年级查
		if (gradeClassFlag) {

			// 判断班级有没有
			if (clazzFlag) {
				sb.append(
						"WHERE g.course_id=co.course_id AND s.class_id=z.class_id AND m.major_id=co.major_id AND s.stu_id=g.stu_id AND gc.grade_class_id=z.grade_class_id AND z.class_id=#{clazzId} ");
			} else {

				sb.append(
						"WHERE g.course_id=co.course_id AND s.class_id=z.class_id AND m.major_id=co.major_id AND s.stu_id=g.stu_id AND gc.grade_class_id=z.grade_class_id AND m.major_id=#{majorId}   AND gc.grade_class_id=#{gradeClassId}");

			}

			// 不带年级查询
		} else {
			sb.append(
					"WHERE g.course_id=co.course_id AND s.class_id=z.class_id AND m.major_id=co.major_id AND s.stu_id=g.stu_id AND gc.grade_class_id=z.grade_class_id AND m.major_id=#{majorId} ");

		}

		sb.append(" GROUP BY s.stu_id  ");

		sb.append(" limit #{pageStarNum} , #{pageModel.pageSize} ");
		
		
		return sb.toString();

	}
	
	/**
	 * 详情展示相关开始：查询总记录数
	 * 
	 * @return
	 */
	public static String buildStudentGradeDetialsTotalNum(@Param("courseName") final List<String> courseName,@Param("majorId") final String majorId, @Param("gradeClassId") final String gradeClassId,
			@Param("clazzId") final String clazzId) {
		// 判断专业是否为空
	//	boolean majorFlag = majorId != null && !"".equals(majorId);
		// 判断班级是否为空
		boolean clazzFlag = clazzId != null && !"".equals(clazzId);
		// 判断年级是否为空
		boolean gradeClassFlag = gradeClassId != null && !"".equals(gradeClassId);
		StringBuilder sb = new StringBuilder();
		//定义这个是为了呼应 GradeDetailWithTable 实体类 中定义 的存放 数据库的 属性 
		  String str = "ABCDEFGHIJKLMNPQRSTUVWXYZ".toLowerCase();
		   
		
		sb.append("SELECT   COUNT(DISTINCT s.stu_id) ");
	
		
		sb.append("FROM  grade g,student s,major m ,clazz z,course co ,grade_class gc ");

		// 判断年级是否为空
		// 如果有年级 就带年级查
		if (gradeClassFlag) {

			// 判断班级有没有
			if (clazzFlag) {
				sb.append(
						"WHERE g.course_id=co.course_id AND s.class_id=z.class_id AND m.major_id=co.major_id AND s.stu_id=g.stu_id AND gc.grade_class_id=z.grade_class_id AND z.class_id=#{clazzId} ");
			} else {

				sb.append(
						"WHERE g.course_id=co.course_id AND s.class_id=z.class_id AND m.major_id=co.major_id AND s.stu_id=g.stu_id AND gc.grade_class_id=z.grade_class_id AND m.major_id=#{majorId}   AND gc.grade_class_id=#{gradeClassId}");

			}

			// 不带年级查询
		} else {
			sb.append(
					"WHERE g.course_id=co.course_id AND s.class_id=z.class_id AND m.major_id=co.major_id AND s.stu_id=g.stu_id AND gc.grade_class_id=z.grade_class_id AND m.major_id=#{majorId} ");

		}

	
		
		
		
		return sb.toString();

	}
	
	
	/**
	 * 详情展示相关开始：根据 marjorId ,classId,gradeClassId 查询学生的成绩
	 * 
	 * @return
	 */
	public static String buildStudentGradeExportExcel(@Param("courseName") final List<String> courseName,@Param("majorId") final String majorId, @Param("gradeClassId") final String gradeClassId,
			@Param("clazzId") final String clazzId) {
		// 判断专业是否为空
	//	boolean majorFlag = majorId != null && !"".equals(majorId);
		// 判断班级是否为空
		boolean clazzFlag = clazzId != null && !"".equals(clazzId);
		// 判断年级是否为空
		boolean gradeClassFlag = gradeClassId != null && !"".equals(gradeClassId);
		StringBuilder sb = new StringBuilder();
		//定义这个是为了呼应 GradeDetailWithTable 实体类 中定义 的存放 数据库的 属性 
		  String str = "ABCDEFGHIJKLMNPQRSTUVWXYZ".toLowerCase();
		   
		
		sb.append("SELECT  s.stu_id,s.stu_name , ");
		// {0}占位符 当'{0} ={0} 失效 当 ''{0}'' 时 就相当于 'asd' 
		for (int i = 0; i < courseName.size(); i++) {
			sb.append(MessageFormat.format("SUM(CASE WHEN co.course_name=''{0}'' THEN g.result ELSE 0 END) AS '"+str.charAt(i)+"'",
					courseName.get(i)));
			if (i < courseName.size() - 1) {
				sb.append(",");
			}
		}
         //后面一定要有空格  否则 会报错
		sb.append(", z.class_name ");
		
		sb.append("FROM  grade g,student s,major m ,clazz z,course co ,grade_class gc ");

		// 判断年级是否为空
		// 如果有年级 就带年级查
		if (gradeClassFlag) {

			// 判断班级有没有
			if (clazzFlag) {
				sb.append(
						"WHERE g.course_id=co.course_id AND s.class_id=z.class_id AND m.major_id=co.major_id AND s.stu_id=g.stu_id AND gc.grade_class_id=z.grade_class_id AND z.class_id=#{clazzId} ");
			} else {

				sb.append(
						"WHERE g.course_id=co.course_id AND s.class_id=z.class_id AND m.major_id=co.major_id AND s.stu_id=g.stu_id AND gc.grade_class_id=z.grade_class_id AND m.major_id=#{majorId}   AND gc.grade_class_id=#{gradeClassId}");

			}

			// 不带年级查询
		} else {
			sb.append(
					"WHERE g.course_id=co.course_id AND s.class_id=z.class_id AND m.major_id=co.major_id AND s.stu_id=g.stu_id AND gc.grade_class_id=z.grade_class_id AND m.major_id=#{majorId} ");

		}
		
		sb.append(" GROUP BY s.stu_id  ");
		
		
		return sb.toString();

	}
	

	
	
	
	
	
	
	
	
	

}
