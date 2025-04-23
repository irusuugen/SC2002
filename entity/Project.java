/**
 * Represents a housing project in the BTO Management System
 */

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

    /**
     * Constructs a new project with the specified details.
     *
     * @param projectName         Nme of the project
     * @param neighborhood        Neighborhood of the project
     * @param num2Room            Number of 2-room flats in the project
     * @param sellingPrice2Room   Selling price for 2-room flats
     * @param num3Room            Number of 3-room flats in the project
     * @param sellingPrice3Room   Selling price for 3-room flats
     * @param applicationOpenDate Start date for applications
     * @param applicationCloseDate End date for applications
     * @param projectManager       Manager of the project
     * @param officerSlot         Number of officer slots available for the project
     * @param officerSlotList     List of officers assigned to the project
     */
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
    /** @return Name of project */
    public String getProjectName() {
        return projectName;
    }

    /** @return Neighbourhood of project */
    public String getNeighborhood() {
        return neighborhood;
    }

    /** @param type Flat type to get the selling price of
     * @return Selling price of the corresponding flat type */
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

    /** @return true if project should be visible to applicants */
    public boolean isVisible() {
        return visibility;
    }

    /** @return Opening date of project's application period */
    public LocalDate getOpenDate() {
        return applicationOpenDate;
    }

    /** @return Closing date of project's application period */
    public LocalDate getCloseDate() {
        return applicationCloseDate;
    }

    /** @return Number of officers that can be assigned to the project */
    public int getOfficerSlot (){
        return officerSlot;
    }

    /** @return List of enquiries associated with the project */
    public List<Enquiry> getEnquiries() {
        return enquiryList;
    }

    /**
     * @param type Flat type to check number of flats for
     * @return Number of flats available for the chosen flat type */
    public int getNumFlatAvailable(FlatType type) {
        return switch (type) {
            case TWOROOMS -> numAvailable2Room;
            case THREEROOMS -> numAvailable3Room;
        };
    }

    /**
     * @param type Flat type to check number of flats for
     * @return Number of flats for the chosen flat type */
    public int getNumRoom(FlatType type) {
        return switch (type) {
            case TWOROOMS -> num2Room;
            case THREEROOMS -> num3Room;
        };
    }

    /** @return List of registrations associated with the project */
    public List<Registration> getRegistrations(){
        return registrationList;
    }

    /** @return List of applications associated with the project */
    public List<Application> getApplications(){
        return applicationList;
    }

    /** @return List of officers handling the project */
    public List<HDBOfficer> getOfficerSlotList() {
        return officerSlotList;
    }

    /** @return Manager handling the project */
    public HDBManager getManager() {
        return projectManager;
    }

    // === Setters ===

    /**
     * Sets visibility to on or off
     * @param visibility If visibility is on or off
     */
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * Sets new number of officers that can be assigned to project
     * @param newSlotCount New number of officers that can be assigned
     */
    public void setOfficerSlots(int newSlotCount) {
        this.officerSlot = newSlotCount;
    }

    /**
     * Adds enquiry to list of enquiries associated with the project
     * @param enquiry Enquiry to be added
     */
    public void addEnquiry(Enquiry enquiry) {
        enquiryList.add(enquiry);
    }

    /**
     * Removes enquiry from list of enquiries associated with the project
     * @param enquiry Enquiry to be removed
     */
    public void removeEnquiry(Enquiry enquiry) {
        enquiryList.remove(enquiry);
    }

    /**
     * Sets neighborhood of project
     * @param neighborhood New neighborhood
     */
    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    /**
     * Sets new number of 2-room flats
     * @param num2Room New number of 2-room flats
     */
    public void setNum2Room(int num2Room) {
        this.num2Room = num2Room;
    }

    /**
     * Sets new number of 3-room flats
     * @param num3Room New number of 3-room flats
     */
    public void setNum3Room(int num3Room) {
        this.num3Room = num3Room;
    }

    /**
     * Sets new price of 2-room flats
     * @param sellingPrice2Room New price of 2-room flats
     */
    public void setSellingPrice2Room(float sellingPrice2Room) {
        this.sellingPrice2Room = sellingPrice2Room;
    }

    /**
     * Sets new price of 3-room flats
     * @param sellingPrice3Room New price of 3-room flats
     */
    public void setSellingPrice3Room(float sellingPrice3Room) {
        this.sellingPrice3Room = sellingPrice3Room;
    }

    /**
     * Sets new opening date for applications for project
     * @param openDate New opening date
     */
    public void setOpenDate(LocalDate openDate) {
        this.applicationOpenDate = openDate;
    }

    /**
     * Sets new closing date for applications for project
     * @param closeDate New closing date
     */
    public void setCloseDate(LocalDate closeDate) {
        this.applicationCloseDate = closeDate;
    }

    // === Officer Management ===

    /**
     *
     * @return true if officers are still able to register for the project
     */
    public boolean hasAvailableOfficerSlot() {
        return officerSlotList.size() < officerSlot;
    }

    /**
     * Adds a registered officer to the list of officers handling the project
     * @param officer Officer to be added to project
     */
    public void addOfficer(HDBOfficer officer) {
        if (hasAvailableOfficerSlot()) {
            officerSlotList.add(officer);
        }
    }

    /**
     * Adds an officer's registration to the list of registrations associated with the project
     * @param registration Registration to be added
     */
    public void registerOfficer(Registration registration) {
        registrationList.add(registration);
    }


    // === Flat Management ===
    /**
     * Updates the number of available flats by marking a flat as occupied for a specific type.
     * Minuses 1 from the number of available rooms for the flat type.
     *
     * @param type Type of flat (2-room or 3-room)
     */
    public void addOccupiedFlat(FlatType type) {
        switch (type) {
            case TWOROOMS -> numAvailable2Room--;
            case THREEROOMS -> numAvailable3Room--;
        }
    }

    /**
     * Updates the number of available flats by marking a flat as unoccupied for a specific type.
     * Adds 1 to the number of available rooms for the flat type.
     *
     * @param type Type of flat (2-room or 3-room)
     */
    public void removeOccupiedFlat(FlatType type) {
        switch (type) {
            case TWOROOMS -> numAvailable2Room++;
            case THREEROOMS -> numAvailable3Room++;
        }
    }

    // === Application Management ===

    /**
     * Adds application to the list of applications associated with the project
     * @param application Application to be added
     */
    public void addApplication(Application application) {
        applicationList.add(application);
    }

    // === Utility Methods ===

    /**
     * Checks whether the current date is within the application period of the project
     *
     * @return True if local date is within the application period
     */
    public boolean checkOpeningPeriod() {
        LocalDate today = LocalDate.now();
        return (today.isEqual(applicationOpenDate) || today.isAfter(applicationOpenDate))
                && (today.isEqual(applicationCloseDate) || today.isBefore(applicationCloseDate));
    }
}
