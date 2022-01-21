package personal.gym.receipt;

import java.io.Serializable;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import personal.gym.person.Member;

/**
 * Describes a member's tab for the week.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public class BillMember extends Receipt {

    private List<BillMemberData> sessionsInfo;

    /**
     * Initializes a member's bill.
     *
     * @param recipient Member bill recipient
     */
    public BillMember(Member recipient) {
        
        super(recipient);
        sessionsInfo = new ArrayList<>();
    }

    /**
     * Adds a session attended by a member bill recipient to list of sessions
     * attended.
     *
     * @param sessionDate Session sessionDate
     * @param profName Professional name
     * @param serviceName Service name
     */
    public void addSessionInfo(Date sessionDate, String profName,
        String serviceName) {

        sessionsInfo.add(new BillMemberData(sessionDate, profName,
        serviceName));
    }

    /**
     * String implementation of bill.
     *
     * @return String implementation of bill.
     */
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder(super.toString());

        sb.append("--------------------------------------------------------\n");
        
        for (BillMemberData sessionInfo : sessionsInfo) {
            
            sb.append(sessionInfo.toString());
        }
        
        sb.append("--------------------------------------------------------");

        return sb.toString();
    }

    /**
     * Specifies additional information present on a member's bill.
     */
    private class BillMemberData implements Serializable {
        
        private Date sessionDate;
        private String profName;
        private String serviceName;

        /**
         * Initializes the information relative a member's bill.
         *
         * @param sessionDate Session sessionDate
         * @param profName Professional name
         * @param serviceName Service name
         */
        private BillMemberData(Date sessionDate, String profName,
            String serviceName) {
            
            this.sessionDate = sessionDate;
            this.profName = profName;
            this.serviceName = serviceName;
        }
        
        /**
         * String implementation of bill data.
         *
         * @return String implementation of bill data
         */
        @Override
        public String toString() {
            
            return
            "\n\tDate: " +
            new SimpleDateFormat("dd-MM-yyyy").format(sessionDate) +
            "\n\t Professional name: " + profName +
            "\n\tService name" + serviceName + "\n";
        }
    }
}
