package magentodemo.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import utilities.RandomData;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(fluent = true)
public class Customer {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String company;
	List<Address> addresses = new ArrayList<>();

	public Customer withMinDefaults() {
		this.firstName = RandomData.getRandomString(10);
		this.lastName = RandomData.getRandomString(10);
		this.email = RandomData.getRandomString(10) + "@test.com";
		this.password = RandomData.getRandomString(12);
		return this;
	}

	public Customer withFullDefaults() {
		withMinDefaults();
		this.company = RandomData.en.company().name();
		this.addresses.add(new Address().withDefaults());
		return this;
	}

}
