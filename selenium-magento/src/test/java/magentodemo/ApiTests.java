package magentodemo;

import general.TestBase;
import io.restassured.response.Response;
import magentodemo.api.MagentoApi;
import magentodemo.domain.Account;
import org.testng.annotations.Test;

public class ApiTests extends TestBase {

	@Test
	public void testCreateNewCustomer() {
		Account account = new Account().withDefaults();
		Response response = MagentoApi.customer().tryCreate(account);
		response.then().statusCode(200);
	}

	@Test
	public void testGetMe() {
		Account account = new Account().withDefaults();
		MagentoApi.customer().create(account);
		String token = MagentoApi.customer().getToken(account.getAuthBody());
		Response response = MagentoApi.customer().tryGetMe(token);
		response.then().statusCode(200);
	}

	/*
	Simple demonstration of how to use the Account scenario class
	 */
	@Test
	public void testAccountScenario() {
		Account account = new Account()
				.includeBillingAddress(true)
				.includeShippingAddress(true)
				.create()
				;

		System.out.println(account.getContactInfo());
	}
}
