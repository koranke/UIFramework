package magentodemo.api;

import api.ApiBase;
import enums.AuthType;
import io.restassured.response.Response;
import magentodemo.domain.Account;
import magentodemo.domain.AuthBody;

public class CustomerApi extends ApiBase<CustomerApi> {

	private CustomerApi() {
		baseUrl = "https://magento.softwaretestingboard.com/rest/default/V1/";
	}

	public static String getToken(AuthBody body) {
		return tryGetToken(body)
			.then()
			.statusCode(200)
			.extract()
			.as(String.class);
	}

	public static Response tryGetToken(AuthBody body) {
		return new CustomerApi()
			.post("integration/customer/token", body);
	}

	public static Account getMe(String token) {
		return tryGetMe(token)
			.then()
			.statusCode(200)
			.extract()
			.as(Account.class);
	}

	public static Response tryGetMe(String token) {
		return new CustomerApi()
			.withAuthType(AuthType.BEARER)
			.withAuthorization(token)
			.get("customers/me");
	}

	public static Response tryCreate(Account body) {
		return new CustomerApi().post("customers", body);
	}

	public static Account create(Account body) {
		return tryCreate(body)
			.then()
			.statusCode(200)
			.extract()
			.as(Account.class);
	}

}
