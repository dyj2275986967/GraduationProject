package com.huas.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.huas.entities.UniversityDep;
import com.huas.mapper.ManagerUserMsgMapper;
import com.huas.service.ManagerUserMsgService;
import com.huas.utils.ConstantUtils;

@Service
public class ManagerUserMsgServiceImpl implements ManagerUserMsgService {
	
	@Autowired
	private ManagerUserMsgMapper mapper;
	
	
	 public List<UniversityDep> findDepMsg() {
		 return  mapper.findDepMsg();
		
	}


	@Override
	public void updateMsg(String sex,String name, String dep, String signature, Integer userId) {
		mapper.updateMsg(sex,name, dep, signature, userId);
		
	}


	//头像上传
	public String updUserProfile(MultipartFile headImage) {

		return"";
		
	}

	
	
	
	
}
