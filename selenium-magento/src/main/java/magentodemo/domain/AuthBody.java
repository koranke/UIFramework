package magentodemo.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class AuthBody {
	private String username;
	private String password;
}
