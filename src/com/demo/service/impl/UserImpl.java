package com.demo.service.impl;

import java.util.List;

import com.demo.common.model.User;
import com.jfinal.plugin.activerecord.Page;

public interface UserImpl {

	public Page<User> paginate(int pageNumber, int pageSize);
	
	public List<User> selectAll();
	
	public User selectById(String id);
}
