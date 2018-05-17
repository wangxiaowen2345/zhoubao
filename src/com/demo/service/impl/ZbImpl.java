package com.demo.service.impl;

import java.util.Date;
import java.util.List;

import com.demo.common.model.User;
import com.demo.common.model.Zb;
import com.jfinal.plugin.activerecord.Page;

public interface ZbImpl {

	public Page<Zb> paginate(int pageNumber, int pageSize,String para);
	
	public List<Zb> selectAll();

	public Zb selectById(String id);
	
	public List<Zb> selectByUserid(String userid);
	
	public List<Zb> selectAllByTime(Date time);
}
