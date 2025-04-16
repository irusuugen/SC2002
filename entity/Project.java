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
    private HDBManager projectManager;
    private int officerSlot;
    private List<HDBOfficer> officerSlotList;
    private ArrayList<HDBOfficer> registrationList;
    private ArrayList<Application> applicationList;
    private ArrayList<Enquiry> enquiryList;
    
    public Project(String projectName, String neighborhood, int num2Room, float sellingPrice2Room, int num3Room, float sellingPrice3Room, LocalDate applicationOpenDate, LocalDate applicationCloseDate, HDBManager projectManager, int officerSlot, List<HDBOfficer> officerSlotList) {
    	this.projectName = projectName;
    	this.neighborhood = neighborhood;
    	this.num2Room = num2Room;
    	this.num3Room = num3Room;
    	this.sellingPrice2Room = sellingPrice2Room;
    	this.applicationOpenDate = applicationOpenDate;
    	this.applicationCloseDate = applicationCloseDate;
    	this.officerSlot = officerSlot;
    	this.numAvailable2Room = num2Room;
    	this.numAvailable3Room = num3Room;
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
    
    public float getSellingPrice(FlatType type) {
    	if(type == FlatType.TWOROOMS) {
    		return sellingPrice2Room;
    	}
    	else if(type == FlatType.THREEROOMS) {
    		return sellingPrice3Room;
    	}
    	return 0;
    }
    
    public boolean getVisibility() {
    	return this.visibility;
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
    
    public void registerOfficer(HDBOfficer officer) {
    	registrationList.add(officer);
    }

    public void unregisterOfficer(HDBOfficer officer) {
    	registrationList.remove(officer);
    }
    
    public void printProjectDetails() {
        System.out.printf("Name: %s\nNeighborhood: %s\n Number of 2-room flats: %d\tSelling price: %d"
        		+ "\\n Number of 2-room flats: %d\\tSelling price: %d\n Application period: %s - %s",
        		projectName, neighborhood, numAvailable2Room, sellingPrice2Room, numAvailable3Room, sellingPrice3Room, this.applicationOpenDate, this.applicationCloseDate);
    }

    public boolean checkOpeningPeriod() {
        return true;
    }

    public int getNumFlatAvailable(FlatType type) {
    	if(type == FlatType.TWOROOMS) {
    		return numAvailable2Room;
    	}
    	if(type == FlatType.THREEROOMS) {
    		return numAvailable3Room;
    	}
    	return 0;//handle error
    }

    public void addOccupiedFlat(FlatType type, int num) {
    	if(type == FlatType.TWOROOMS) {
    		numAvailable2Room -= 1;
    	}
    	if(type == FlatType.THREEROOMS) {
    		numAvailable3Room -= 1;
    	}
    	//add error
    }
    
    public void removeOccupiedFlat(FlatType type, int num) {
    	if(type == FlatType.TWOROOMS) {
    		numAvailable2Room += 1;
    	}
    	if(type == FlatType.THREEROOMS) {
    		numAvailable3Room += 1;
    	}
    	//add error
    }

    public LocalDate getOpenDate() {
        return this.applicationOpenDate;
    }

    public LocalDate getCloseDate() {
        return this.applicationCloseDate;
    }
}
