package personal.gym.exception;

/**
 * Indicates that a person's status does not permit them to undergo a desired
 * action.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public class StatusException extends Exception {

	/**
     * Initializes the exception by specifying the person's status.
	 * 
	 * @param status Status of the person
	 */
	public StatusException(String status) {
		
        super(status);
	}
}