package magentodemo.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import utilities.RandomData;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(fluent = true)
public class Customer extends BaseScenario {
	private String firstname;
	private String lastname;
	private String email;
	private String company;
	List<Address> addresses;

	public Customer withMinDefaults() {
		if (needsPopulation) {
			this.firstname = RandomData.en.name().firstName();
			this.lastname = RandomData.en.name().lastName();
			this.email = RandomData.getRandomString(10) + "@test.com";
			needsPopulation = false;
		}
		return this;
	}

	public Customer withFullDefaults() {
		if (needsPopulation) {
			withMinDefaults();
			this.company = RandomData.en.company().name();
			this.addresses = new ArrayList<>();
			this.addresses.add(new Address().withDefaults());
			needsPopulation = false;
		}
		return this;
	}

}
