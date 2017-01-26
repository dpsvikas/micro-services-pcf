package test;

import java.util.List;

import org.mockito.internal.matchers.Find;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;


public interface TestRepository extends Repository<Test, Long> {

	 /* Find an account with the specified account number.
	 *
	 * @param accountNumber
	 * @return The account if found, null otherwise.
	 */
	public Test findByNumber(String accountNumber);

	/**
	 * Find accounts whose owner name contains the specified string
	 * 
	 * @param partialName
	 *            Any alphabetic string.
	 * @return The list of matching accounts - always non-null, but may be
	 *         empty.
	 */
	public List<Test> findByOwnerContainingIgnoreCase(String partialName);

	/**
	 * Fetch the number of accounts known to the system.
	 * 
	 * @return The number of accounts.
	 */
	@Query("SELECT count(*) from Test")
	public int countAccounts();
}
