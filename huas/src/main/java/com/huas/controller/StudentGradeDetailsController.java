package com.huas.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huas.entities.GradeClass;
import com.huas.entities.GradeDetail;
import com.huas.entities.UniversityDep;
import com.huas.entities.User;
import com.huas.exception.ErrorReturnPageException;
import com.huas.serviceImpl.GradeServiceImpl;
import com.huas.utils.ConstantUtils;
import com.huas.utils.PageModel;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/student/grade/detail")
public class StudentGradeDetailsController {
	
	@Autowired
	private  GradeServiceImpl  service;
	
	/**
	 *  进入图表展示页面
	 * @param result
	 * @param stuId
	 * @param courseId
	 * @return
	 */
	
	@RequestMapping(value="/datails")
	public String intostugradeDetal(){
		
		HttpServletRequest request = ConstantUtils.getRequest();
	
	

		
		
	return "stugradeDetal";
	}
	
	/**
	 * 和图标展示相关 ：查询成绩合格和不合格信息
	 * @return
	 */
	
	@RequestMapping(value = "/showMsg")
	@ResponseBody
	public Object addShowStudentMsg(){
		//NotPass 不合格
		List<GradeDetail>  notPass=	service.buildStudentGradeMsgDetailsNotPass();
		//合格
		List<GradeDetail>  pass=	service.buildStudentGradeMsgDetailsPassOrNotPass();		
		
	
 	
		 JSONObject json = new JSONObject();
//	     	 Gson gson = new Gson();
	      Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	    
	        
	      Map <String ,Object> map=new HashMap<>();
	        
	       map.put("notPass", notPass);
	       map.put("passOrNotPass", pass);
	  
	    
	  
		String j= gson.toJson(map);
  
	return j;
	}
	
	
	
	
	
	/**
	 * 表格详情展示页面 进入主页面
	 */

	@RequestMapping(value="/detailsWithTable")
	public String intostugradeDetalWithTable(String clazzId,  String majorId,String gradeClassId,PageModel pageModel,String pageIndex){
		
		HttpServletRequest request = ConstantUtils.getRequest();
	
		String mId="";
        String gId="";
        String cId="";

        //不能为空判断开始    
        if (pageIndex != null && !"".equals(pageIndex)) {
			
    			pageModel.setPageIndex(Integer.valueOf(pageIndex));
    		}
       
        if (gradeClassId != null && !"".equals(gradeClassId)) {
       	 gId=gradeClassId;      	
		 }
        if (clazzId != null && !"".equals(clazzId)) {
       	 cId=clazzId;      	
		 }
        if (majorId != null && !"".equals(majorId)) {
        	
                  mId=majorId;
        	
        	//查询所有的专业下的课程信息 
        	List<String> courseName =service.findCourseNameByMajorId(majorId); 
        
        	List<String> letter =new ArrayList();
        	String str = "ABCDEFGHIJKLMNPQRSTUVWXYZ".toLowerCase();
        	 
        	for(int i=0;i<courseName.size();i++){
        		
        		letter.add(String.valueOf(str.charAt(i)));
        	 }

        	//存信息
        	//用于动态生成学生信息
         	request.setAttribute("letter",letter);
            //表头
          	request.setAttribute("courseName",courseName);
            //存相关信息
            request.setAttribute("pageModel", pageModel);
        	
            request.setAttribute("pageIndex", pageIndex);
            
          	 request.setAttribute("majorId", mId);
            
          	 request.setAttribute("gradeClassId", gId);
            
          	 request.setAttribute("clazzId", cId);
            
    		request.setAttribute("lists",service.findStudentAndCourseAndResult(pageModel,courseName,majorId, gId, cId));
    	
    	
    		
    		
   		 }
 
	return "stugradeDetalWithTable";
	
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
	public void deriveExceltStudentMsg( String majorId,  String gradeClassId,  String clazzId,HttpServletResponse response){
	

		
		service.buildStudentGradeExportExcel(majorId, gradeClassId, clazzId, response);
		
	}
	
	
	
	

}
