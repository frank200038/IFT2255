package personal.gym.service;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Date;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import personal.gym.accounting.AccountingUtils;

import personal.gym.exception.InvalidFormatException;

import personal.gym.util.Day;

/**
 * Entity describing information relative to a service offered at the #GYM
 * facility.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public class Service implements Serializable {

    private static final int MAX_NAME_LENGTH = 20;
    private static final int MAX_CAPACITY = 30;
    private static final int MAX_COMMENT_LENGTH = 100;
    private static final int MAX_FEE = 10000; // $100.00
    private static final int PROF_NO_LENGTH = 9;
    private static final int CODE_LENGTH = 7;

	private String name;
    private Date dateNow;
	private Date startDate;
	private Date endDate;
	private Day[] occurrences;
	private int capacityMax;
	private String comment;
	private int fee;
	private Date serviceTime;
	private String profNo;
    private final String code;

    private static int currentServiceId = 0;

    /**
     * @return Counter value
     */
    public static int getCurrentServiceId() {
        
        return currentServiceId;
    }

    /**
     * @param num Counter value
     */
    public static void setCurrentServiceId(int num) {
        
        currentServiceId = num;
    }

	/**
     * Verifies whether the different fields of a service respect the imposed
     * format.
	 * 
	 * @param name Service name
	 * @param startDate Service start date
	 * @param endDate Service end date
	 * @param occurrences Service weekly recurrence
	 * @param capacityMax Service maximum capacity
     * @param comment Service comment
	 * @param fee Service fee
	 * @param profNo Service professional's number
     * @throws InvalidFormatException if a field does not respect the imposed
     * format
	 */
	public static void verifyFormat(String name, Date startDate, Date endDate,
        Day[] occurrences, int capacityMax, String comment, int fee,
        String profNo) throws InvalidFormatException {
		
        if (name.length() > MAX_NAME_LENGTH
            || ! name.matches("[A-Z][a-z]*")) {
            
            throw new InvalidFormatException("name");
        }
        
        if (startDate.after(endDate)) {
            
            throw new InvalidFormatException("startDate, endDate");
        }
        
        for (int i = 0; i < occurrences.length; i++) {
            
            for (int j = i + 1; j < occurrences.length; j++) {
                
                if (occurrences[i].equals(occurrences[j])) {
                    
                    throw new InvalidFormatException("occurrences");
                }
            }
        }
        
        if (capacityMax < 0 || capacityMax > MAX_CAPACITY) {
            
            throw new InvalidFormatException("capacityMax");
        }
        
        if (comment.length() > MAX_COMMENT_LENGTH) {
            
            throw new InvalidFormatException("comment");
        }
        
        if (fee < 0 || fee > MAX_FEE) {
            
            throw new InvalidFormatException("fee");
        }
        
        if (profNo.length() != PROF_NO_LENGTH) {
            
            throw new InvalidFormatException("profNo");
        }
	}

	/**
     * Creates a service using the provided fields after verifying their format.
     * Also generates a new service code for the service.
	 * 
	 * @param name Service name
	 * @param startDate Service start date
	 * @param endDate Service end date
	 * @param occurrences Service weekly recurrence
	 * @param capacityMax Service maximum capacity
	 * @param comment Service comment
	 * @param fee Service fee
	 * @param serviceTime Service time
	 * @param profNo Service professional's number
     * @throws ParseException in case of unexpected behaviour while parsing a
     * date
     * @throws InvalidFormatException if any of the fields does not respect the
     * imposed format
	 */
	public Service(String name, Date startDate, Date endDate, Day[] occurrences,
        int capacityMax, String comment, int fee, Date serviceTime,
        String profNo) throws ParseException, InvalidFormatException {
        
        verifyFormat(name, startDate, endDate, occurrences, capacityMax,
        comment, fee, profNo);
        
        this.name = name;
        dateNow = new Date(System.currentTimeMillis());
        this.startDate = startDate;
        this.endDate = endDate;
        this.occurrences = occurrences;
        this.capacityMax = capacityMax;
        this.comment = comment;
        this.fee = fee;
        this.serviceTime = serviceTime;
        this.profNo = profNo;
        code = generateServiceNo();
	}

    /**
	 * @return Service name
	 */
	public String getName() {
        
		return name;
	}

	/**
	 * @param name Service name
	 */
	public void setName(String name) {
        
		this.name = name;
	}

    /**
     * @return Service start date
     */
	public Date getStartDate() {
        
		return startDate;
	}

	/**
	 * @param startDate Service start date
	 */
	public void setStartDate(Date startDate) {
        
		this.startDate = startDate;
	}

    /**
	 * @return endDate Service end date
	 */
	public Date getEndDate() {
        
		return endDate;
	}

	/**
	 * @param endDate Service end date
	 */
	public void setEndDate(Date endDate) {
        
		this.endDate = endDate;
	}

    /**
     * @return Service weekly recurrence
     */
	public Day[] getOccurrences() {
        
		return occurrences;
	}

	/**
	 * @param occurrences Service weekly recurrence
	 */
	public void setOccurrences(Day[] occurrences) {
        
		this.occurrences = occurrences;
	}

    /**
	 * @return Service maximum capacity
	 */
	public int getCapacityMax() {
        
		return capacityMax;
	}

	/**
	 * @param capacityMax Service maximum capacity
	 */
	public void setCapacityMax(int capacityMax) {
        
		this.capacityMax = capacityMax;
	}

    /**
     * @return Service comment
     */
	public String getComment() {
        
		return comment;
	}

	/**
	 * @param comment Service comment
	 */
	public void setComment(String comment) {
        
		this.comment = comment;
	}

    /**
     * @return Service fee
     */
	public int getFee() {
        
		return fee;
	}

	/**
	 * @param fee Service fee
	 */
	public void setFee(int fee) {
        
		this.fee = fee;
	}

    /**
     * @return Service time
     */
	public Date getServiceTime() {
        
		return serviceTime;
	}

	/**
	 * @param serviceTime Service time
	 */
	public void setServiceTime(Date serviceTime) {
        
		this.serviceTime = serviceTime;
	}

    /**
     * @return Service professional's number
     */
	public String getProfNo() {
        
		return profNo;
	}

	/**
	 * @param profNo Service professional's number
	 */
	public void setProfNo(String profNo) {
        
		this.profNo = profNo;
	}
    
    /**
	 * @return Service code
	 */
    public String getCode() {
        
		return code;
	}
    
    /**
     * Generates a unique service code for a newly created service.
     *
     * @return Service code
     */
    private String generateServiceNo() {
		
        String code = String.valueOf(currentServiceId++);

        return "0".repeat(CODE_LENGTH - code.length()) + code;
	}
    
    /**
     * String implementation of a service.
     *
     * @return String implementation of a service
     */
    public String toString() {
        
        return
        "\n\t\tService name: " + name +
        "\n\t\tActual date: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        .format(dateNow) +
        "\n\t\tStart date: " + new SimpleDateFormat("dd-MM-yyyy")
        .format(startDate) +
        "\n\t\tEnd date: " + new SimpleDateFormat("dd-MM-yyyy")
        .format(endDate) +
        "\n\t\tWeekly recurrence: " + Arrays.toString(occurrences) +
        "\n\t\tCapacity: " + capacityMax +
        "\n\t\tComment: " + comment +
        "\n\t\tService fee: $" + new DecimalFormat("0.00").format(
        fee / AccountingUtils.CENTS_IN_DOLLAR) +
        "\n\t\tService time: " + new SimpleDateFormat("HH:mm")
        .format(serviceTime) +
        "\n\t\tProfessional number: " + profNo +
        "\n\t\tService number: " + code + "\n";
    }
}