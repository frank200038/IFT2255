package personal.gym.receipt;

import java.util.HashMap;
import java.util.Map;

import personal.gym.person.ListPersons;

import personal.gym.session.ListSessions;
import personal.gym.session.ServicesDirectory;

import personal.gym.validation.Validation;

/**
 * Data structure for holding the different weekly receipts of members and
 * professionals affiliated with #GYM.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public class ListReceipts {

    private Map<String, BillMember> bills;
    private Map<String, PaymentNoticeProf> paymentNotices;

    private ListPersons listPersons;
    private ListSessions listSessions;
    private ServicesDirectory servicesDirectory;

    /**
     * Initializes the receipts hashmaps.
     *
     * @param bills Bills for which members are recipients
     * @param paymentNotices Payment notices for which professionals are
     * recipients
     */
    public ListReceipts(Map<String, BillMember> bills,
        Map<String, PaymentNoticeProf> paymentNotices) {

        if ((this.bills = bills) == null) {
            
            this.bills = new HashMap<>();
        }
        
        if ((this.paymentNotices = paymentNotices) == null) {
            
            this.paymentNotices = new HashMap<>();
        }
    }
    
    /**
     * @param listPersons ListPersons reference
     */
    public void setListPersons(ListPersons listPersons) {
        
        this.listPersons = listPersons;
    }

    /**
     * @param listSessions ListSessions reference
     */
    public void setListSessions(ListSessions listSessions) {
        
        this.listSessions = listSessions;
    }

    /**
     * @param servicesDirectory ServicesDirectory reference
     */
    public void setServicesDirectory(ServicesDirectory servicesDirectory) {
        
        this.servicesDirectory = servicesDirectory;
    }

    /**
     * @return Members' bills
     */
    public Map<String, BillMember> getBills() {
        
        return bills;
    }

    /**
     * @return Professionals' payment notices
     */
    public Map<String, PaymentNoticeProf> getPaymentNotices() {
        
        return paymentNotices;
    }

    /**
     * Clears the members bills and professionals payment notices maps. This
     * method should be called on a weekly basis.
     */
    public void clear() {
        
        bills.clear();
        paymentNotices.clear();
    }

    /**
     * Creates or updates a bill for a member that has recently attended a
     * session.
     *
     * @param memberNo Member number
     * @param validation Validation indicating that the member attended the
     * session
     */
    public void createBillMember(String memberNo, Validation validation) {

        BillMember billMember;

        if ((billMember = bills.get(memberNo)) == null) {
            
            billMember = new BillMember(listPersons.getMember(memberNo));
            bills.put(memberNo, billMember);
        }

        billMember.addSessionInfo(validation.getDateNow(),
        listPersons.getProf(validation.getProfNo()).getName(),
        servicesDirectory.getServiceName(validation.getServiceNo()));
    }

    /**
     * Creates or updates a payment notice for a professional that has recently
     * taught a session to a member.
     *
     * @param profNo Professional number
     * @param validation Validation indicating that a member attended the
     * session under this professional
     */
    public void createPaymentNoticeProf(String profNo, Validation validation) {

        PaymentNoticeProf paymentProf;

        if ((paymentProf = paymentNotices.get(profNo)) == null) {
            
            paymentProf = new PaymentNoticeProf(listPersons.getProf(profNo));
            paymentNotices.put(profNo, paymentProf);
        }

        String sessionNo = validation.getSessionNo();
        String memberNo = validation.getMemberNo();

        paymentProf.addSessionInfo(sessionNo, validation.getDateNow(), memberNo,
        listPersons.getMember(memberNo).getName(),
        listSessions.getSession(sessionNo).getFee());
    }
}
