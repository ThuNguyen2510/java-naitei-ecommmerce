package vn.triplet.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import vn.triplet.dao.GenericDAO;
import vn.triplet.dao.UserDAO;
import vn.triplet.model.User;

public class UserDAOImpl extends GenericDAO<Integer, User> implements UserDAO {
	
	public UserDAOImpl() {
		super(User.class);
	}
	
	public UserDAOImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	@Override
	public void delete(User entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User saveOrUpdate(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findById(Serializable key) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> loadUsers() {
		return getSession().createQuery("from User").getResultList();
	}
}
