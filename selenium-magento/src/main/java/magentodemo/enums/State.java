package magentodemo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum State {
	AK(2,"Alaska"),
	AL(1, "Alabama"),
	AR(5, "Arkansas"),
	AZ(4, "Arizona"),
	CA(12, "California"),
	CO(13, "Colorado"),
	CT(14, "Connecticut"),
	DE(15, "Delaware"),
	FL(18, "Florida"),
	GA(19, "Georgia"),
	HI(21, "Hawaii"),
	IA(25, "Iowa"),
	ID(22, "Idaho"),
	IL(23, "Illinois"),
	IN(24, "Indiana"),
	KS(26, "Kansas"),
	KY(27, "Kentucky"),
	LA(28, "Louisiana"),
	MA(32, "Massachusetts"),
	MD(31, "Maryland"),
	ME(29, "Maine"),
	MI(33, "Michigan"),
	MN(34, "Minnesota"),
	MO(36, "Missouri"),
	MS(35, "Mississippi"),
	MT(37, "Montana"),
	NC(44, "North Carolina"),
	ND(45, "North Dakota"),
	NE(38, "Nebraska"),
	NH(40, "New Hampshire"),
	NJ(41, "New Jersey"),
	NM(42, "New Mexico"),
	NV(39, "Nevada"),
	NY(43, "New York"),
	OH(47, "Ohio"),
	OK(48, "Oklahoma"),
	OR(49, "Oregon"),
	PA(51, "Pennsylvania"),
	RI(53, "Rhode Island"),
	SC(54, "South Carolina"),
	SD(55, "South Dakota"),
	TN(56, "Tennessee"),
	TX(57, "Texas"),
	UT(58, "Utah"),
	VA(61, "Virginia"),
	VT(59, "Vermont"),
	WA(62, "Washington"),
	WI(64, "Wisconsin"),
	WV(63, "West Virginia"),
	WY(65, "Wyoming"),
	;

	private final int stateId;
	private final String stateName;
}
