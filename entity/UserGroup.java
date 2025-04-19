package entity;

public enum UserGroup {
    MARRIED, SINGLE, NEITHER;

    @Override
    public String toString() {
        switch (this) {
            case MARRIED:
                return "Married";
            case SINGLE:
                return "Single";
            default:
                return "N/A";
        }
    }
}
