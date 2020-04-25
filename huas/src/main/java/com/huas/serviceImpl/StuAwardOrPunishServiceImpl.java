package com.huas.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huas.entities.Grade;
import com.huas.entities.RewardOrPunish;
import com.huas.mapper.StuAwardOrPunishmMapper;
import com.huas.service.StuAwardOrPunishService;
import com.huas.utils.PageModel;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Service
public class StuAwardOrPunishServiceImpl implements StuAwardOrPunishService {

	@Autowired
	 private StuAwardOrPunishmMapper mapper;
	
	
	
	@Override
	public List<RewardOrPunish> findAllStuMsg(String keywords, PageModel pageModel, String universityDepId, String majorId,
			String gradeClassId, String clazzId) {
		

		// 获取总记录数
		int totalNum = mapper.findTotalNum(keywords, universityDepId, majorId,
				gradeClassId, clazzId);

		pageModel.setTotalNum(totalNum);
				 
	
		
		return mapper.findAllStuAwardOrPunishMsg(keywords, pageModel.getStarNum(), pageModel, universityDepId, majorId, gradeClassId, clazzId);
		
	
	
	
	}



	@Override
	public void insert(String stuId, String awadrs, String reason, Date dataT) {
		
		
		 mapper.insertMsg(stuId, awadrs, reason, dataT);
		
	}



	@Override
	public void ExcelExport(HttpServletResponse response,String keywords, PageModel pageModel, String universityDepId, String majorId,
			String gradeClassId, String clazzId) {
		
		
		List<RewardOrPunish>  list=	 mapper.findAllStuAwardOrPunishMsgExcelExport(keywords, pageModel.getStarNum(), pageModel, universityDepId, majorId, gradeClassId, clazzId);
		
				
				
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
			sheet.addCell(new Label(7, 0, "奖罚数量", format));			
		  // 导入行
			for (int i = 0, j = 1; i < list.size(); i++, j++) {
				
				RewardOrPunish reward=list.get(i);
 				// 设置列宽
				sheet.setColumnView(i, 16);
				// 重新设置部分列宽
			
				// 设置行高
				sheet.setRowView(i, 350);
				// 设置字体的attribute
				WritableFont font1 = new WritableFont(WritableFont.createFont("楷体 _GB2312"), 12, WritableFont.NO_BOLD);
				WritableFont font2 = new WritableFont(WritableFont.createFont("楷体 _GB2312"), 12,
					     WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					     jxl.format.Colour.CORAL); 
				WritableCellFormat format1 = new WritableCellFormat(font1);
				WritableCellFormat format2 = new WritableCellFormat(font2);

				sheet.addCell(new Label(0, j, reward.getStuId(), format1));
				sheet.addCell(new Label(1, j, reward.getStuName(), format1));
				sheet.addCell(new Label(2, j, reward.getSex(), format1));
				sheet.addCell(new Label(3, j, reward.getDepName(), format1));

				sheet.addCell(new Label(4, j, reward.getMajorName(), format1));
				sheet.addCell(new Label(5, j, reward.getClassName(), format1));
				sheet.addCell(new Label(6, j, reward.getGradeClassName(), format1));
				sheet.addCell(new Label(7, j,String.valueOf(reward.getNum()) , format1));				
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


	//通过学生id查询奖罚信息
	@Override
	public  List<RewardOrPunish> findStuAwardOrPunishMsgByStuId(String stuId) {
		
		List<RewardOrPunish> list=	mapper.findStuAwardOrPunishMsgByStuId(stuId);
		
		System.out.println(list);
		return list;
		
	}



	@Override
	public void updateAwardOrPunishByid(Integer id, Date data, String reason) {
		
		mapper.updateAwardOrPunishByid(id, data, reason);
		
	}



	@Override
	public void deleteAwardOrPunishByid(Integer id) {
		 mapper.deleteAwardOrPunishByid(id);
		
	}

}
