/**
 * Enum representing the user's group based on their marital status.
 * This is used to categorize applicants as either married or single.
 */

package entity;

public enum UserGroup {
    MARRIED, SINGLE;

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
