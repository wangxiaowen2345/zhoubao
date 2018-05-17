package com.demo.service;

import java.util.List;

import com.demo.common.model.Contract;
import com.demo.service.impl.ContractImpl;
import com.jfinal.plugin.activerecord.Page;

public class ContractService implements ContractImpl{

	public Page<Contract> paginate(int pageNumber, int pageSize) {
		return Contract.dao.paginate(pageNumber, pageSize, "select *", " from contract where istrue=0");
	}

	public List<Contract> selectAll(){
		return Contract.dao.find("select * from contract where istrue=0");
			
	}
	
	public List<Contract> selectAllByUserid(int userid){
		return Contract.dao.find("select * from contract where userid=?",userid);
			
	}


}