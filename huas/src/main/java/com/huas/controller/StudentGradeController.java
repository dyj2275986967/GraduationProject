package com.huas.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huas.entities.Grade;
import com.huas.entities.GradeClass;
import com.huas.entities.Student;
import com.huas.entities.UniversityDep;
import com.huas.entities.User;
import com.huas.exception.ErrorReturnPageException;
import com.huas.serviceImpl.GradeServiceImpl;
import com.huas.utils.ConstantUtils;
import com.huas.utils.PageModel;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/student/grade")
public class StudentGradeController {
   
	@Autowired
	private  GradeServiceImpl  service;
	
	@RequestMapping(value = "/list")
	public String loginInStuGrade(String courseId,String stuId,String universityDepId,  String majorId,  String gradeClassId,  String clazzId ,String keywords,PageModel pageModel,String pageIndex,HttpServletResponse response){
		
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
         String sId="";
         String couId="";
         
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
         if (stuId != null && !"".equals(stuId)) {
        	 sId=stuId;      	
 		 }
         if (courseId != null && !"".equals(courseId)) {
        	 couId=courseId;      	
 		 }
         request.setAttribute("list",  service.findAllStuGradeMsg( key,pageModel,uDepId,mId,gId,cId,couId,sId));
 
         //存相关信息
         request.setAttribute("pageModel", pageModel);
     	
         request.setAttribute("pageIndex", pageIndex);
         
     	 request.setAttribute("keywords", key);
         
     	 request.setAttribute("universityDepId", uDepId);
         
       	 request.setAttribute("majorId", mId);
         
       	 request.setAttribute("gradeClassId", gId);
         
       	 request.setAttribute("clazzId", cId);
       	 
         request.setAttribute("stuId", sId);
         
       	 request.setAttribute("courseId", couId);

		
		return "stugradeM";
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
	
		service.findAllStuGradeMsgExportExcel(keywords, pageModel, universityDepId, majorId, gradeClassId, clazzId, courseId, stuId, response);
		
		
		
	}
	
	   /**接收上传的文件
	 * @throws IOException 
	 * @throws BiffException */
 @RequestMapping("/stuMsg_To_Lead")
 public String upLoad(@RequestParam(value="filename")MultipartFile file) {
 	
 	List<Grade> list=new ArrayList();
 	
 	// 1.获取用户上传的文件
     Workbook workbook;
		try {
			workbook = Workbook.getWorkbook(file.getInputStream());
			
		      // 2.获取工作簿sheet
	        Sheet sheet = workbook.getSheet(0);
	        // 3.获取总行数
	        int rows = sheet.getRows();
	       
	        for (int i = 1; i < rows; i++) {
	        	Grade s=new Grade();
	            s.setStuId(sheet.getCell(0, i).getContents());
	            s.setStuName(sheet.getCell(1, i).getContents());
	            s.setSex(sheet.getCell(2, i).getContents());
                s.setDepName(sheet.getCell(3, i).getContents());
	            s.setMajorName(sheet.getCell(4, i).getContents());
	            s.setClassName(sheet.getCell(5, i).getContents()); 
	            s.setCourseId(Integer.valueOf(sheet.getCell(7, i).getContents())); 
	            s.setClassName(sheet.getCell(6, i).getContents()); 
	            s.setCourseName(sheet.getCell(8, i).getContents()); 
	            s.setResult(Integer.valueOf(sheet.getCell(9, i).getContents())); 
    //	     
//	            // 4.添加到数据库中
	           list.add(s);
	        }
	        
	        //调用服务层
	      service.buildInsertGradeMsgList(list);
	     // 5.关闭资源
	        workbook.close();
			
		} catch (Exception e) {
		throw new ErrorReturnPageException("重复导入数据或没有上传文件");
		
		} 
 
 
  return "redirect:/student/grade/list";
 

 }
 
 /**
  * 根据联合主建删除学生信息
  * @param stuId
  * @return
  */
 @RequestMapping("/delete")
 public String deleteBYstudentId(String stuId,String courseId){
 	
 	service.deleteStudentGradeById(stuId,Integer.valueOf(courseId));
 	
 	  return "redirect:/student/grade/list";
 }
 
	/**
	 * 添加按钮相关
	 * @return
	 */
	@RequestMapping(value = "/addShow")
	@ResponseBody
	public Object addShowStudentMsg(){
		List<UniversityDep>  lists=	service.findAllStuUniversityMsg();
		
		List<GradeClass>  gradeClasslists=	service.findGradeClazzMsgByGradeClazzId();		
		
	
 	
		 JSONObject json = new JSONObject();
//	     	 Gson gson = new Gson();
	      Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	    
	        
	      Map <String ,Object> map=new HashMap<>();
	        
	       map.put("msg", lists);
	       map.put("gradeClassmsg", gradeClasslists);
	  
	    
	  
		String j= gson.toJson(map);
  
	return j;
	}
    /**
     * 添加成绩信息
     * @param stuId 学生id
     * @param courseId 课程id
     * @return
     */
	@RequestMapping(value="/add")
	public String addGradeMsg(String result,String stuId,String courseId){
	try{
		
		service.insertGradeMsg(Integer.valueOf(result),stuId,Integer.valueOf(courseId));
	}catch(Exception e){
		throw new ErrorReturnPageException("没有正确输入学号或课程号，或者重复导入数据");
	}
		
      
	
	return "redirect:/student/grade/list";
	}
	
	
	@RequestMapping(value="/updatess")
	@ResponseBody
	public String updateGradeMsg(String result,String stuId,String courseId){


		try{
			
			service.updateGradeResult(Integer.valueOf(result), stuId, Integer.valueOf(courseId));
			
			
		} catch (Exception e) {
			throw new ErrorReturnPageException("重复导入数据:数据重复");
		}
		
	
      
	
	return "添加成功";
	}
	
	
	
	
	
}
