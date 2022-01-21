package personal.gym.registration;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.List;

import personal.gym.exception.InvalidFormatException;

import personal.gym.validation.ListValidations;

/**
 * Data structure holding the list of currently valid registrations for which
 * members have not yet attended the registered session.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public class ListRegistrations {

	private List<Registration> registrations;

	private ListValidations listValidations;

    /**
     * Initializes the list of registrations.
     *
     * @param registrations List of registrations
     */
    public ListRegistrations(List<Registration> registrations) {
        
        if ((this.registrations = registrations) == null) {
            
            this.registrations = new ArrayList<>();
        }
    }

    /**
     * @param listValidations ListValidations reference
     */
    public void setListValidations(ListValidations listValidations) {
        
        this.listValidations = listValidations;
    }

    /**
     * @return List of registrations
     */
    public List<Registration> getRegistrations() {
		
        return registrations;
    }

    /**
     * Clears the registrations list from its contents. This method should be
     * called on a weekly basis.
     */
    public void clear() {
        
        registrations.clear();
    }

	/**
     * Creates a new registration for a session taught by a professional for a
     * member and adds it unto the list of registrations.
	 * 
	 * @param sessionNo Session number
	 * @param memberNo Member number
	 * @param profNo Professional number
	 * @param comment Registration comment
     * @return Newly created registration
     * @throws InvalidFormatException if at least one of the provided fields
     * does not respect the imposed format
     * @throws ParseException if a parse error occurs
	 */
	public Registration createRegistration(String sessionNo, String memberNo,
        String profNo, String comment) throws InvalidFormatException,
        ParseException {
        
        Registration registration = new Registration(sessionNo, memberNo,
        profNo, comment);
        
        registrations.add(registration);
        
        return registration;
    }

	/**
     * Retrieves an existing registration for a given session by a given member
     * or null if absent.
	 * 
	 * @param memberNo Member number
	 * @param sessionNo Session number
     * @return Appropriate registration or null if absent
	 */
	public Registration getMemberRegistration(String memberNo,
        String sessionNo) {
		
        for (Registration r : registrations) {
            
            if (r.getMemberNo().equals(memberNo)
                && r.getSessionNo().equals(sessionNo)) {
                
                return r;
            }
        }
        
        return null;
    }

	/**
     * Removes the registrations for sessions under a given professional.
	 * 
	 * @param profNo Professional number
	 */
	public void removeRegistrationsProf(String profNo) {

        registrations.removeIf(r -> r.getProfNo().equals(profNo));
    }

	/**
     * Removes the registrations under a member.
	 * 
	 * @param memberNo Member number
	 */
	public void removeRegistrationsMember(String memberNo) {

        registrations.removeIf(r -> r.getMemberNo().equals(memberNo));
    }

    /**
     * Retrieves the registrations for a particular session.
     *
     * @param sessionNo Session number
     * @return List of registrations for the session
     */
    public List<Registration> consultRegistrations(String sessionNo) {
        
        List<Registration> registrationsConsult = new ArrayList<>();
        
        for (Registration r : registrations) {
            
            if (r.getSessionNo().equals(sessionNo)) {
                
                registrationsConsult.add(r);
            }
        }
        
        return registrationsConsult;
    }

    /**
     * Confirms a member's registration to a session and generates a validation
     * which indicates that the member has attended the session if the
     * registration is valid.
     *
     * @param memberNo Member number
     * @param sessionNo Session number
     * @param comment Validation comment
     * @return {@code true} if the registration is valid and thus a validation
     * has been created, {@code false} otherwise
     * @throws ParseException if parsing error occurs
     * @throws InvalidFormatException if any of the provided attributes does not
     * respect the imposed format
     */
    public boolean confirmRegistration(String memberNo, String sessionNo,
        String comment) throws ParseException, InvalidFormatException {
        
        Registration registration = getMemberRegistration(memberNo, sessionNo);
        
        if (registration == null) {
            
            return false;
        } else {
            
            listValidations.createValidation(registration.getProfNo(), memberNo,
            sessionNo, comment);
            
            return true;
        }
    }
}