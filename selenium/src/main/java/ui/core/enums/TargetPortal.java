package ui.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TargetPortal {
	TEST_WEB("testweb", "TestWeb", "TestWebSite")
	;

	private final String packageName;
	private final String baseName;
	private final String siteName;
}
