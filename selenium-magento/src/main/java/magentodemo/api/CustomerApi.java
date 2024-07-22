package magentodemo.api;

import api.ApiBase;
import enums.AuthType;
import io.restassured.response.Response;
import magentodemo.domain.Account;
import magentodemo.domain.AuthBody;
import magentodemo.domain.Customer;

public class CustomerApi extends ApiBase<CustomerApi> {

	public CustomerApi() {
		baseUrl = "https://magento.softwaretestingboard.com/rest/default/V1/";
	}

	public String getToken(AuthBody body) {
		return tryGetToken(body)
			.then()
			.statusCode(200)
			.extract()
			.as(String.class);
	}

	public Response tryGetToken(AuthBody body) {
		return new CustomerApi()
			.post("integration/customer/token", body);
	}

	public Account getMe(String token) {
		Account account = new Account();
		Customer customer = tryGetMe(token)
			.then()
			.statusCode(200)
			.extract()
			.as(Customer.class);
		return account.customer(customer);
	}

	public Response tryGetMe(String token) {
		return new CustomerApi()
			.withAuthType(AuthType.BEARER)
			.withAuthorization(token)
			.get("customers/me");
	}

	public Response tryCreate(Account body) {
		return new CustomerApi().post("customers", body);
	}

	public Account create(Account body) {
		return tryCreate(body)
			.then()
			.statusCode(200)
			.extract()
			.as(Account.class);
	}

}
