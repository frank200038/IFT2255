package personal.gym.person;

import personal.gym.exception.InvalidFormatException;

/**
 * Holds information relative to a member at #GYM.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public class Member extends Person {
    
	private static int currentMemberId = 0;

    /**
     * @return Counter value
     */
    public static int getCurrentMemberId() {
        
        return currentMemberId;
    }

    /**
     * @param num Counter value
     */
    public static void setCurrentMemberId(int num) {
        
        currentMemberId = num;
    }

	/**
     * Creates a member using the provided fields. Also generates a unique
     * member number for the newly created member.
	 * 
     * @param name Member name
	 * @param address Member address
     * @param city Member city
     * @param province Member province
	 * @param postalCode Member postal code
     * @param email Member email
     * @throws InvalidFormatException if any of the provided attributes does not
     * respect the imposed format
	 */
	public Member(String name, String address, String city, String province,
        String postalCode, String email) throws InvalidFormatException {
        
        super(name, address, city, province, postalCode, email);
        code = generateMemberNo();
	}

    /**
     * Generates a unique member code for a newly created member.
     *
     * @return Member code
     */
	private static String generateMemberNo() {
		
        String code = String.valueOf(currentMemberId++);
        return "0".repeat(CODE_LENGTH - code.length()) + code;
	}
}