package magentodemo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Countries {
	UK("United Kingdom"),
	US("United States"),
	FR("France"),
	DE("Germany"),
	IT("Italy"),
	ES("Spain"),
	BE("Belgium"),
	NL("Netherlands"),
	SE("Sweden"),
	DK("Denmark"),
	FI("Finland"),
	NO("Norway"),
	PL("Poland"),
	CZ("Czech Republic"),
	HU("Hungary"),
	RO("Romania"),
	BG("Bulgaria"),
	GR("Greece"),
	CH("Switzerland"),
	AT("Austria"),
	IE("Ireland"),
	PT("Portugal"),
	LU("Luxembourg"),
	MT("Malta"),
	CY("Cyprus"),
	EE("Estonia"),
	LV("Latvia"),
	LT("Lithuania"),
	SK("Slovakia"),
	SI("Slovenia"),
	IS("Iceland"),
	JP("Japan"),
	CN("China"),
	IN("India"),
	AU("Australia"),
	NZ("New Zealand"),
	CA("Canada"),
	BR("Brazil"),
	AR("Argentina")
	;


	private final String countryName;
}
