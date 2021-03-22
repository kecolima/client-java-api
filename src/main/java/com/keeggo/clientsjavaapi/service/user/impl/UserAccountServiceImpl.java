package com.keeggo.clientsjavaapi.service.user.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeggo.clientsjavaapi.model.user.UserAccount;
import com.keeggo.clientsjavaapi.repository.user.UserAccountRepository;
import com.keeggo.clientsjavaapi.service.user.UserAccountService;

/**
 * Class that implements the user's account service methods
 * 
 * @author Mariana Azevedo
 * @since 13/10/2020
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired
	UserAccountRepository repository;
	
	/**
	 * @see UserAccountService#save(UserAccount)
	 */
	@Override
	public UserAccount save(UserAccount userTransaction) {
		return repository.save(userTransaction);
	}

	/**
	 * @see UserAccountService#findByUserIdAndAccountId(Long, Long)
	 */
	@Override
	public Optional<UserAccount> findByUserIdAndAccountId(Long user, Long account) {
		return repository.findByUserIdAndAccountId(user, account);
	}

}
