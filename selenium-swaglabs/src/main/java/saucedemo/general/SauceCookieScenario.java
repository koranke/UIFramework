package saucedemo.general;

import general.CookieScenario;

import java.util.Date;

public class SauceCookieScenario extends CookieScenario<SauceCookieScenario> {

	@Override
	public SauceCookieScenario withDefaults() {
		if (isNotPopulated) {
			this.name("session-username")
					.nameValue("standard_user")
					.domain("www.saucedemo.com")
					.path("/")
					.sameSite("Lax")
					.isSecure(false)
					.isHttpOnly(false)
					.expiresOn(new Date(System.currentTimeMillis() + 1000000));
			isNotPopulated = false;
		}
		return this;
	}
}
