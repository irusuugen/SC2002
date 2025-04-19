package entity;

import java.time.LocalDate;
import java.util.*;

public class Project {
    private String projectName;
    private String neighborhood;

    private int num2Room;
    private int num3Room;
    private int numAvailable2Room;
    private int numAvailable3Room;

    private float sellingPrice2Room;
    private float sellingPrice3Room;

    private LocalDate applicationOpenDate;
    private LocalDate applicationCloseDate;

    private boolean visibility = true;

    private final HDBManager projectManager;
    private int officerSlot;
    private List<HDBOfficer> officerSlotList;

    private List<Registration> registrationList = new ArrayList<>();
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

    public int getOfficerSlot (){
        return officerSlot;
    }

    public List<Enquiry> getEnquiries() {
        return enquiryList;
    }

    public int getNumFlatAvailable(FlatType type) {
        return switch (type) {
            case TWOROOMS -> numAvailable2Room;
            case THREEROOMS -> numAvailable3Room;
        };
    }

    public int getNumRoom(FlatType type) {
        return switch (type) {
            case TWOROOMS -> num2Room;
            case THREEROOMS -> num3Room;
        };
    }


    public List<Registration> getRegistrations(){
        return registrationList;
    }

    public List<Application> getApplications(){
        return applicationList;
    }

    public List<HDBOfficer> getOfficerSlotList() {
        return officerSlotList;
    }

    public HDBManager getManager() {
        return projectManager;
    }

    // === Setters ===
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public void setOfficerSlots(int newSlotCount) {
        this.officerSlot = newSlotCount;
    }

    public void addEnquiry(Enquiry enquiry) {
        enquiryList.add(enquiry);
    }

    public void removeEnquiry(Enquiry enquiry) {
        enquiryList.remove(enquiry);
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void setNum2Room(int num2Room) {
        this.num2Room = num2Room;
    }

    public void setNum3Room(int num3Room) {
        this.num3Room = num3Room;
    }

    public void setSellingPrice2Room(float sellingPrice2Room) {
        this.sellingPrice2Room = sellingPrice2Room;
    }

    public void setSellingPrice3Room(float sellingPrice3Room) {
        this.sellingPrice3Room = sellingPrice3Room;
    }

    public void setOpenDate(LocalDate openDate) {
        this.applicationOpenDate = openDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.applicationCloseDate = closeDate;
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
    public boolean checkOpeningPeriod() {
        LocalDate today = LocalDate.now();
        return (today.isEqual(applicationOpenDate) || today.isAfter(applicationOpenDate))
                && (today.isEqual(applicationCloseDate) || today.isBefore(applicationCloseDate));
    }
}
