package magentodemo.domain;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;
import magentodemo.enums.Countries;
import utilities.RandomData;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(fluent = true)
public class Address extends BaseScenario {
	private String telephone;
	private List<String> street;
	private String city;
	private Region region;
	private String postcode;
	private String country;
	@SerializedName(value = "countryId", alternate = "country_id")
	private String countryId;
	@SerializedName(value = "defaultBilling", alternate = "default_billing")
	private boolean defaultBilling;
	@SerializedName(value = "defaultShipping", alternate = "default_shipping")
	private boolean defaultShipping;

	public Address withDefaults() {
		if (needsPopulation) {
			this.telephone = RandomData.en.phoneNumber().cellPhone();
			this.street = new ArrayList<>(List.of(RandomData.en.address().streetAddress()));
			this.city = RandomData.en.address().city();
			this.region = new Region().withDefaultsForApi();
			this.postcode = RandomData.en.address().zipCode();
			this.countryId = "US";
			this.country = "United States";
			this.defaultBilling = true;
			this.defaultShipping = true;
			needsPopulation = false;
		}
		return this;
	}

	public String toString() {
		if (country == null && countryId != null) {
			country = Countries.valueOf(countryId).getCountryName();
		}
		return String.join("\n", street) + "\n" + city + ", " + region.region() + ", " + postcode
				+ "\n" + country + "\n" + "T: " + telephone;
	}
}
