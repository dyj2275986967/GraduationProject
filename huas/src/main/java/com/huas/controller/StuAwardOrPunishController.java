package com.huas.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.huas.entities.User;
import com.huas.serviceImpl.StuAwardOrPunishServiceImpl;
import com.huas.serviceImpl.StudentMessageServiceImpl;
import com.huas.utils.ConstantUtils;
import com.huas.utils.PageModel;

@Controller
@RequestMapping("/student/AwardOrPunish")
public class StuAwardOrPunishController {
	
	
	@Autowired
	private  StuAwardOrPunishServiceImpl  service;
	
	@RequestMapping("/index")
	public String deriveExceltStudentMsg(String universityDepId,  String majorId,  String gradeClassId,  String clazzId ,String keywords,PageModel pageModel,String pageIndex,HttpServletResponse response){
			
		HttpServletRequest request = ConstantUtils.getRequest();
	
		request.setAttribute("keywords", keywords);

      if (pageIndex != null && !"".equals(pageIndex)) {
			
			pageModel.setPageIndex(Integer.valueOf(pageIndex));
		}
   
         String key="";
         String uDepId="";
         String mId="";
         String gId="";
         String cId="";
         //不能为空判断开始
 
         if (keywords != null && !"".equals(keywords)) {
        	 key=keywords;      	
 		 }
         if (universityDepId != null && !"".equals(universityDepId)) {
        	 uDepId=universityDepId;      	
 		 }
         if (majorId != null && !"".equals(majorId)) {
        	 mId=majorId;      	
 		 }
         if (gradeClassId != null && !"".equals(gradeClassId)) {
        	 gId=gradeClassId;      	
 		 }
         if (clazzId != null && !"".equals(clazzId)) {
        	 cId=clazzId;      	
 		 }
         request.setAttribute("list",  service.findAllStuMsg( key,pageModel,uDepId,mId,gId,cId));
 
         //存相关信息
         request.setAttribute("pageModel", pageModel);
     	
         request.setAttribute("pageIndex", pageIndex);
         
     	 request.setAttribute("keywords", key);
         
     	 request.setAttribute("universityDepId", uDepId);
         
       	 request.setAttribute("majorId", mId);
         
       	 request.setAttribute("gradeClassId", gId);
         
       	 request.setAttribute("clazzId", cId);
		
		return "stuAwardOrPunish";	
		
		
	}
	
	/**
	 * 进入详情页面
	 * @return
	 */
	
	@RequestMapping("/details")
	public String  intoDetail(String stuId,String stuName){
		
		HttpServletRequest request = ConstantUtils.getRequest();
		// 在session里面查找用户信息
		User u = (User) request.getSession().getAttribute(ConstantUtils.SESSION_USER);

		
		request.setAttribute("stuId",stuId );
		request.setAttribute("stuName",stuName );
		request.setAttribute("list", service.findStuAwardOrPunishMsgByStuId(stuId));
      
		
		return "stuAwardOrPunishDetal";	
		
		
	}
	
	
	
	
	/**
	 * 插入
	 * @param stuId
	 * @param awadrs
	 * @param reason
	 * @param dataT
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public String insertStudentMsg(String stuId, String awadrs,  String reason, String dataT){

		 
        //获得SimpleDateFormat类，我们转换为yyyy-MM-dd的时间格式
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		
   
		try {
		     Date 	date = sf.parse(dataT);
			 service.insert(stuId, awadrs, reason, date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
		
	
		
		return "添加成功";	
		
		
	}
	
	/**
	 * 导出excel
	 * @param keywords
	 * @param pageModel
	 * @param universityDepId
	 * @param majorId
	 * @param gradeClassId
	 * @param clazzId
	 * @param response
	 */
	@RequestMapping("/stuMsg_export")
	@ResponseBody
	public void deriveExceltStudentMsg(String courseId,String stuId,String keywords,PageModel pageModel, String universityDepId,  String majorId,  String gradeClassId,  String clazzId,HttpServletResponse response){
	
		service.ExcelExport(response, keywords, pageModel, universityDepId, majorId, gradeClassId, clazzId);
		
		
		
	}
	
	//删除
	@RequestMapping("/delete")
	public ModelAndView  delete(String id,String name,String stuId){
		HttpServletRequest request = ConstantUtils.getRequest();
		ModelAndView mv = new ModelAndView();
		mv.addObject("stuId", id);
		mv.addObject("stuName", name);
	
		request.setAttribute("stuId",stuId );
		request.setAttribute("stuName",name );
		
		
		service.deleteAwardOrPunishByid(Integer.valueOf(id));
		 mv.setViewName("forward:/student/AwardOrPunish/details");
		
		return mv;
	}
	
	
	//更新
	@RequestMapping("/update")
	@ResponseBody
	public String update(String id,String dataT,String reason){
		
		
		   //获得SimpleDateFormat类，我们转换为yyyy-MM-dd的时间格式
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

   
		try {
		     Date 	date = sf.parse(dataT);
		     service.updateAwardOrPunishByid(Integer.valueOf(id), date, reason);
				
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		return "更新成功";
	}
	
	
	

}
