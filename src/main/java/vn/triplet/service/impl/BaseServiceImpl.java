package vn.triplet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import vn.triplet.dao.UserDAO;

public class BaseServiceImpl {
	
	@Autowired
	protected UserDAO userDAO;

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO studentDAO) {
		this.userDAO = studentDAO;
	}
}
