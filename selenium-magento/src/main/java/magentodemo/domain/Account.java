package magentodemo.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import utilities.RandomData;

@Data
@Accessors(fluent = true)
public class Account extends BaseScenario {
	private Customer customer;
	private String password;

	public Account withDefaults() {
		if (needsPopulation) {
			this.customer = new Customer().withMinDefaults();
			this.password = RandomData.getRandomString(12);
			needsPopulation = false;
		}
		return this;
	}

	public AuthBody getAuthBody() {
		AuthBody authBody = new AuthBody();
		authBody.username = customer.email();
		authBody.password = password;
		return authBody;
	}
}
