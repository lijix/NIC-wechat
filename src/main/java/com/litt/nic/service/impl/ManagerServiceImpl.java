package com.litt.nic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.litt.nic.Dao.managerMapper;
import com.litt.nic.pojo.manager;
import com.litt.nic.service.IManagerService;

@Service
public class ManagerServiceImpl implements IManagerService {

	@Autowired
	private managerMapper managerMapper;

	public manager findById(int id) {
		// TODO Auto-generated method stub
		return managerMapper.selectByPrimaryKey(id);
	}

	@Override
	public manager findByNamePsw(String name, String psw) {
		// TODO Auto-generated method stub
		return managerMapper.selectByNamePsw(name, psw);
	}

	@Override
	public List<manager> selectAllManager() {
		// TODO Auto-generated method stub
		return managerMapper.selectAllManager();
	}

	@Override
	public void insert(manager record) {
		// TODO Auto-generated method stub
		managerMapper.insert(record);
	}

	@Override
	public void deleteByPrimaryKey(Integer managerId) {
		// TODO Auto-generated method stub
		managerMapper.deleteByPrimaryKey(managerId);
	}

	@Override
	public manager selectByPrimaryKey(Integer managerId) {
		// TODO Auto-generated method stub
		return managerMapper.selectByPrimaryKey(managerId);
	}

	@Override
	public void updateByPrimaryKeySelective(manager model) {
		// TODO Auto-generated method stub
		managerMapper.updateByPrimaryKey(model);

	}

	@Override
	public manager findByName(String name) {

		return managerMapper.findByName(name);
	}

}
