package com.huas.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.huas.entities.RewardOrPunish;
import com.huas.entities.Student;
import com.huas.provider.StudentProvider;
import com.huas.provider.StuAwardOrPunishProvider;
import com.huas.utils.PageModel;

public interface StuAwardOrPunishmMapper {
	
	
	/***********
	 * 分页相关 动态sql
	 **********
	 */

	// 查询所有的学生信息
   
	@SelectProvider(type = StuAwardOrPunishProvider.class, method = "buildSelectstuAwardOrPunish")
	  List<RewardOrPunish> findAllStuAwardOrPunishMsg(  @Param("keywords")String keywords,@Param("pageStarNum")Integer pageStarNum,@Param("pageModel")PageModel pageModel,@Param("universityDepId")String universityDepId,@Param("majorId")String majorId,@Param("gradeClassId")String gradeClassId,@Param("clazzId")String clazzId);
   //获取总记录数
	@SelectProvider(type = StuAwardOrPunishProvider.class, method = "buildfindTotalNum")
        int findTotalNum(
        	 @Param("keywords")String keywords,@Param("universityDepId")String universityDepId,@Param("majorId")String majorId,@Param("gradeClassId")String gradeClassId,@Param("clazzId")String clazzId);
	
	/***********
	 * 分页相关结束
	 **********
	 */
	
	//插入数据
	 @Insert("INSERT INTO reward_or_punish (stu_id,awadrs,reason,data_t) VALUES(#{stuId},#{awadrs},#{reason},#{dataT})")
	 int insertMsg(@Param("stuId")String stuId,@Param("awadrs")String awadrs,@Param("reason")String reason,@Param("dataT")java.util.Date dataT);
   //excel导出
	 @SelectProvider(type = StuAwardOrPunishProvider.class, method = "buildSelectstuAwardOrPunishExcelExport")
	  List<RewardOrPunish> findAllStuAwardOrPunishMsgExcelExport(  @Param("keywords")String keywords,@Param("pageStarNum")Integer pageStarNum,@Param("pageModel")PageModel pageModel,@Param("universityDepId")String universityDepId,@Param("majorId")String majorId,@Param("gradeClassId")String gradeClassId,@Param("clazzId")String clazzId);
    //通过学号查找奖罚 
	 @Select("select  id,stu_id,awadrs,reason, DATE_FORMAT(data_t,'%Y-%m-%d') AS'data_t'   from reward_or_punish where stu_id=#{stuId} ")
	  List<RewardOrPunish> findStuAwardOrPunishMsgByStuId(@Param("stuId")String stuId);
	 //通过奖罚id修改奖罚信息
	 @Update("UPDATE reward_or_punish SET reason=#{reason},data_t=#{data} WHERE id=#{id} ")
	  void updateAwardOrPunishByid(@Param("id")Integer id,@Param("data")java.util.Date data,@Param("reason")String reason);
	 //通过奖罚id删除奖罚信息
	 @Delete("DELETE  FROM reward_or_punish WHERE id=#{id}")
	 void deleteAwardOrPunishByid(@Param("id")Integer id);
}
