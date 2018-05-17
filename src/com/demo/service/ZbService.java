package com.demo.service;


import java.util.Date;
import java.util.List;

import com.demo.common.model.Blog;
import com.demo.common.model.Ribao;
import com.demo.common.model.User;
import com.demo.common.model.Zb;
import com.demo.service.impl.ZbImpl;
import com.jfinal.plugin.activerecord.Page;

public class ZbService implements ZbImpl{
	/**
	 * 所有的 dao 对象也放在 Service 中
	 */
	private static final Blog dao = new Blog().dao();

	public Page<Zb> paginate(int pageNumber, int pageSize,String para) {
		return Zb.dao.paginate(pageNumber, pageSize, "select *", " from zb where weekofyear=? order by concat(userid,time) asc ",para);
	}

	public List<Zb> selectAll(){
		return Zb.dao.find("select * from zb order by concat(userid,time) asc ");
			
	}
	public List<Zb> selectAllByTime(Date time){
		return Zb.dao.find("select * from zb where time between date_sub(curdate(),INTERVAL WEEKDAY(curdate()) + 0 DAY) and date_sub(curdate(),INTERVAL WEEKDAY(curdate()) - 7 DAY)");
			
	}
	
	public Zb selectById(String id){
		return Zb.dao.findById(id);
			
	}

	@Override
	public List<Zb> selectByUserid(String userid) {
		return Zb.dao.find("select * from zb where admin=?",userid);
	}


}
