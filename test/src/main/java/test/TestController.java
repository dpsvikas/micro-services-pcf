package test;

import java.util.List;
import java.util.logging.Logger;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	

		protected Logger logger = Logger.getLogger(TestController.class
				.getName());
		protected TestRepository accountRepository;

		/**
		 * Create an instance plugging in the respository of Accounts.
		 * 
		 * @param accountRepository
		 *            An account repository implementation.
		 */
		@Autowired
		public TestController(TestRepository accountRepository) {
			this.accountRepository = accountRepository;

			logger.info("AccountRepository says system has "
					+ accountRepository.countAccounts() + " accounts");
		}

		/**
		 * Fetch an account with the specified account number.
		 * 
		 * @param accountNumber
		 *            A numeric, 9 digit account number.
		 * @return The account if found.
		 * @throws Exception 
		 * @throws AccountNotFoundException
		 *             If the number is not recognised.
		 */
		@RequestMapping("/test/{accountNumber}")
		public Test byNumber(@PathVariable("accountNumber") String accountNumber) throws Exception {

			logger.info("accounts-service byNumber() invoked: " + accountNumber);
			Test account = accountRepository.findByNumber(accountNumber);
			logger.info("accounts-service byNumber() found: " + account);

			if (account == null)
				throw new Exception(accountNumber);
			else {
				return account;
			}
		}

		/**
		 * Fetch accounts with the specified name. A partial case-insensitive match
		 * is supported. So <code>http://.../accounts/owner/a</code> will find any
		 * accounts with upper or lower case 'a' in their name.
		 * 
		 * @param partialName
		 * @return A non-null, non-empty set of accounts.
		 * @throws Exception 
		 * @throws AccountNotFoundException
		 *             If there are no matches at all.
		 */
		@RequestMapping("/test/owner/{name}")
		public List<Test> byOwner(@PathVariable("name") String partialName) throws Exception {
			logger.info("accounts-service byOwner() invoked: "
					+ accountRepository.getClass().getName() + " for "
					+ partialName);

			List<Test> accounts = accountRepository
					.findByOwnerContainingIgnoreCase(partialName);
			logger.info("accounts-service byOwner() found: " + accounts);

			if (accounts == null || accounts.size() == 0)
				throw new Exception(partialName);
			else {
				return accounts;
			}
		}
	}


