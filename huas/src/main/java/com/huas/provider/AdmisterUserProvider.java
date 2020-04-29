package com.huas.provider;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.huas.entities.Student;
import com.huas.entities.User;

/**
 * 账号管理动态sql
 * @author 500R5H
 *
 */
public class AdmisterUserProvider {

	
	
	
	
	
	
	
	
	/**
	 *动态添加账号
	 * @param studentList
	 * @return
	 */
	 
	public static String buildInsertUserMsgList(Map map) {
		List<User> urlBlack = (List<User>) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO user ");
        sb.append("(login_name, pwd,level,manage_id,create_time) ");
        sb.append("VALUES ");
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].loginName},#'{'list[{0}].pwd},#'{'list[{0}].level},#'{'list[{0}].manageId},#'{'list[{0}].createTime})");
        for (int i = 0; i < urlBlack.size(); i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < urlBlack.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();

	}
	
	
	
	
	
}
