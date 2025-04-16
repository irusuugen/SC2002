package control;

import entity.Application;

public class HDBOfficerApplicationController{

	public void updateApplication(Application application)
	{
		application.getProject().addOccupiedFlat(application.getFlatType());
		application.markBooked();
	}
	
}
