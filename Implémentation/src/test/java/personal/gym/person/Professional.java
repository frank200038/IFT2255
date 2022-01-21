package personal.gym.person;

import personal.gym.exception.InvalidFormatException;

/**
 * Holds information relative to a professional at #GYM.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public class Professional extends Person {
    
	private static int currentProfId = 0;

    /**
     * @return Counter value
     */
    public static int getCurrentProfId() {
        
        return currentProfId;
    }

    /**
     * @param num Counter value
     */
    public static void setCurrentProfId(int num) {
        
        currentProfId = num;
    }

	/**
     * Creates a professional using the provided fields. Also generates a unique
     * professional number for the newly created professional.
	 * 
	 * @param name Professional name
	 * @param address Professional address
     * @param city Professional city
     * @param province Professional province
	 * @param postalCode Professional postal code
     * @param email Professional email
     * @throws InvalidFormatException if any of the provided attributes does not
     * respect the imposed format
	 */
	public Professional(String name, String address, String city,
        String province, String postalCode, String email)
        throws InvalidFormatException {

        super(name, address, city, province, postalCode, email);
        code = generateProfNo();
	}

    /**
     * Generates a unique professional code for a newly created professional.
     *
     * @return Professional code
     */
	private static String generateProfNo() {
		
        String code = String.valueOf(currentProfId++);
        
        return "0".repeat(CODE_LENGTH - code.length()) + code;
	}
}