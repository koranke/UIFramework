package configuration;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TargetEnvironment {
	TEST("test"),
	INT("int"),
	PERF("perf")
	;

	private final String value;
}
