package general;

import lombok.Data;
import lombok.experimental.Accessors;
import org.openqa.selenium.Cookie;
import ui.core.SeleniumManager;

import java.util.Date;

@Data
@Accessors(fluent = true)
public abstract class CookieScenario<T> {
	protected boolean isNotPopulated = true;
	private String name;
	private String nameValue;
	private String domain;
	private String path;
	private String sameSite;
	private boolean isSecure;
	private boolean isHttpOnly;
	private Date expiresOn;

	public abstract T withDefaults();

	public Cookie build() {
		withDefaults();
		return new Cookie.Builder(name, nameValue)
				.domain(domain)
				.expiresOn(expiresOn)
				.isHttpOnly(isHttpOnly)
				.isSecure(isSecure)
				.path(path)
				.sameSite(sameSite)
				.build();
	}

	public T create() {
		SeleniumManager.getCurrentDriver().manage().addCookie(build());
		return (T) this;
	}
}
