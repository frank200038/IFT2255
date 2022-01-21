package personal.gym.session;

import java.io.Serializable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import personal.gym.accounting.AccountingUtils;

import personal.gym.service.Service;

import personal.gym.util.Day;

/**
 * Entity describing information relative to a session offered at the #GYM
 * facility.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public class Session implements Serializable {
    
    private String serviceName;
    private Day occurrence;
    private Date serviceTime;
    private int capacityMax;
    private int remainCapacity;
    private int fee;
    private String profNo;
    private String sessionNo;

    /**
     * Creates a session using the provided fields.
     *
     * @param serviceName Service name
     * @param occurrence Session occurrence day
     * @param serviceTime Session time of day
     * @param capacityMax Session maximum capacity
     * @param fee Session fee
     * @param profNo Professional number
     * @param sessionNo Session number
     */
    public Session(String serviceName, Day occurrence, Date serviceTime,
        int capacityMax, int fee, String profNo, String sessionNo) {

        this.serviceName = serviceName;
        this.occurrence = occurrence;
        this.serviceTime = serviceTime;
        this.capacityMax = capacityMax;
        remainCapacity = capacityMax;
        this.fee = fee;
        this.profNo = profNo;
        this.sessionNo = sessionNo;
    }

    /**
     * Creates a session from a provided service and its specific occurrence and
     * session number.
     *
     * @param service Service from which session is derived
     * @param occurrence Session occurrence day
     * @param sessionNo Session number
     */
    public Session(Service service, Day occurrence, String sessionNo) {
        
        this(service.getName(), occurrence, service.getServiceTime(),
        service.getCapacityMax(), service.getFee(), service.getProfNo(),
        sessionNo);
    }

    /**
     * @return Service name
     */
    public String getServiceName() {
        
        return serviceName;
    }

    /**
     * @return Session occurrence day
     */
    public Day getOccurrence() {
        
        return occurrence;
    }

    /**
     * @return Session remaining capacity
     */
    public int getRemainCapacity() {
        
        return remainCapacity;
    }

    /**
     * @param remainCapacity Session remaining capacity
     */
    public void setRemainCapacity(int remainCapacity) {
        
        this.remainCapacity = remainCapacity;
    }

    /**
     * @return Session fee
     */
    public int getFee() {
        
        return fee;
    }

    /**
     * @return Professional number
     */
    public String getProfNo() {
        
        return profNo;
    }

    /**
     * String implementation of a Session.
     *
     * @return String implementation of a Session
     */
    @Override
    public String toString() {

        return
        "\n\t\tName - " + serviceName +
        "\n\t\tOccurrence - " + occurrence +
        "\n\t\tSession number - " + sessionNo +
        "\n\t\tMax capacity - " + capacityMax +
        "\n\t\tRemaining capacity - " + remainCapacity +
        "\n\t\tFee - $" + new DecimalFormat("0.00").format(
        (fee / AccountingUtils.CENTS_IN_DOLLAR)) +
        "\n\t\tService Time - "
        + new SimpleDateFormat("HH-mm").format(serviceTime) +
        "\n\t\tProfessional number - " + profNo + "\n";
    }
    
    /**
     * Specifies conditions under which two {@link Session} objects are equal.
     *
     * @param o Object of comparison
     * @return {@code true} if both keys are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        
        if (this == o) return true;
        if (! (o instanceof Session)) return false;
        
        Session session = (Session) o;
        
        return
        serviceName.equals(session.serviceName) &&
        occurrence.equals(session.occurrence) &&
        serviceTime.equals(session.serviceTime) &&
        capacityMax == session.capacityMax &&
        fee == session.fee &&
        profNo.equals(session.profNo) &&
        sessionNo.equals(session.sessionNo);
    }
}