package com.demo.service.impl;

import java.util.Date;
import java.util.List;

import com.demo.common.model.Ribao;
import com.demo.common.model.User;
import com.demo.common.model.Zb;
import com.jfinal.plugin.activerecord.Page;

public interface RbImpl {

	public Page<Ribao> paginate(int pageNumber, int pageSize,String para,String para1);
	
	public List<Ribao> selectAll();
	
	public List<Ribao> selectByUserid(String userid);
	
	public List<Ribao> selectAllByTime(Date time);
	
	public Ribao selectById(String id);

	public List<Ribao> selectByFid(String id);
}
