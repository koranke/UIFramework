package magentodemo.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import utilities.RandomData;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(fluent = true)
public class Address extends BaseScenario {
	private String telephone;
	private List<String> street;
	private String address2;
	private String address3;
	private Region region;
	private String state;
	private String postcode;
	private String country;
	private String countryId;
	private boolean defaultBilling;
	private boolean defaultShipping;

	public Address withDefaults() {
		if (needsPopulation) {
			this.telephone = RandomData.en.phoneNumber().cellPhone();
			this.street = new ArrayList<>(List.of(RandomData.en.address().streetAddress()));
			this.region = new Region().withDefaultsForApi();
			this.state = RandomData.en.address().state();
			this.postcode = RandomData.en.address().zipCode();
			this.countryId = "US";
			this.country = "United States";
			this.defaultBilling = true;
			this.defaultShipping = true;
			needsPopulation = false;
		}
		return this;
	}
}
