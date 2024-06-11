package magentodemo;

import general.TestBase;
import io.restassured.response.Response;
import magentodemo.api.CustomerApi;
import magentodemo.domain.Account;
import org.testng.annotations.Test;

public class ApiTests extends TestBase {

	@Test
	public void testCreateNewCustomer() {
		Account account = new Account().withDefaults();
		Response response = CustomerApi.tryCreate(account);
		response.then().statusCode(200);
	}

	@Test
	public void testGetMe() {
		Account account = new Account().withDefaults();
		CustomerApi.create(account);
		String token = CustomerApi.getToken(account.getAuthBody());
		Response response = CustomerApi.tryGetMe(token);
		response.then().statusCode(200);
	}
}
