package com.huas.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.huas.entities.Major;
import com.huas.entities.UniversityDep;
import com.huas.entities.User;
import com.huas.mapper.AdmisterUserMapper;
import com.huas.service.AdmisterUserService;
import com.huas.utils.ConstantUtils;
import com.huas.utils.PageModel;

@Service
public class AdmisterUserServiceImpl implements AdmisterUserService {

	@Autowired
	private AdmisterUserMapper mapper;

	/**
	 * 插入账号
	 */
	@Override
	public void insertUserMsg(String loginName, String pwd, Integer num, Integer level, Integer id) {

		
			List<User> list = new ArrayList();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

			// Date date =sfrmat(System.currentTimeMillis());

			Date date = new Date();
			
			
			
			
			String login ="";

			for (int i = 1; i <= num; i++) {
				User u = new User();
				 //如果num==1的话  账号后面就不用拼接序号
				if(num==1){
					
					login= loginName;	
					
				}else{
				 login = loginName + "0" + String.valueOf(i);
				}
         
				u.setLoginName(login);
				u.setPwd(pwd);
				u.setLevel(level);
				u.setManageId(id);
				u.setCreateTime(date);

				list.add(u);
			}
			mapper.buildInsertStudentMsgList(list);


	}

	@Override
	public List<User> selectAllUserMsg(PageModel pageModel,Integer id,Integer level) {
		//如果是超级管理员
		if(level==0){
			int num=mapper.findSelectAllMsgByAdminNum();
			pageModel.setTotalNum(num);
			return mapper.selectAllMsgByAdmin(pageModel.getStarNum(),pageModel);
	    //如果是一级管理员
		}else if(level==1){
			
			int num=mapper.findselectAllMsgByManager(id);
			pageModel.setTotalNum(num);
			return mapper.selectAllMsgByManager(id,pageModel.getStarNum(),pageModel);
		//如果是二级管理员
		}else{
			return null;
		}
	
		
		
		
		
		
		
	}
  
	@Override
	public void deleteUserByUserId(Integer id) {
	 
		 mapper.deleteUserByUserId(id);
		
	}

	@Override
	public List<UniversityDep> findAllDep(PageModel pageModel) {

		int num=mapper.findAllDepNum();
		pageModel.setTotalNum(num);

		return mapper.findAllDep(pageModel.getStarNum(),pageModel);
	}

	@Override
	public void deleteDep(String depId) {
		mapper.deleteDep(depId);
		
	}

	@Override
	public void insertDep(String depName) {
		mapper.insertDep(depName);
		
	}

	@Override
	public void updateDep(String depName, String depId) {
		mapper.updateDep(depName, depId);
		
	}

	@Override
	public List<Major> findAllMajor(Integer depId, PageModel pageModel) {
		 
		int num=mapper.findAllMajorNum(depId);
		pageModel.setTotalNum(num);
		
		
		
		
		return mapper.findAllMajor(depId, pageModel.getStarNum(), pageModel);

	}

	@Override
	public void insertMajor(String majorName,String depId) {
		mapper.insertMajor(majorName,Integer.valueOf(depId));
		

		
	}

	@Override
	public void updateMajor(String majorName, Integer majorId) {
	   mapper.updateMajor(majorName, majorId);
	}

	
}
