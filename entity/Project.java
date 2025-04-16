package entity;

import java.time.LocalDate;
import java.util.*;

enum flatType{
	TWOROOMS,
	THREEROOMS
}

public class Project {
	private String projectName;
	private String neighborhood;
    private int num2Room;
    private int num3Room;
    private int numAvailable2Room;
    private int numAvailable3Room;
    private int sellingPrice2Room;
    private int sellingPrice3Room;
    private LocalDate applicationOpenDate;
    private LocalDate applicationCloseDate;
    private boolean visibility;
    private HDBManager projectManager;
    private int officerSlot;
    private List<HDBOfficer> officerSlotList;
    private ArrayList<Registration> registrationList;
    private ArrayList<Application> applicationList;
    private ArrayList<Enquiry> enquiryList;
    
    public Project(String projectName, String neighborhood, int num2Room, int sellingPrice2Room, int num3Room, int sellingPrice3Room, LocalDate applicationOpenDate, LocalDate applicationCloseDate, HDBManager projectManager, int officerSlot, List<HDBOfficer> officerSlotList) {
    	this.projectName = projectName;
    	this.neighborhood = neighborhood;
    	this.num2Room = num2Room;
    	this.num3Room = num3Room;
    	this.sellingPrice2Room = sellingPrice2Room;
    	this.applicationOpenDate = applicationOpenDate;
    	this.applicationCloseDate = applicationCloseDate;
    	this.officerSlot = officerSlot;
    	this.numAvailable2Room = 0;
    	this.numAvailable3Room = 0;
    	this.officerSlotList = officerSlotList;
        this.applicationList = new ArrayList<>();
        this.registrationList = new ArrayList<>();
        this.enquiryList = new ArrayList<>();
    }
    
    public String getProjectName() {
    	return this.projectName;
    }
    
    public String getNeighborhood() {
    	return this.neighborhood;
    }
    
    public int getSellingPrice(flatType type) {
    	if(type == flatType.TWOROOMS) {
    		return sellingPrice2Room;
    	}
    	else if(type == flatType.THREEROOMS) {
    		return sellingPrice3Room;
    	}
    	return 0;
    }
    
    public boolean getVisibility() {
    	return this.visibility;
    }

    public void setVisibility(boolean vis){
        this.visibility = vis;
    }

    public void addApplication(Application application) {
        applicationList.add(application);
    }

    public void removeApplication(Application application) {
        applicationList.remove(application);
    }
    
    public ArrayList<Enquiry> getEnquiries(){
    	return this.enquiryList;
    }
    
    public void updateOfficerSlot(int newSlots) {
    	officerSlot = newSlots;
    }
    
    public boolean checkOfficerSlot() {
    	return officerSlotList.size() < officerSlot;
    }

    public void addOfficer(HDBOfficer officer) {
    	officerSlotList.add(officer);
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
    
    public void printProjectDetails() {
        System.out.printf("Name: %s\nNeighborhood: %s\n Number of 2-room flats: %d\tSelling price: %d"
        		+ "\\n Number of 2-room flats: %d\\tSelling price: %d\n Application period: %s - %s",
        		projectName, neighborhood, numAvailable2Room, sellingPrice2Room, numAvailable3Room, sellingPrice3Room, dateFormat.format(this.applicationOpenDate),dateFormat.format(this.applicationCloseDate));
    }

    public boolean checkOpeningPeriod() {
        return true;
    }

    public int getNumFlatAvailable(flatType type) {
    	if(type == flatType.TWOROOMS) {
    		return numAvailable2Room;
    	}
    	if(type == flatType.THREEROOMS) {
    		return numAvailable3Room;
    	}
    	return 0;//handle error
    }

    public void addOccupiedFlat(flatType type, int num) {
    	if(type == flatType.TWOROOMS) {
    		numAvailable2Room -= 1;
    	}
    	if(type == flatType.THREEROOMS) {
    		numAvailable3Room -= 1;
    	}
    }
    
    public void removeOccupiedFlat(flatType type, int num) {
    	if(type == flatType.TWOROOMS) {
    		numAvailable2Room += 1;
    	}
    	if(type == flatType.THREEROOMS) {
    		numAvailable3Room += 1;
    	}
    }
}
