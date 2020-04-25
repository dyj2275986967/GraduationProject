package com.huas.controller;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huas.entities.Clazz;
import com.huas.entities.GradeClass;
import com.huas.entities.Major;
import com.huas.entities.Student;
import com.huas.entities.UniversityDep;
import com.huas.entities.User;
import com.huas.exception.ErrorReturnPageException;
import com.huas.serviceImpl.StudentMessageServiceImpl;
import com.huas.utils.ConstantUtils;
import com.huas.utils.PageModel;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("/student/msg")
public class StudentMessageController {

	@Autowired
	private  StudentMessageServiceImpl  service;
	

	
	/**
	 * 用户进入主界面  分页 模糊查询加条件搜索
	 * @param pageModel
	 * @param pageIndex
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String loginInStuMsg(String universityDepId,  String majorId,  String gradeClassId,  String clazzId ,String keywords,PageModel pageModel,String pageIndex,HttpServletResponse response){
				
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
		return "stuMessageM";
		
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
	
	      Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		    
	        
	      Map <String ,Object> map=new HashMap<>();
	        
	       map.put("msg", lists);
	       map.put("gradeClassmsg", gradeClasslists);
	     
		
	   	String json= gson.toJson(map);
		
		

	return json;
	}
	
	
	
	/**
	 * 添加学生信息开始
	 * @param student
	 * @return
	 */
	
	@RequestMapping(value = "/insertStudent")
	@ResponseBody
	public String insertStudentMsg(Student student){
	      
		
			 service.insertStudentMsg(student);
		
			
	
			   return "forward:/student/msg/list";
	
	}
	
	
	
	/**
	 * 更新学生信息开始
	 * @param student
	 * @return
	 */
	
	@RequestMapping(value = "/updataStudent")
	@ResponseBody
	public String updataStudentMsg(Student student,String oldStuId){
	   
		try{
			
			service.updataStudentMsg(student,oldStuId);
			
			
		} catch (Exception e) {
			throw new ErrorReturnPageException("重复导入数据:数据重复");
		} 
		
	
		return "添加成功";
	}
  /**4
   *
   *导出excel
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
	public void deriveExceltStudentMsg(String keywords,PageModel pageModel, String universityDepId,  String majorId,  String gradeClassId,  String clazzId,HttpServletResponse response){
	
		service.deriveExceltStudentMsg(keywords, pageModel, universityDepId, majorId, gradeClassId, clazzId, response);
		
		
		
	}
	
	
	   /**接收上传的文件
	 * @throws IOException 
	 * @throws BiffException */
    @RequestMapping("/stuMsg_To_Lead")
    public String upLoad(@RequestParam(value="filename")MultipartFile file) {
    	
    	List<Student> list=new ArrayList();
    	
    	// 1.获取用户上传的文件
        Workbook workbook;
		try {
			workbook = Workbook.getWorkbook(file.getInputStream());
			
		      // 2.获取工作簿sheet
	        Sheet sheet = workbook.getSheet(0);
	        // 3.获取总行数
	        int rows = sheet.getRows();
	       
	        for (int i = 1; i < rows; i++) {
	         
	        	Student s=new Student();
	        	
	    
	            s.setStuId(sheet.getCell(0, i).getContents());
	            s.setStuName(sheet.getCell(1, i).getContents());
	            s.setSex(sheet.getCell(2, i).getContents());
	            s.setNation(sheet.getCell(3, i).getContents());
	            s.setPhone(sheet.getCell(4, i).getContents());
	            s.setClassName(sheet.getCell(5, i).getContents()); 
	     
	            // 4.添加到数据库中
	           list.add(s);
	        }
	        
	        //调用服务层
	        service.buildInsertStudentMsgList(list);
	     // 5.关闭资源
	        workbook.close();
			
		} catch (Exception e) {
			throw new ErrorReturnPageException("重复导入数据");
		} 

    
     return "redirect:/student/msg/list";
    
  
    }
    
    @RequestMapping("/delete")
    public String deleteBYstudentId(String stuId){
    	
    	service.deleteStudentBystuId(stuId);
    	
    	  return "redirect:/student/msg/list";
    }
	
	
	
}
