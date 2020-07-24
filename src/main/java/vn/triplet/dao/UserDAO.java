package vn.triplet.dao;

import java.util.List;

import vn.triplet.model.User;

public interface UserDAO extends BaseDAO<Integer, User> {
	
	List<User> loadUsers();
	
}
