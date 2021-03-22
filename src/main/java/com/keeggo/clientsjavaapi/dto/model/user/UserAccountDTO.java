package com.keeggo.clientsjavaapi.dto.model.user;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import com.keeggo.clientsjavaapi.model.account.Account;
import com.keeggo.clientsjavaapi.model.user.User;
import com.keeggo.clientsjavaapi.model.user.UserAccount;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that implements User Account data transfer object (DTO)
 * 
 * @author Mariana Azevedo
 * @since 13/10/2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserAccountDTO extends RepresentationModel<UserAccountDTO>{

	private Long id;
	
	@NotNull(message = "Id cannot be null")
	private Long userId;
	
	@NotNull(message = "Account Id cannot be null")
	private Long accountId;
	
	public UserAccountDTO (Long userId, Long accountId) {
		this.userId = userId;
		this.accountId = accountId;
	}
	
	/**
	 * Method to convert an User Account DTO to an User Account entity.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2020
	 * 
	 * @return an UserAccount object
	 */
	public UserAccount convertDTOToEntity() {
		
		User user = new User(this.getUserId());
		Account account = new Account(this.getAccountId());
		
		UserAccount userAccount = new UserAccount(this.getId(), user, account);
		return userAccount;
	}
	
}
