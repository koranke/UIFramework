package magentodemo.api;

public class MagentoApi {

	public static CustomerApi customer() {
		return new CustomerApi();
	}
}
