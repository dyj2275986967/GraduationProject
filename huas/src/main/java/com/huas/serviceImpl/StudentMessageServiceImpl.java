package com.huas.serviceImpl;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huas.entities.Clazz;
import com.huas.entities.GradeClass;
import com.huas.entities.Student;
import com.huas.entities.UniversityDep;
import com.huas.mapper.StudentMapper;
import com.huas.service.StudentMessageService;
import com.huas.utils.PageModel;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Service
public class StudentMessageServiceImpl implements StudentMessageService {

	@Autowired
	private StudentMapper mapper;

	// 数据分页，模糊查询,条件查询
	@Override
	public List<Student> findAllStuMsg(String keywords, PageModel pageModel, String universityDepId, String majorId,
			String gradeClassId, String clazzId) {
		// 获取总记录数
		int totalNum = mapper.findTotalNum(keywords, pageModel.getStarNum(), pageModel, universityDepId, majorId,
				gradeClassId, clazzId);

		pageModel.setTotalNum(totalNum);

		return mapper.findAllStuMsg(keywords, pageModel.getStarNum(), pageModel, universityDepId, majorId, gradeClassId,
				clazzId);
	}

	// 查学院 班级 专业信息
	@Override
	public List<UniversityDep> findAllStuUniversityMsg() {

		return mapper.findAllUniversitys();
	}

	// 查年级专业信息
	@Override
	public List<GradeClass> findGradeClazzMsgByGradeClazzId() {
		// TODO Auto-generated method stub
		return mapper.findGradeClazzMsgByGradeClazzId();
	}

	// 添加学生信息
	@Override
	public void insertStudentMsg(Student stu) {

		mapper.insertStuMsg(stu);

	}

	// 更新学生信息
	@Override
	public void updataStudentMsg(Student stu, String oldStuId) {

		mapper.updataStuMsg(stu, oldStuId);

	}

	@Override
	public void deriveExceltStudentMsg(String keywords, PageModel pageModel, String universityDepId, String majorId,
			String gradeClassId, String clazzId, HttpServletResponse response) {
		// 查询要导入的学生数据
		List<Student> stuList = mapper.deriveExceltStudentMsg(keywords, pageModel.getStarNum(), pageModel,
				universityDepId, majorId, gradeClassId, clazzId);
		WritableWorkbook workbook;
		// 1.文件下载响应头
		//response.setHeader("Content-Disposition", "attachment;filename=studentMessage.xls");
		String fileName="学生信息表";
	
		// 2.响应到浏览器
		
		try {
			fileName = new String(fileName.getBytes(),"iso-8859-1");
			response.setCharacterEncoding("gb2312");response.reset();
			response.setContentType("application/OCTET-STREAM;charset=gb2312");
			response.setHeader("pragma", "no-cache");
			response.addHeader("Content-Disposition", "attachment;filename=\""+ fileName + ".xls\"");// 点击导出excle按钮时候页面显示的默认名称
			workbook = Workbook.createWorkbook(response.getOutputStream());
			// 创建工作簿sheet
			WritableSheet sheet = workbook.createSheet("学生信息表", 0);
			// 设置字体的attribute
			WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.NO_BOLD);
			WritableCellFormat format = new WritableCellFormat(font);
			// 3.设置column名
			sheet.addCell(new Label(0, 0, "学号", format));
			sheet.addCell(new Label(1, 0, "姓名", format));
			sheet.addCell(new Label(2, 0, "性别", format));
			sheet.addCell(new Label(3, 0, "民族", format));
			sheet.addCell(new Label(4, 0, "手机号", format));
			sheet.addCell(new Label(5, 0, "班级", format));
			sheet.addCell(new Label(6, 0, "专业", format));
			sheet.addCell(new Label(7, 0, "院系", format));
			sheet.addCell(new Label(8, 0, "年级", format));
			// 导入行
			for (int i = 0, j = 1; i < stuList.size(); i++, j++) {
				Student stuMsg = stuList.get(i);

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
				WritableCellFormat format1 = new WritableCellFormat(font1);

				sheet.addCell(new Label(0, j, stuMsg.getStuId(), format1));
				sheet.addCell(new Label(1, j, stuMsg.getStuName(), format1));
				sheet.addCell(new Label(2, j, stuMsg.getSex(), format1));
				sheet.addCell(new Label(3, j, stuMsg.getNation(), format1));

				sheet.addCell(new Label(4, j, stuMsg.getPhone(), format1));
				sheet.addCell(new Label(5, j, stuMsg.getClassName(), format1));
				sheet.addCell(new Label(6, j, stuMsg.getMajorName(), format1));

				sheet.addCell(new Label(7, j, stuMsg.getDepName(), format1));
				sheet.addCell(new Label(8, j, stuMsg.getGradeClassName(), format1));

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
	public void buildInsertStudentMsgList(List<Student> list) {

		List<Clazz> clazzList = mapper.findAllClazz();

		for (Clazz clazz : clazzList) {

			for (Student student : list) {

				if (clazz.getClassName().equals(student.getClassName())) {
                    student.setClassId(clazz.getClassId());
				}

			}

		}

		mapper.buildInsertStudentMsgList(list);

	}

	@Override
	public void deleteStudentBystuId(String stuId) {
		mapper.deleteStudentMsgById(stuId);
		
	}

}
