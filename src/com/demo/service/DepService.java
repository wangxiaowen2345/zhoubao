package com.demo.service;


import java.util.List;

import com.demo.common.model.Dep;
import com.demo.common.model.User;
import com.demo.service.impl.DepImpl;
import com.jfinal.plugin.activerecord.Page;

public class DepService implements DepImpl{

	public Page<Dep> paginate(int pageNumber, int pageSize) {
		return Dep.dao.paginate(pageNumber, pageSize, "select *", " from dep");
	}

	public List<Dep> selectAll(){
		return Dep.dao.find("select * from dep");
			
	}


}
