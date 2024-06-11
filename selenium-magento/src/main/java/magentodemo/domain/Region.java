package magentodemo.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import utilities.RandomData;

@Data
@Accessors(fluent = true)
public class Region extends BaseScenario {
	private int regionId;
	private String regionCode;
	private String region;

	public Region withDefaultsForUI() {
		this.region = RandomData.en.address().state();
		return this;
	}

	public Region withDefaultsForApi() {
		this.region = RandomData.getRandomInt(1, 50).toString();
		return this;
	}
}
