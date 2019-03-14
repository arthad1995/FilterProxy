package com.abc.FliterProxy.Daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc.FliterProxy.beans.User;

public interface UserDao extends JpaRepository<User, Integer>{
	public User findByUsername(String name);
}
