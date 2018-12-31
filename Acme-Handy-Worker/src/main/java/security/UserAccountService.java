
package security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class UserAccountService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UserAccountRepository	useraccountRepository;


	// Constructors -----------------------------------------------------------

	public UserAccountService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public UserAccount create() {
		final UserAccount result;

		result = new UserAccount();

		return result;
	}

	public Collection<UserAccount> findAll() {
		Collection<UserAccount> result;

		result = this.useraccountRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public UserAccount findOne(final int configurationId) {
		UserAccount result;

		result = this.useraccountRepository.findOne(configurationId);
		Assert.notNull(result);

		return result;
	}

	public UserAccount save(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		UserAccount result;

		result = this.useraccountRepository.save(userAccount);

		return result;
	}

	public void delete(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Assert.notNull(userAccount.getId());

		this.useraccountRepository.delete(userAccount);
	}

}
