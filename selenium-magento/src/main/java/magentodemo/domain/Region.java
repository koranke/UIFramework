package magentodemo.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import magentodemo.enums.State;
import utilities.RandomData;

@Data
@Accessors(fluent = true)
public class Region extends BaseScenario {
	private Integer regionId;
	private String regionCode;
	private String region;

	public Region withDefaults() {
		State state = RandomData.getRandomItemFromList(State.values());
		this.regionId = state.getStateId();
		this.regionCode = regionId.toString();
		this.region = state.getStateName();
		return this;
	}
}
