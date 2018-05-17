package com.demo.service.impl;

import java.util.List;

import com.demo.common.model.Dep;
import com.demo.common.model.User;
import com.jfinal.plugin.activerecord.Page;

public interface DepImpl {

	public Page<Dep> paginate(int pageNumber, int pageSize);
	
	public List<Dep> selectAll();
}
