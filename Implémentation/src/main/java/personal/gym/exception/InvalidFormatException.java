package personal.gym.exception;

/**
 * Indicates that a certain attribute retrieved from the user is in the wrong
 * imposed format.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public class InvalidFormatException extends Exception {

	/**
     * Initializes the exception by specifying the faulty attribute.
	 * 
	 * @param attribute Attribute in wrong format
	 */
	public InvalidFormatException(String attribute) {
		
        super(attribute);
	}
}