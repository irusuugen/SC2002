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