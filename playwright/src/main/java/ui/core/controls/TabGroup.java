package ui.core.controls;

import com.microsoft.playwright.Locator;
import lombok.Getter;

@Getter
public class TabGroup {
    protected final Locator locator;

    public TabGroup(Locator locator) {
        this.locator = locator;
    }

}
