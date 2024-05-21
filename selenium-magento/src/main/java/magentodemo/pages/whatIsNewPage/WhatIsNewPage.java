package magentodemo.pages.whatIsNewPage;

import lombok.Getter;
import lombok.experimental.Accessors;
import magentodemo.MagentoDemoSite;
import magentodemo.components.NavigationPanel;
import magentodemo.pages.BaseMagentoDemoPage;

@Getter
@Accessors(fluent = true)
public class WhatIsNewPage extends BaseMagentoDemoPage<WhatIsNewPage> {
	private final NavigationPanel navigationPanel;

	public WhatIsNewPage(MagentoDemoSite site) {
		super(site, "what-is-new.html");
		this.navigationPanel = new NavigationPanel(site.getWebDriver());
	}

}
