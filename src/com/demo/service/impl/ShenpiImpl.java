package com.demo.service.impl;

import java.util.List;

import com.demo.common.model.Dep;
import com.demo.common.model.Shenpi;
import com.demo.common.model.User;
import com.jfinal.plugin.activerecord.Page;

public interface ShenpiImpl {

	public Page<Shenpi> paginate(int pageNumber, int pageSize);
	
	public List<Shenpi> selectAll();
}
