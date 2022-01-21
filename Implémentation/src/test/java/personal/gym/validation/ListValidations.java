package personal.gym.validation;

import java.util.ArrayList;
import java.util.List;

import personal.gym.exception.InvalidFormatException;

import personal.gym.receipt.ListReceipts;

/**
 * Data structure holding all the registration validations accumulated.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public class ListValidations {

	private List<Validation> validations;
    
    private ListReceipts listReceipts;

    /**
     * Initializes the list of validations.
     *
     * @param validations List of validations
     */
	public ListValidations(List<Validation> validations) {
        
        if ((this.validations = validations) == null) {
            
            this.validations = new ArrayList<>();
        }
	}

    /**
     * @param listReceipts ListReceipts reference
     */
    public void setListReceipts(ListReceipts listReceipts) {
        
        this.listReceipts = listReceipts;
    }

    /**
     * @return List of validations
     */
    public List<Validation> getValidations() {
		
        return validations;
	}

    /**
     * Clears the validations list from its contents. This method should be
     * called on a weekly basis.
     */
    public void clear() {
        
        validations.clear();
    }

	/**
     * Creates a validation for a registration to a session indicating that a
     * member has attended the session offered by some professional. Also
     * creates or updates the receipts for the member and professional.
	 * 
	 * @param profNo Validation session professional number
	 * @param memberNo Validation session member number
	 * @param sessionNo Validation session number
	 * @param comment Validation comment
     * @throws InvalidFormatException if at least one of the provided attributes
     * does not respect the imposed format
	 */
	public void createValidation(String profNo, String memberNo,
        String sessionNo, String comment) throws InvalidFormatException {
		
        Validation validation = new Validation(profNo, memberNo, sessionNo,
        comment);
        validations.add(validation);
        listReceipts.createBillMember(memberNo, validation);
        listReceipts.createPaymentNoticeProf(profNo, validation);
	}

	/**
     * Verifies whether a validation exists for a given session.
	 * 
	 * @param sessionNo Session number
     * @return {@code true} if a validation exists for the given session number,
     * {@code false} otherwise
	 */
	public boolean isValidationExisting(String sessionNo) {
		
        for (Validation v : validations) {
            
            if (v.getSessionNo().equals(sessionNo)) {
                
                return true;
            }
        }
        
        return false;
	}
}