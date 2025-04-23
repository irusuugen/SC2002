package control;

import entity.HDBManager;

public interface IManagerApplicationService {
    void processApplication(HDBManager manager);
    void processWithdrawal(HDBManager manager);
}
