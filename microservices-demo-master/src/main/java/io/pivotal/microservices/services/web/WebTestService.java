package io.pivotal.microservices.services.web;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import io.pivotal.microservices.exceptions.AccountNotFoundException;

@Service
public class WebTestService {

	

		@Autowired
		@LoadBalanced
		protected RestTemplate restTemplate;

		protected String serviceUrl;

		protected Logger logger = Logger.getLogger(WebAccountsService.class
				.getName());

		public WebTestService(String serviceUrl) {
			this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
					: "http://" + serviceUrl;
		}

		/**
		 * The RestTemplate works because it uses a custom request-factory that uses
		 * Ribbon to look-up the service to use. This method simply exists to show
		 * this.
		 */
		@PostConstruct
		public void demoOnly() {
			// Can't do this in the constructor because the RestTemplate injection
			// happens afterwards.
			logger.warning("The RestTemplate request factory is "
					+ restTemplate.getRequestFactory().getClass());
		}

		public Test findByNumber(String accountNumber) {

			logger.info("findByNumber() invoked: for " + accountNumber);
			return restTemplate.getForObject(serviceUrl + "/test/{number}",
					Test.class, accountNumber);
		}

		public List<Test> byOwnerContains(String name) {
			logger.info("byOwnerContains() invoked:  for " + name);
			Test[] accounts = null;

			try {
				accounts = restTemplate.getForObject(serviceUrl
						+ "/test/owner/{name}", Test[].class, name);
			} catch (HttpClientErrorException e) { // 404
				// Nothing found
			}

			if (accounts == null || accounts.length == 0)
				return null;
			else
				return Arrays.asList(accounts);
		}

		public Test getByNumber(String accountNumber) throws Exception {
			Test account = restTemplate.getForObject(serviceUrl
					+ "/test/{number}", Test.class, accountNumber);

			if (account == null)
				throw new Exception(accountNumber);
			else
				return account;
		}
	}
