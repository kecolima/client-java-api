package com.keeggo.clientsjavaapi.test.repository.user;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.keeggo.clientsjavaapi.enumeration.AccountTypeEnum;
import com.keeggo.clientsjavaapi.enumeration.RoleEnum;
import com.keeggo.clientsjavaapi.model.account.Account;
import com.keeggo.clientsjavaapi.model.user.User;
import com.keeggo.clientsjavaapi.model.user.UserAccount;
import com.keeggo.clientsjavaapi.repository.account.AccountRepository;
import com.keeggo.clientsjavaapi.repository.user.UserAccountRepository;
import com.keeggo.clientsjavaapi.repository.user.UserRepository;

/**
 * Class that implements tests of the UserAccountRepository functionalities
 * 
 * @author Mariana Azevedo
 * @since 06/12/2020
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public class UserAccountRepositoryTest {
	
	static final String EMAIL = "main@test.com";
	static final String ACCOUNT_NUMBER = "999887";
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserAccountRepository userAccRepository;
	
	/**
	 * Method that test the repository that save an UserAccount in the API.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@Test
	public void testSave() {
		
		User user = new User(null, "Main User", "9999", EMAIL, 
				RoleEnum.ROLE_ADMIN);
		userRepository.save(user);
		
		Account account = new Account(null, ACCOUNT_NUMBER, 
				AccountTypeEnum.PREMIUM);
		accountRepository.save(account);
		
		UserAccount userAccount = new UserAccount(null, user, account);
		userAccRepository.save(userAccount);
	}
	
	/**
	 * Method to remove all UserAccount test data.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/12/2020
	 */
	@AfterAll
	private void tearDown() {
		userAccRepository.deleteAll();
		accountRepository.deleteAll();
		userRepository.deleteAll();
	}

}
