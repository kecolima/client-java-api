package com.keeggo.clientsjavaapi.service.security.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.keeggo.clientsjavaapi.model.security.JwtUserFactory;
import com.keeggo.clientsjavaapi.model.user.User;
import com.keeggo.clientsjavaapi.service.user.UserService;

/**
 * Class that implements UserDetailsService interface which loads user-specific data.
 * 
 * @author Mariana Azevedo
 * @since 13/10/2020
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserService userService;
	
	/**
	 * @see UserDetailsService#loadUserByUsername(String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user = userService.findByEmail(username);

		if (user.isPresent()) {
			return JwtUserFactory.create(user.get());
		}

		throw new UsernameNotFoundException("User/Email not found.");
	}

}
