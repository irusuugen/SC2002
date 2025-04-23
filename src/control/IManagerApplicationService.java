/**
 * Interface for application management services handled by HDB Managers.
 */

package control;

import entity.HDBManager;

public interface IManagerApplicationService {
    /**
     * Processes applications submitted to the manager's projects.
     *
     * @param manager Manager processing applications
     */
    void processApplication(HDBManager manager);

    /**
     * Handles application withdrawal requests from applicants.
     *
     * @param manager Manager processing withdrawals
     */
    void processWithdrawal(HDBManager manager);
}
