package entity;

import java.time.LocalDate;
import java.util.*;

public class Project {
    private final String projectName;
    private final String neighborhood;

    private final int num2Room;
    private final int num3Room;
    private int numAvailable2Room;
    private int numAvailable3Room;

    private final float sellingPrice2Room;
    private final float sellingPrice3Room;

    private final LocalDate applicationOpenDate;
    private final LocalDate applicationCloseDate;

    private boolean visibility = true;

    private final HDBManager projectManager;
    private int officerSlot;
    private List<HDBOfficer> officerSlotList;

    private final List<Registration> registrationList = new ArrayList<>();
    private List<Application> applicationList = new ArrayList<>();
    private List<Enquiry> enquiryList = new ArrayList<>();

    public Project(String projectName, String neighborhood, int num2Room, float sellingPrice2Room,
                   int num3Room, float sellingPrice3Room, LocalDate applicationOpenDate,
                   LocalDate applicationCloseDate, HDBManager projectManager, int officerSlot,
                   List<HDBOfficer> officerSlotList) {

        this.projectName = projectName;
        this.neighborhood = neighborhood;
        this.num2Room = num2Room;
        this.num3Room = num3Room;
        this.numAvailable2Room = num2Room;
        this.numAvailable3Room = num3Room;
        this.sellingPrice2Room = sellingPrice2Room;
        this.sellingPrice3Room = sellingPrice3Room;
        this.applicationOpenDate = applicationOpenDate;
        this.applicationCloseDate = applicationCloseDate;
        this.projectManager = projectManager;
        this.officerSlot = officerSlot;
        this.officerSlotList = new ArrayList<>(officerSlotList);
    }

    // === Getters ===
    public String getProjectName() {
        return projectName;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public float getSellingPrice(FlatType type) {
        switch (type) {
            case TWOROOMS:
                return sellingPrice2Room;
            case THREEROOMS:
                return sellingPrice3Room;
            default:
                return 0;
        }
    }

    public boolean isVisible() {
        return visibility;
    }

    public LocalDate getOpenDate() {
        return applicationOpenDate;
    }

    public LocalDate getCloseDate() {
        return applicationCloseDate;
    }

    public List<Enquiry> getEnquiries() {
        return Collections.unmodifiableList(enquiryList);
    }

    public int getNumFlatAvailable(FlatType type) {
        return switch (type) {
            case TWOROOMS -> numAvailable2Room;
            case THREEROOMS -> numAvailable3Room;
        };
    }

    public List<Registration> getRegistrations(){
        return registrationList;
    }

    public List<Application> getApplications(){
        return applicationList;
    }

    // === Setters ===
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public void updateOfficerSlot(int newSlotCount) {
        this.officerSlot = newSlotCount;
    }

    // === Officer Management ===
    public boolean hasAvailableOfficerSlot() {
        return officerSlotList.size() < officerSlot;
    }

    public void addOfficer(HDBOfficer officer) {
        if (hasAvailableOfficerSlot()) {
            officerSlotList.add(officer);
        }
    }

    public void removeOfficer(HDBOfficer officer) {
        officerSlotList.remove(officer);
    }

    public void registerOfficer(Registration registration) {
        registrationList.add(registration);
    }

    public void unregisterOfficer(Registration registration) {
        registrationList.remove(registration);
    }

    // === Flat Management ===
    public void addOccupiedFlat(FlatType type) {
        switch (type) {
            case TWOROOMS -> numAvailable2Room--;
            case THREEROOMS -> numAvailable3Room--;
        }
    }

    public void removeOccupiedFlat(FlatType type) {
        switch (type) {
            case TWOROOMS -> numAvailable2Room++;
            case THREEROOMS -> numAvailable3Room++;
        }
    }

    // === Application Management ===
    public void addApplication(Application application) {
        applicationList.add(application);
    }

    public void removeApplication(Application application) {
        applicationList.remove(application);
    }

    // === Utility Methods ===
    public void printProjectDetails() {
        System.out.printf("""
            Name: %s
            Neighborhood: %s
            Number of 2-room flats: %d\tSelling price: %.2f
            Number of 3-room flats: %d\tSelling price: %.2f
            Application period: %s - %s
            """,
            projectName, neighborhood,
            numAvailable2Room, sellingPrice2Room,
            numAvailable3Room, sellingPrice3Room,
            applicationOpenDate, applicationCloseDate
        );
    }

    public boolean checkOpeningPeriod() {
        LocalDate today = LocalDate.now();
        return (today.isEqual(applicationOpenDate) || today.isAfter(applicationOpenDate))
                && (today.isEqual(applicationCloseDate) || today.isBefore(applicationCloseDate));
    }
}
