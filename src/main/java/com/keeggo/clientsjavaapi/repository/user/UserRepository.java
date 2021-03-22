package com.keeggo.clientsjavaapi.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keeggo.clientsjavaapi.model.user.User;

/**
 * Interface that implements the User Repository, with JPA CRUD methods
 * and other customized searches.
 *  
 * @author Mariana Azevedo
 * @since 11/10/2020
 */
public interface UserRepository extends JpaRepository<User, Long>{
	
	/**
	 * Method to search an User by the email.
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @param email
	 * @return Optional<User>
	 */
	Optional<User> findByEmail(String email);
}

