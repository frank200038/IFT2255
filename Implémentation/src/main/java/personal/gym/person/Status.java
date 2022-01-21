package personal.gym.person;

/**
 * Constants indicating possible account status.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public enum Status {
    
    VALID("Valid"),
    INVALID_NUMBER("Invalid Number"),
	SUSPENDED("Suspended");
    
    private final String message;
    
    /**
     * Initializes the status with a message.
     *
     * @param message Status message
     */
    Status(String message) {
        
        this.message = message;
    }
    
    /**
     * @return Status message
     */
    public String getMessage() {
        
        return message;
    }
}