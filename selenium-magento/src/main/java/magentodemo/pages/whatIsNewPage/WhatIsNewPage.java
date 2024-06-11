package magentodemo.pages.whatIsNewPage;

import lombok.Getter;
import lombok.experimental.Accessors;
import magentodemo.MagentoDemoSite;
import magentodemo.components.PanelNavigation;
import magentodemo.pages.BaseMagentoDemoPage;

@Getter
@Accessors(fluent = true)
public class WhatIsNewPage extends BaseMagentoDemoPage<WhatIsNewPage> {
	private final PanelNavigation panelNavigation;

	public WhatIsNewPage(MagentoDemoSite site) {
		super(site, "what-is-new.html");
		this.panelNavigation = new PanelNavigation(site.getWebDriver());
	}

}
