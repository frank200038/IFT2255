package personal.gym.validation;

import java.io.Serializable;

import java.util.Date;

import personal.gym.exception.InvalidFormatException;

/**
 * Entity describing information relative to a registration validation generated
 * once a member participates at a session.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public class Validation implements Serializable {

    private static final int PROF_NO_LENGTH = 9;
    private static final int MEMBER_NO_LENGTH = 9;
    private static final int SESSION_NO_LENGTH = 7;
    private static final int MAX_COMMENT_LENGTH = 100;
    private static final int SERVICE_NO_LENGTH = 3;

	private String profNo;
	private String memberNo;
    private String sessionNo;
	private String comment;
	private Date dateNow;

    /**
     * Verifies whether the different fields of a validation respect the imposed
     * format.
	 * 
	 * @param profNo Validation session professional number
	 * @param memberNo Validation session member number
	 * @param sessionNo Validation session number
	 * @param comment Validation comment
     * @throws InvalidFormatException if a field does not respect the imposed
     * format
	 */
	public static void verifyFormat(String profNo, String memberNo,
        String sessionNo, String comment) throws InvalidFormatException {
		
        if (profNo.length() != PROF_NO_LENGTH) {
            
            throw new InvalidFormatException("profNo");
        }
        
        if (memberNo.length() != MEMBER_NO_LENGTH) {
            
            throw new InvalidFormatException("profNo");
        }
        
        if (sessionNo.length() != SESSION_NO_LENGTH) {
            
            throw new InvalidFormatException("profNo");
        }
        
        if (comment.length() > MAX_COMMENT_LENGTH) {
            
            throw new InvalidFormatException("profNo");
        }
	}

	/**
     * Initializes a validation indicating that a member has attended a session
     * taught by a professional.
	 * 
	 * @param memberNo Validation session member number
	 * @param profNo Validation session professional number
	 * @param sessionNo Validation session number
	 * @param comment Validation comment
     * @throws InvalidFormatException if a field does not respect the imposed
     * format
	 */
	public Validation(String profNo, String memberNo, String sessionNo,
        String comment) throws InvalidFormatException {
		
        verifyFormat(profNo, memberNo, sessionNo, comment);
        
        this.profNo = profNo;
        this.memberNo = memberNo;
        this.sessionNo = sessionNo;
        this.comment = comment;
        dateNow = new Date(System.currentTimeMillis());
	}

    /**
     * @return Validation session member number
     */
	public String getMemberNo() {
        
		return memberNo;
	}

    /**
     * @return Validation session professional number
     */
    public String getProfNo() {
		
        return profNo;
	}

    /**
     * @return Validation session number
     */
    public String getSessionNo() {
        
		return sessionNo;
	}

    /**
     * @return Validation date of creation
     */
	public Date getDateNow() {
        
		return dateNow;
	}
    
    /**
     * Extracts the service number from the session number.
     *
     * @return Service number
     */
    public String getServiceNo() {
        
        return sessionNo.substring(0, SERVICE_NO_LENGTH);
    }
}