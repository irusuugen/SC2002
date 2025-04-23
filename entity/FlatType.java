/**
 * Enum representing the types of flats available for BTO projects.
 * This includes the two types of flats: 2-Room and 3-Room.
 */

package entity;

public enum FlatType{
	TWOROOMS,
	THREEROOMS;

	@Override
	public String toString() {
		switch (this) {
			case TWOROOMS:
				return "2-Room";
			case THREEROOMS:
				return "3-Room";
			default:
				return super.toString();
		}
	}
}