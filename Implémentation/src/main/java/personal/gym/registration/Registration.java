package personal.gym.registration;

import java.io.Serializable;

import java.text.SimpleDateFormat;

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
public class Registration implements Serializable {

    private static final int SESSION_NO_LENGTH = 7;
    private static final int MEMBER_NO_LENGTH = 9;
    private static final int PROFESSIONAL_NO_LENGTH = 9;
    private static final int MAX_COMMENT_LENGTH = 100;

	private String sessionNo;
	private String memberNo;
	private String profNo;
    private String comment;
	private Date dateNow;
	private Date dateSession;

    /**
     * Verifies whether the different fields of a registration respect the
     * imposed format.
     *
     * @param sessionNo Registration session number
     * @param memberNo Registration session member number
     * @param profNo Registration session professional number
     * @param comment Registration comment
     * @throws InvalidFormatException if any of the provided attributes does not
     * respect the imposed format
     */
    public static void verifyFormat(String sessionNo, String memberNo,
        String profNo, String comment) throws InvalidFormatException {
        
        if (sessionNo.length() != SESSION_NO_LENGTH) {
            
            throw new InvalidFormatException("sessionNo");
        }
        
        if (memberNo.length() != MEMBER_NO_LENGTH) {
            
            throw new InvalidFormatException("memberNo");
        }
        
        if (profNo.length() != PROFESSIONAL_NO_LENGTH) {
            
            throw new InvalidFormatException("profNo");
        }
        
        if (comment.length() > MAX_COMMENT_LENGTH) {
            
            throw new InvalidFormatException("comment");
        }
    }

	/**
     * Initializes a registration record indicating that a member has registered
     * for a session.
	 * 
	 * @param sessionNo Registration session number
	 * @param memberNo Registration session member number
	 * @param profNo Registration session professional number
	 * @param comment Registration comment
     * @throws InvalidFormatException if any of the provided attributes does not
     * respect the imposed format
	 */
	public Registration(String sessionNo, String memberNo, String profNo,
        String comment) throws InvalidFormatException {
        
        verifyFormat(sessionNo, memberNo, profNo, comment);
		
        this.sessionNo = sessionNo;
        this.memberNo = memberNo;
        this.profNo = profNo;
        this.comment = comment;
        dateNow = new Date(System.currentTimeMillis());
        dateSession = new Date(System.currentTimeMillis());
	}

    /**
	 * @return Registration session number
	 */
	public String getSessionNo() {
        
		return this.sessionNo;
	}

    /**
	 * @return Registration session member number
	 */
	public String getMemberNo() {
        
		return this.memberNo;
	}

    /**
	 * @return Registration session professional number
	 */
	public String getProfNo() {
        
		return this.profNo;
	}

    /**
     * String implementation of a registration.
     *
     * @return String implementation of a registration
     */
    @Override
    public String toString() {
        
        return
        "\n\tDate of Registration: " +
        new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dateNow) +
        "\n\tDate session: " +
        new SimpleDateFormat("dd-MM-yyyy").format(dateSession) +
        "\n\tSession number: " + sessionNo +
        "\n\tMember number: " + memberNo +
        "\n\tProfessional number: " + profNo +
        "\n\tComment: " + comment + "\n";
    }
}