package com.demo.service.impl;

import java.util.List;

import com.demo.common.model.Contract;
import com.jfinal.plugin.activerecord.Page;

public interface ContractImpl {

	public Page<Contract> paginate(int pageNumber, int pageSize);
	
	public List<Contract> selectAll();
}
