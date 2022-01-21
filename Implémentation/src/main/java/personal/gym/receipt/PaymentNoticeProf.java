package personal.gym.receipt;

import java.io.Serializable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import personal.gym.accounting.AccountingUtils;

import personal.gym.person.Professional;

/**
 * Describes a professional's payment notice for the week.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public class PaymentNoticeProf extends Receipt {

    private Map<PaymentNoticeProfKey, PaymentNoticeProfData> sessionsInfo;

    /**
     * Initializes a professional's payment notice.
     *
     * @param recipient Professional payment notice recipient
     */
    public PaymentNoticeProf(Professional recipient) {
        
        super(recipient);
        sessionsInfo = new HashMap<>();
    }

    /**
     * Adds or updates a session taught by a professional payment notice
     * recipient to list of sessions taught.
     *
     * @param sessionNo Session number
     * @param sessionDate Session date
     * @param memberNo Member number
     * @param memberName Member name
     * @param balance Session fee
     */
    public void addSessionInfo(String sessionNo, Date sessionDate, String memberNo,
        String memberName, int balance) {
        
        PaymentNoticeProfData sessionInfo;
        PaymentNoticeProfKey sessionKey =
        new PaymentNoticeProfKey(sessionNo, sessionDate);

        if ((sessionInfo = sessionsInfo.get(sessionKey)) == null) {
            
            sessionsInfo.put(sessionKey, new PaymentNoticeProfData(memberName,
            memberNo, balance));
        } else {

            sessionInfo.members.put(memberNo, memberName);
            sessionInfo.balance += balance;
        }
    }

    /**
     * String implementation of payment notice.
     *
     * @return String implementation of payment notice
     */
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder(super.toString());
        
        sb.append("--------------------------------------------------------\n");
        
        for (Map.Entry<PaymentNoticeProfKey, PaymentNoticeProfData> e :
            sessionsInfo.entrySet()) {
            
            sb.append(e.getKey().toString());
            sb.append(e.getValue().toString());
        }
        
        sb.append("--------------------------------------------------------");

        return sb.toString();
    }

    /**
     * Specifies the unique information per session information on a
     * professional's payment notice.
     */
    private class PaymentNoticeProfKey implements Serializable {
        
        private final String sessionNo;
        private final Date sessionDate;
        
        /**
         * Initializes the unique information relative to a professional's
         * payment notice.
         *
         * @param sessionNo Session number
         * @param sessionDate Session date
         */
        private PaymentNoticeProfKey(String sessionNo, Date sessionDate) {
            
            this.sessionNo = sessionNo;
            this.sessionDate = sessionDate;
        }
        
        /**
         * Specifies conditions under which two {@link PaymentNoticeProfKey}
         * objects are equal.
         *
         * @param o Object of comparison
         * @return {@code true} if both keys are equal, {@code false} otherwise
         */
        @Override
        public boolean equals(Object o) {
            
            if (this == o) return true;
            if (!(o instanceof PaymentNoticeProfKey)) return false;
            
            PaymentNoticeProfKey key = (PaymentNoticeProfKey) o;
            return sessionNo.equals(key.sessionNo) &&
            sessionDate.equals(key.sessionDate);
        }

        /**
         * Generates a hash code value for the {@link PaymentNoticeProfKey}
         * object. This method is implemented so as to permit to use this object
         * as a key in a Map.
         *
         * @return hash code value for this object
         */
        @Override
        public int hashCode() {
            
            return super.hashCode();
        }

        /**
         * String implementation of payment notice key.
         *
         * @return String implementation of payment notice key
         */
        @Override
        public String toString() {
            
            return
            "\n\tSession date: " +
            new SimpleDateFormat("dd-MM-yyyy").format(sessionDate) +
            "\n\tSession number: " + sessionNo;
        }
    }

    /**
     * Specifies additional information present on a professional's payment
     * notice.
     */
    private class PaymentNoticeProfData implements Serializable {
        
        private Date dateNow;
        private Map<String, String> members;
        private int balance;

        /**
         * Initializes the information relative a professional's payment notice.
         *
         * @param memberNo Member number
         * @param memberName Member name
         * @param balance Service fee
         */
        private PaymentNoticeProfData(String memberNo, String memberName,
            int balance)  {

            dateNow = new Date(System.currentTimeMillis());
            members = new HashMap<>();
            members.put(memberNo, memberName);
            this.balance = balance;
        }
        
        /**
         * String implementation of payment notice.
         *
         * @return String implementation of payment notice
         */
        @Override
        public String toString() {
            
            StringBuilder sb =
            new StringBuilder("\n\tInformation retrieval date: " +
            new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dateNow));
            
            for (Map.Entry<String, String> e : members.entrySet()) {
                
                sb.append("\n\tMember name: " + e.getValue());
                sb.append("\n\tMember code: " + e.getKey());
            }
            
            sb.append("\n\tTotal balance: $" + new DecimalFormat("0.00").format(
            (balance / AccountingUtils.CENTS_IN_DOLLAR)) + "\n");
            
            return sb.toString();
        }
    }
}
