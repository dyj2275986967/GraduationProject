package com.huas.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class StuAwardOrPunishProvider {

	/**
	 * 模糊查询加条件搜索 加分页的动态sql
	 * 
	 * @param name
	 * @return
	 */
	public static String buildSelectstuAwardOrPunish(
			@Param("universityDepId")final String universityDepId, @Param("majorId")final String majorId,  @Param("gradeClassId")final String gradeClassId, @Param("clazzId")final String clazzId) {
		return new SQL() {
			{
				SELECT("count(awadrs) as 'num',s.`stu_id`,s.`stu_name`,s.`sex`,u.`dep_id`,u.`dep_name`,m.`major_id`,m.`major_name`,c.`class_id`,c.`class_name`,r.`reason`,r.`data_t`,r.`awadrs`,r.`id`,g.`grade_class_id`,g.`grade_class_name` ");
				FROM(" student s ,major m,clazz c,university_dep u,reward_or_punish r,grade_class g ");

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
					
					WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id` AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%')  ");	
					
					
				}else if(!depFlag&&gradeClassFlag){
					WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id`  AND  g.grade_class_id=c.grade_class_id AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%') AND g.grade_class_id=#{gradeClassId} ");	
					
				}else{
				
				
				// 当年级不为空时，带年级条件查询
				if (gradeClassFlag) {

					// 按年级查询班级信息 因为一个班级id对应了一个年级id所有不用把年级id再和班级id一起查了
					if (clazzFlag) {

						WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id` AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId}");
						// 按年级查询专业信息
					} else if (majorFlag) {

						WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id` AND s.stu_name LIKE  CONCAT('%',#{keywords},'%')   AND m.major_id=#{majorId} AND g.grade_class_id=#{gradeClassId}");

						// 按年级查询院系信息
					} else if (depFlag) {

						WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id` AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND  u.dep_id=#{universityDepId} AND g.grade_class_id=#{gradeClassId}");
					}

					// 当年级为空时,不带年级条件查询
				} else {
					// 如果来的的班级不为空只查班级
					if (clazzFlag) {

						WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id`  AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId}");
						// 如果带来的专业不为空只查专业
					} else if (majorFlag) {

						WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id`  AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND m.major_id=#{majorId}");

						// 如果带来的年级不为空只查年级
					} else if (depFlag) {

						WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id`  AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND  u.dep_id=#{universityDepId}");
					}

				}

				
			}
				
				GROUP_BY(" s.`stu_id`");
			}
		//分页 :由于mybatisProvider 在用where 拼接sql时会自动加括号 因为sql底层是一个字符串 所以自作聪明 在某尾加 了  分页   	
		}.toString()+"limit #{pageStarNum} , #{pageModel.pageSize} ";
	}
	
	
	/**
	 * 模糊查询加条件搜索 加分页的动态sql
	 * 
	 * @param name
	 * @return
	 */
	public static String buildfindTotalNum(
			@Param("universityDepId")final String universityDepId, @Param("majorId")final String majorId,  @Param("gradeClassId")final String gradeClassId, @Param("clazzId")final String clazzId) {
		return new SQL() {
			{
				SELECT(" COUNT(DISTINCT(s.`stu_id`)) ");
				FROM(" student s ,major m,clazz c,university_dep u,reward_or_punish r,grade_class g ");

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
					
					WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id` AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%')  ");	
					
					
				}else if(!depFlag&&gradeClassFlag){
					WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id`  AND  g.grade_class_id=c.grade_class_id AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%') AND g.grade_class_id=#{gradeClassId} ");	
					
				}else{
				
				
				// 当年级不为空时，带年级条件查询
				if (gradeClassFlag) {

					// 按年级查询班级信息 因为一个班级id对应了一个年级id所有不用把年级id再和班级id一起查了
					if (clazzFlag) {

						WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id` AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId}");
						// 按年级查询专业信息
					} else if (majorFlag) {

						WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id` AND s.stu_name LIKE  CONCAT('%',#{keywords},'%')   AND m.major_id=#{majorId} AND g.grade_class_id=#{gradeClassId}");

						// 按年级查询院系信息
					} else if (depFlag) {

						WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id` AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND  u.dep_id=#{universityDepId} AND g.grade_class_id=#{gradeClassId}");
					}

					// 当年级为空时,不带年级条件查询
				} else {
					// 如果来的的班级不为空只查班级
					if (clazzFlag) {

						WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id`  AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId}");
						// 如果带来的专业不为空只查专业
					} else if (majorFlag) {

						WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id`  AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND m.major_id=#{majorId}");

						// 如果带来的年级不为空只查年级
					} else if (depFlag) {

						WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id`  AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND  u.dep_id=#{universityDepId}");
					}

				}

				
			}
				
			}
			
		//分页 :由于mybatisProvider 在用where 拼接sql时会自动加括号 因为sql底层是一个字符串 所以自作聪明 在某尾加 了  分页   	
		}.toString();
	}

	
	/**
	 * Excel导出 
	 */
	public static String buildSelectstuAwardOrPunishExcelExport(
			@Param("universityDepId")final String universityDepId, @Param("majorId")final String majorId,  @Param("gradeClassId")final String gradeClassId, @Param("clazzId")final String clazzId) {
		return new SQL() {
			{
				SELECT("count(awadrs) as 'num',s.`stu_id`,s.`stu_name`,s.`sex`,u.`dep_id`,u.`dep_name`,m.`major_id`,m.`major_name`,c.`class_id`,c.`class_name`,r.`reason`,r.`data_t`,r.`awadrs`,r.`id`,g.`grade_class_id`,g.`grade_class_name` ");
				FROM(" student s ,major m,clazz c,university_dep u,reward_or_punish r,grade_class g ");

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
					
					WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id` AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%')  ");	
					
					
				}else if(!depFlag&&gradeClassFlag){
					WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id`  AND  g.grade_class_id=c.grade_class_id AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%') AND g.grade_class_id=#{gradeClassId} ");	
					
				}else{
				
				
				// 当年级不为空时，带年级条件查询
				if (gradeClassFlag) {

					// 按年级查询班级信息 因为一个班级id对应了一个年级id所有不用把年级id再和班级id一起查了
					if (clazzFlag) {

						WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id` AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId}");
						// 按年级查询专业信息
					} else if (majorFlag) {

						WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id` AND s.stu_name LIKE  CONCAT('%',#{keywords},'%')   AND m.major_id=#{majorId} AND g.grade_class_id=#{gradeClassId}");

						// 按年级查询院系信息
					} else if (depFlag) {

						WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id` AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND  u.dep_id=#{universityDepId} AND g.grade_class_id=#{gradeClassId}");
					}

					// 当年级为空时,不带年级条件查询
				} else {
					// 如果来的的班级不为空只查班级
					if (clazzFlag) {

						WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id`  AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%')  AND c.class_id=#{clazzId}");
						// 如果带来的专业不为空只查专业
					} else if (majorFlag) {

						WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id`  AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND m.major_id=#{majorId}");

						// 如果带来的年级不为空只查年级
					} else if (depFlag) {

						WHERE("s.`class_id`=c.`class_id` AND m.`major_id`=c.`major_id` AND u.`dep_id`=m.`dep_id` AND r.`stu_id`=s.`stu_id` AND g.`grade_class_id`=c.`grade_class_id`  AND s.stu_name  LIKE  CONCAT('%',#{keywords},'%')   AND  u.dep_id=#{universityDepId}");
					}

				}

				
			}
				
				GROUP_BY(" s.`stu_id`");
			}
		//分页 :由于mybatisProvider 在用where 拼接sql时会自动加括号 因为sql底层是一个字符串 所以自作聪明 在某尾加 了  分页   	
		}.toString();
	}
	
	
	

}
