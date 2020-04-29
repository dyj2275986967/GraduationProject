package com.huas.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.huas.entities.User;
import com.huas.serviceImpl.AdmisterUserServiceImpl;
import com.huas.utils.ConstantUtils;
import com.huas.utils.PageModel;

@Controller
@RequestMapping("/user/admin")
public class AdmisterUserController {
	@Autowired
	private AdmisterUserServiceImpl service;

	// 用户进入主页面
	@RequestMapping(value = "/index")
	public String loginIn(PageModel pageModel, String pageIndex) {

		User u = (User) ConstantUtils.getRequest().getSession().getAttribute("session_user");

		if (pageIndex != null && !"".equals(pageIndex)) {

			pageModel.setPageIndex(Integer.valueOf(pageIndex));
		}

		List<User> lists = service.selectAllUserMsg(pageModel, u.getUserId(), u.getLevel());

		if (lists != null) {

			ConstantUtils.getRequest().setAttribute("lists", lists);

		}
		// 存相关信息
		ConstantUtils.getRequest().setAttribute("pageModel", pageModel);

		ConstantUtils.getRequest().setAttribute("pageIndex", pageIndex);

		return "admin";
	}

	// 增加账号
	@RequestMapping(value = "/addLogin")
	public String add(String loginName, String pwd, String num, String level, String id) {

		service.insertUserMsg(loginName, pwd, Integer.valueOf(num), Integer.valueOf(level), Integer.valueOf(id));

		return "redirect:/user/admin/index";
	}

	@RequestMapping(value = "/delete")
	public String delete(String id) {

		service.deleteUserByUserId(Integer.valueOf(id));

		return "redirect:/user/admin/index";

	}

	// 查询学院信息展示
	@RequestMapping(value = "/index/dep")
	public String dep(PageModel pageModel, String pageIndex) {

		

		if (pageIndex != null && !"".equals(pageIndex)) {

			pageModel.setPageIndex(Integer.valueOf(pageIndex));
		}
		
		ConstantUtils.getRequest().setAttribute("list", service.findAllDep(pageModel));

		// 存相关信息
		ConstantUtils.getRequest().setAttribute("pageModel", pageModel);

		ConstantUtils.getRequest().setAttribute("pageIndex", pageIndex);

		return "dep";

	}

	// 删除学院信息
	@RequestMapping(value = "/index/dep/delete")
	public String deleteDep() {

		return "redirect:/user/admin/index/dep";

	}

	// 添加学院信息
	@RequestMapping(value = "/index/dep/add")
	public String addDep(String depName) {

		service.insertDep(depName);
		return "redirect:/user/admin/index/dep";

	}

	// 修改学院信息
	@RequestMapping(value = "/index/dep/update")
	public String updateDep(String depName, String depId) {

		service.updateDep(depName, depId);

		return "redirect:/user/admin/index/dep";

	}
	// 查询专业信息展示
		@RequestMapping(value = "/index/major")
		public String major(String depId,PageModel pageModel, String pageIndex) {
			

			if (pageIndex != null && !"".equals(pageIndex)) {

				pageModel.setPageIndex(Integer.valueOf(pageIndex));
			}
			

			ConstantUtils.getRequest().setAttribute("list", service.findAllMajor(Integer.valueOf(depId), pageModel));

			// 存相关信息
			ConstantUtils.getRequest().setAttribute("pageModel", pageModel);

			ConstantUtils.getRequest().setAttribute("pageIndex", pageIndex);
			ConstantUtils.getRequest().setAttribute("depId", depId);
			
			return "major";

		}
		// 添加专业信息
		@RequestMapping(value = "/index/major/add")
		@ResponseBody
		public String addMajor(String depId,String majorName) {

			service.insertMajor(majorName,depId);
			
			
			return "添加成功";
			

		}
		//修改专业信息
		@RequestMapping(value = "/index/major/update")
		@ResponseBody
		public String updateMajor(String majorId,String majorName) {

			service.updateMajor(majorName,Integer.valueOf(majorId) );
			
			
			return "修改dsfaddddddddddddddd成功";
			

		}

}
