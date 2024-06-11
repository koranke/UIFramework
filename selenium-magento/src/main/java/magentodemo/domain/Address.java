package magentodemo.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import utilities.RandomData;

@Data
@Accessors(fluent = true)
public class Address {
	private String telephone;
	private String address1;
	private String address2;
	private String address3;
	private String city;
	private String state;
	private String zip;
	private String country;
	private boolean isDefaultBilling;
	private boolean isDefaultShipping;

	public Address withDefaults() {
		this.telephone = RandomData.en.phoneNumber().cellPhone();
		this.address1 = RandomData.en.address().streetAddress();
		this.city = RandomData.en.address().city();
		this.state = RandomData.en.address().state();
		this.zip = RandomData.en.address().zipCode();
		this.country = RandomData.en.address().country();
		this.isDefaultBilling = true;
		this.isDefaultShipping = true;
		return this;
	}
}
