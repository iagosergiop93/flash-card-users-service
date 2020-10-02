package com.flashcard.users.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flashcard.users.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	List<User> findAll();
	
	User findById(long id);
	
	User findByUsername(String username);
	
	User findByUsernameAndPasswd(String username, String passwd);
	
	User save(User user);
	
	List<User> save(List<User> users);
	
	void flush();
	
	void deleteById(long id);
	
}

