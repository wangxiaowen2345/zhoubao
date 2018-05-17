package com.demo.service;


import java.util.List;

import com.demo.common.model.User;
import com.demo.service.impl.UserImpl;
import com.jfinal.plugin.activerecord.Page;

public class UserService implements UserImpl{

	public Page<User> paginate(int pageNumber, int pageSize) {
		return User.dao.paginate(pageNumber, pageSize, "select *", " from user order by depid");
	}
	
	public List<User> selectAll(){
		return User.dao.find("select * from user");
			
	}
	
	public User selectById(String id){
		return User.dao.findById(id);
			
	}


}
