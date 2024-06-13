package magentodemo.domain;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;
import magentodemo.enums.Country;
import utilities.RandomData;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(fluent = true)
public class Address extends BaseScenario {
	private String firstname;
	private String lastname;
	private String telephone;
	private List<String> street;
	private String city;
	private Region region;
	private String postcode;
	private transient String country;
	@SerializedName(value = "countryId", alternate = "country_id")
	private String countryId;
	@SerializedName(value = "defaultBilling", alternate = "default_billing")
	private boolean defaultBilling;
	@SerializedName(value = "defaultShipping", alternate = "default_shipping")
	private boolean defaultShipping;

	public Address withDefaults() {
		if (needsPopulation) {
			if (firstname == null)
				this.firstname = RandomData.en.name().firstName();
			if (lastname == null)
				this.lastname = RandomData.en.name().lastName();
			this.telephone = RandomData.en.phoneNumber().cellPhone();
			this.street = new ArrayList<>(List.of(RandomData.en.address().streetAddress()));
			this.city = RandomData.en.address().city();
			this.region = new Region().withDefaults();
			this.postcode = RandomData.en.address().zipCode();
			this.countryId = "US";
			this.country = "United States";
			needsPopulation = false;
		}
		return this;
	}

	public String toString() {
		if (country == null && countryId != null) {
			country = Country.valueOf(countryId).getCountryName();
		}
		return firstname + " " + lastname + "\n" + String.join("\n", street) + "\n" + city + ", " + region.region() + ", " + postcode
				+ "\n" + country + "\n" + "T: " + telephone;
	}
}
