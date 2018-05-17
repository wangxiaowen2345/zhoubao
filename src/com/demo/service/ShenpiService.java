package com.demo.service;


import java.util.List;

import com.demo.common.model.Dep;
import com.demo.common.model.Liucheng;
import com.demo.common.model.Shenpi;
import com.demo.common.model.User;
import com.demo.service.impl.DepImpl;
import com.demo.service.impl.ShenpiImpl;
import com.jfinal.plugin.activerecord.Page;

public class ShenpiService implements ShenpiImpl{

	public Page<Shenpi> paginate(int pageNumber, int pageSize) {
		return Shenpi.dao.paginate(pageNumber, pageSize, "select *", " from shenpi order by state asc");
	}

	public List<Shenpi> selectAll(){
		return Shenpi.dao.find("select * from shenpi");
			
	}
	
	public List<Liucheng> selectLiucheng(String id){
		return Liucheng.dao.find("select * from liucheng where shenpi_id=? order by time asc",id);
			
	}
	
	public Liucheng selectNew(Integer id){
		return Liucheng.dao.findFirst("select * from liucheng where shenpi_id=? order by time desc",id);
			
	}


}
