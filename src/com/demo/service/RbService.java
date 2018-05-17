package com.demo.service;


import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.demo.common.model.Blog;
import com.demo.common.model.Ribao;
import com.demo.common.model.User;
import com.demo.common.model.Zb;
import com.demo.service.impl.RbImpl;
import com.demo.service.impl.ZbImpl;
import com.jfinal.plugin.activerecord.Page;

public class RbService implements RbImpl{
	/**
	 * 所有的 dao 对象也放在 Service 中
	 */
	private static final Blog dao = new Blog().dao();

	public Page<Ribao> paginate(int pageNumber, int pageSize,String para,String para1) {
		return Ribao.dao.paginate(pageNumber, pageSize, "select *", " from ribao where fid=0 and time>=? and time<=? order by depid desc ",para,para1);
	}

	public List<Ribao> selectAll(){
		return Ribao.dao.find("select * from ribao where fid=0 order by time desc ");
			
	}
	
	public List<Ribao> selectAllByUserid(String para){
		return Ribao.dao.find("select * from ribao where fid=0 and week(ribao.time) = week('"+para+"') group by userid order by depid");
			
	}
	
	public List<Ribao> selecteffectByUserid(String para,String para1){
		return Ribao.dao.find("select * from ribao where fid=0 and week(ribao.time) = week('"+para+"') and userid="+para1);
			
	}
	
	
	public List<Ribao> selectAllByTime(Date time){
		return Ribao.dao.find("select * from ribao where fid=0 and to_days(time)=to_days(?) order by time desc ",time);
			
	}

	public Ribao selectById(String id){
		return Ribao.dao.findById(id);
			
	}
	

	public List<Ribao> selectByFid(String id){
		return Ribao.dao.find("select * from ribao where fid=? order by time asc",id);
			
	}

	@Override
	public List<Ribao> selectByUserid(String userid) {
		return Ribao.dao.find("select * from ribao where userid=?",userid);
	}


}
