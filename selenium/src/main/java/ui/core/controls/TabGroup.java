package ui.core.controls;

import lombok.Getter;
import ui.core.Locator;

@Getter
public class TabGroup {
    protected final Locator locator;

    public TabGroup(Locator locator) {
        this.locator = locator;
    }

}
