package personal.gym.accounting;

import java.io.Serializable;

/**
 * Entity describing information to represent on a TEF file.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */

public class TEF implements Serializable {

	private String profName;
	private String profNo;
	private double weeklyProfRevenueAmount;

	/**
     * Initializes the information present on a TEF file.
	 * 
	 * @param profName Professional name
	 * @param profNo Professional code
	 * @param weeklyProfRevenueAmount Weekly revenue accumulated by the
     * professional
	 */
	public TEF(String profName, String profNo, double weeklyProfRevenueAmount) {
		
        this.profName = profName;
        this.profNo = profNo;
        this.weeklyProfRevenueAmount = weeklyProfRevenueAmount;
	}

    /**
     * @return Professional name
     */
    public String getProfName() {
        
		return profName;
	}
    
    /**
     * @return Professional code
     */
    public String getProfNo() {
        
        return profNo;
    }
}