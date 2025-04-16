package control;

import entity.*;

public class WithdrawalService {
    public static void requestWithdrawal(Application application) {
        application.setWithdrawalRequested(true);
        System.out.println("Withdrawal request has been sent.");
    }
    
    
    public static void approveWithdrawal(Application application) {
        application.withdraw();
        FlatType type = application.getFlatType();
        application.getProject().removeOccupiedFlat(type);
    }
    
}
