package com.litt.nic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.litt.nic.Dao.userMapper;
import com.litt.nic.pojo.user;
import com.litt.nic.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private userMapper usermapper;

	@Override
	public user findById(int id) {

		return usermapper.selectByPrimaryKey(id);
	}

	@Override
	public void addUser(String name, String telephone) {
		// TODO Auto-generated method stub

	}

	// @Override
	// public void addUser(String name, String telephone) {
	// // TODO Auto-generated method stub
	// usermapper.i
	// }
}
