package com.huas.serviceImpl;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huas.entities.Grade;
import com.huas.entities.GradeClass;
import com.huas.entities.GradeDetail;
import com.huas.entities.GradeDetailWithTable;
import com.huas.entities.Student;
import com.huas.entities.UniversityDep;
import com.huas.mapper.GradeMapper;
import com.huas.mapper.StudentMapper;
import com.huas.service.GradeService;
import com.huas.utils.PageModel;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Service
public class GradeServiceImpl implements GradeService{
	
	@Autowired
	private GradeMapper mapper;
	
	/**
	 * 分页查询 条件搜索,查询
	 */
	
	@Override
	public List<Grade> findAllStuGradeMsg(String keywords, PageModel pageModel, String universityDepId, String majorId,
			String gradeClassId, String clazzId,String courseId,String stuId) {
	
		
		// 获取总记录数
				int totalNum = mapper.findTotalNum(keywords,  pageModel.getStarNum(), pageModel, universityDepId, majorId, gradeClassId, clazzId, courseId, stuId);
			    

				pageModel.setTotalNum(totalNum);

				return mapper.findAllStuMsg(keywords, pageModel.getStarNum(), pageModel, universityDepId, majorId, gradeClassId, clazzId, courseId, stuId);
						
	}
    /**
     * 
     */
	@Override
	public void findAllStuGradeMsgExportExcel(String keywords,PageModel pageModel, String universityDepId,  String majorId,  String gradeClassId,  String clazzId,String courseId,String stuId,HttpServletResponse response) {
	
		// 查询要导入的学生数据
				List<Grade> gradeList = mapper.findAllStuMsgExportExcel(keywords, pageModel.getStarNum(), pageModel, universityDepId, majorId, gradeClassId, clazzId, courseId, stuId);
				WritableWorkbook workbook;
				// 1.文件下载响应头
				//response.setHeader("Content-Disposition", "attachment;filename=studentMessage.xls");
				String fileName="学生成绩表";
			
				// 2.响应到浏览器
				
				try {
					fileName = new String(fileName.getBytes(),"iso-8859-1");
					response.setCharacterEncoding("gb2312");response.reset();
					response.setContentType("application/OCTET-STREAM;charset=gb2312");
					response.setHeader("pragma", "no-cache");
					response.addHeader("Content-Disposition", "attachment;filename=\""+ fileName + ".xls\"");// 点击导出excle按钮时候页面显示的默认名称
					workbook = Workbook.createWorkbook(response.getOutputStream());
					// 创建工作簿sheet
					WritableSheet sheet = workbook.createSheet("学生成绩表", 0);
					// 设置字体的attribute
					WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.NO_BOLD);
					WritableCellFormat format = new WritableCellFormat(font);
					// 3.设置column名
					sheet.addCell(new Label(0, 0, "学号", format));
					sheet.addCell(new Label(1, 0, "姓名", format));
					sheet.addCell(new Label(2, 0, "性别", format));
					sheet.addCell(new Label(3, 0, "院系", format));
					sheet.addCell(new Label(4, 0, "专业", format));
					sheet.addCell(new Label(5, 0, "班级", format));
					sheet.addCell(new Label(6, 0, "年级", format));
					sheet.addCell(new Label(7, 0, "课程号", format));
					sheet.addCell(new Label(8, 0, "课程名", format));
					sheet.addCell(new Label(9, 0, "成绩", format));
					// 导入行
					for (int i = 0, j = 1; i < gradeList.size(); i++, j++) {
						Grade grade = gradeList.get(i);

						// 设置列宽
						sheet.setColumnView(i, 16);
						// 重新设置部分列宽
						sheet.setColumnView(3, 14);
						sheet.setColumnView(6, 10);
						sheet.setColumnView(7, 10);
						// 设置行高
						sheet.setRowView(i, 350);
						// 设置字体的attribute
						WritableFont font1 = new WritableFont(WritableFont.createFont("楷体 _GB2312"), 12, WritableFont.NO_BOLD);
						WritableFont font2 = new WritableFont(WritableFont.createFont("楷体 _GB2312"), 12,
							     WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
							     jxl.format.Colour.CORAL); 
						WritableCellFormat format1 = new WritableCellFormat(font1);
						WritableCellFormat format2 = new WritableCellFormat(font2);

						sheet.addCell(new Label(0, j, grade.getStuId(), format1));
						sheet.addCell(new Label(1, j, grade.getStuName(), format1));
						sheet.addCell(new Label(2, j, grade.getSex(), format1));
						sheet.addCell(new Label(3, j, grade.getDepName(), format1));

						sheet.addCell(new Label(4, j, grade.getMajorName(), format1));
						sheet.addCell(new Label(5, j, grade.getClassName(), format1));
						sheet.addCell(new Label(6, j, grade.getGradeClassName(), format1));
						sheet.addCell(new Label(7, j,String.valueOf(grade.getCourseId()) , format1));	
						sheet.addCell(new Label(8, j,grade.getCourseName(), format1));
						
						//如果成绩大于等于60 显黑色
						if(grade.getResult()>=60){
							sheet.addCell(new Label(9, j,String.valueOf(grade.getResult()) , format1));	
							
						//如果成绩小于60显示红色	
						}else{
							sheet.addCell(new Label(9, j,String.valueOf(grade.getResult()) , format2));	
							
						}
						
						
						

					}

					// 5.写入数据
					workbook.write();
					// 6.关闭资源
					workbook.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		
		
		
	}
	@Override
	public void buildInsertGradeMsgList(List<Grade> list) {
		
		
	
	mapper.buildInsertGradeList(list);
		
		
		
	}
	@Override
	public void deleteStudentGradeById(String stuId,Integer courseId) {
	 
		mapper.deleteStudentGradeById(stuId, courseId);
		
	}
	@Override
	public List<UniversityDep> findAllStuUniversityMsg() {
		
		return mapper.findAllUniversitys();
	}
	@Override
	public List<GradeClass> findGradeClazzMsgByGradeClazzId() {
	
	    return mapper.findGradeClazzMsgByGradeClazzId();
	}
	/**
	 * 插入学生信息
	 */
	@Override
	public void insertGradeMsg(Integer result,String stuId, Integer courseId) {
		mapper.insertIntoGrade(result,stuId, courseId);
		
	}
	@Override
	public void updateGradeResult(Integer result, String stuId, Integer courseId) {
		mapper.updateGradeResult(result, stuId, courseId);
	}
	@Override
	public List<GradeDetail> buildStudentGradeMsgDetailsPassOrNotPass() {
		return	mapper.buildStudentGradeMsgDetailsPassOrNotPass();
	
	}
	@Override
	public List<GradeDetail> buildStudentGradeMsgDetailsNotPass() {
		return	mapper.buildStudentGradeMsgDetailsNotPass();
	}
	@Override
	public List<GradeDetailWithTable> findStudentAndCourseAndResult(PageModel pageModel ,List<String> courseName,String majorId,
			String gradeClassId, String clazzId) {
	 
		int totalNum= mapper.buildStudentGradeDetialsTotalNum(courseName, majorId, gradeClassId, clazzId);
		
	
		
		pageModel.setTotalNum(totalNum);
		
		return mapper.findStudentAndCourseAndResult(pageModel.getStarNum(),pageModel,courseName, majorId, gradeClassId, clazzId);
	}
	@Override
	public List<String> findCourseNameByMajorId(String majorId) {
		
		return mapper.findCourseNameByMajorId(Integer.valueOf(majorId));
	}
	/**
	 * 导出excel：成绩管理详情
	 */
	@Override
	public void buildStudentGradeExportExcel( String majorId, String gradeClassId,
			String clazzId,HttpServletResponse response) {
		//表头
		List<String> courseName=	mapper.findCourseNameByMajorId(Integer.valueOf(majorId));		
		
		List<GradeDetailWithTable> list=   mapper.buildStudentGradeExportExcel(courseName, majorId, gradeClassId, clazzId);
		
		
		WritableWorkbook workbook;
		// 1.文件下载响应头
		//response.setHeader("Content-Disposition", "attachment;filename=studentMessage.xls");
		String fileName="学生成绩详情表";
			// 2.响应到浏览器
		
		try {
			fileName = new String(fileName.getBytes(),"iso-8859-1");
			response.setCharacterEncoding("gb2312");response.reset();
			response.setContentType("application/OCTET-STREAM;charset=gb2312");
			response.setHeader("pragma", "no-cache");
			response.addHeader("Content-Disposition", "attachment;filename=\""+ fileName + ".xls\"");// 点击导出excle按钮时候页面显示的默认名称
			workbook = Workbook.createWorkbook(response.getOutputStream());
			// 创建工作簿sheet
			WritableSheet sheet = workbook.createSheet("学生成绩表", 0);
			// 设置字体的attribute
			WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.NO_BOLD);
			WritableCellFormat format = new WritableCellFormat(font);
			// 3.设置column名
			
			 sheet.addCell(new Label(0, 0,"学号", format));
				
			 sheet.addCell(new Label(1, 0, "姓名", format));
				
			 
			for(int i=0;i<courseName.size();i++){
				System.out.println(courseName.get(i));
				//遍历表头
		      sheet.addCell(new Label(2+i, 0, courseName.get(i), format));
			
			}
			
			sheet.addCell(new Label(courseName.size()+2, 0, "班级", format));

			//准备 好方法名
			List<String> letter =new ArrayList();
        	String str = "ABCDEFGHIJKLMNPQRSTUVWXYZ";
        	 
        	for(int i=0;i<courseName.size();i++){
        		StringBuffer bf=new StringBuffer();
        		//拼接get方法的名字
        		bf.append("get").append(String.valueOf(str.charAt(i)));
        		letter.add(bf.toString());
        	}
			// 导入行
			for (int i = 0, j = 1; i < list.size(); i++, j++) {
	
			// 设置列宽
				sheet.setColumnView(i, 16);
				// 重新设置部分列宽
				
				sheet.setColumnView(courseName.size()+2, 20);
				// 设置行高
				sheet.setRowView(i, 350);
				// 设置字体的attribute
				WritableFont font1 = new WritableFont(WritableFont.createFont("楷体 _GB2312"), 12, WritableFont.NO_BOLD);
				WritableFont font2 = new WritableFont(WritableFont.createFont("楷体 _GB2312"), 12,
					     WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					     jxl.format.Colour.CORAL); 
				WritableCellFormat format1 = new WritableCellFormat(font1);
				WritableCellFormat format2 = new WritableCellFormat(font2);
	
				
				GradeDetailWithTable g = list.get(i);
				Class<?> c = g.getClass();
				
				//获取该类的所有方法
				Method[]   method=    c.getMethods();
				
				sheet.addCell(new Label(0, j,g.getStuId(), format1));
				sheet.addCell(new Label(1, j,g.getStuName(), format1));
				
		  	 
				for(int z=0;z<method.length;z++){

					for(int n=0;n<letter.size();n++){
						//如果方法名相同就执行get方法
	
						if(method[z].getName().equals(letter.get(n))){
							
					
					
							//如果成绩大于等于60 显黑色
							if((Integer)method[z].invoke(g)>=60){
								sheet.addCell(new Label(2+n, j,String.valueOf(method[z].invoke(g)) , format1));	
								
							//如果成绩小于60显示红色	
							}else{
								sheet.addCell(new Label(2+n, j,String.valueOf(method[z].invoke(g)) , format2));		
								
							}						
							
							
							
						}
						
						
					}
				}
				sheet.addCell(new Label(courseName.size()+2, j,g.getClassName(), format1));


			}

			// 5.写入数据
			workbook.write();
			// 6.关闭资源
			workbook.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
		
		
		
		
		
		
		
		
	}

}
