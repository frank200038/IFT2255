package personal.gym.person;

import java.util.HashMap;
import java.util.Map;

import personal.gym.exception.InvalidFormatException;

import personal.gym.accounting.AccountingUtils;

import personal.gym.registration.ListRegistrations;

import personal.gym.service.ListServices;

/**
 * Data structure holding the list of members and professionals subscribed to
 * #GYM. Also holds a reference to the service registrations of each member.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public class ListPersons {

	private Map<String, Member> members;
	private Map<String, Professional> professionals;
    
    private AccountingUtils accountUtils;
	private ListRegistrations listRegistrations;
    private ListServices listServices;

    /**
     * Initializes the hashmaps for members and professionals.
     *
     * @param members Members hashmap
     * @param professionals Professionals hashmap
     */
	public ListPersons(Map<String, Member> members,
        Map<String, Professional> professionals) {
        
        if ((this.members = members) == null) {
            
            this.members = new HashMap<>();
        }
        
        if ((this.professionals = professionals) == null) {
            
            this.professionals = new HashMap<>();
        }
	}

    /**
     * @param accountUtils AccountingUtils reference
     */
    public void setAccountUtils(AccountingUtils accountUtils) {
        
        this.accountUtils = accountUtils;
    }

    /**
     * @param listRegistrations ListRegistrations reference
     */
    public void setListRegistrations(ListRegistrations listRegistrations) {
        
        this.listRegistrations = listRegistrations;
    }

    /**
     * @param listServices ListServices reference
     */
    public void setListServices(ListServices listServices) {
        
        this.listServices = listServices;
    }

    /**
     * @return Members map
     */
    public Map<String, Member> getMembers() {
        
        return members;
    }

    /**
     * @return Professionals map
     */
    public Map<String, Professional> getProfessionals() {
        
        return professionals;
    }

    /**
     * Modifies information relative to given {@link Person} after verifying
     * provided format of input.
	 *
     * @param person Person to modify
	 * @param name Modified name
	 * @param address Modified address
     * @param city Modified city
     * @param province Modified province
	 * @param postalCode Modified postal code
	 * @param email Modified email
     * @throws InvalidFormatException if any of the provided input does not
     * respect the allowed format
	 */
	public void modifyPerson(Person person, String name, String address,
        String city, String province, String postalCode, String email)
        throws InvalidFormatException {
		
        Person.verifyFormat(name, address, city, province, postalCode, email);
        
        person.setName(name);
        person.setAddress(address);
        person.setCity(city);
        person.setProvince(province);
        person.setPostalCode(postalCode);
		person.setEmail(email);
	}

    /**
     * Verifies a member's status and informs of it.
     * 
     * @param memberNo Member number
     * @return Member status
     * @throws NullPointerException if an invalid number is provided
     */
    public Status validateMember(String memberNo) throws NullPointerException {
        
        return members.get(memberNo).getStatus();
    }

    /**
     * Retrieves a member from the members hashmap using the provided member
     * number.
     *
     * @param memberNo Member number
     * @return Member associated with member number
     */
    public Member getMember(String memberNo) {
        
        return members.get(memberNo);
    }

	/**
     * Creates member and adds them onto the members hashmap.
	 * 
	 * @param name Member name
	 * @param address Member address
     * @param city Member city
     * @param province Member province
	 * @param postalCode Member postal code
	 * @param email Member email
     * @return Newly created member
     * @throws InvalidFormatException if any of the provided fields does not
     * respect the imposed format
	 */
	public Member createMember(String name, String address, String city,
        String province, String postalCode, String email)
        throws InvalidFormatException {

        Member member = new Member(name, address, city, province, postalCode,
        email);
        
        members.put(member.getCode(), member);
        
        return member;
	}

	/**
     * Deletes a member from the members hashmap using the provided member
     * number. Also removes the currently active registrations made by this
     * member.
	 * 
	 * @param memberNo Member number
     * @throws NullPointerException if an invalid number is provided
	 */
	public void deleteMember(String memberNo) throws NullPointerException {
		
        if ((members.remove(memberNo)) == null) {
            
            throw new NullPointerException();
        }
        
        listRegistrations.removeRegistrationsMember(memberNo);
	}

    /**
     * Verifies a professional's status and informs of it.
     * 
     * @param profNo Professional number
     * @return Professional status
     * @throws NullPointerException if an invalid number is provided
     */
    public Status validateProf(String profNo) throws NullPointerException {
        
        return professionals.get(profNo).getStatus();
    }

    /**
     * Retrieves a professional from the professionals hashmap using the
     * provided professional number.
     *
     * @param profNo Professional number
     * @return Professional associated with professional number
     */
    public Professional getProf(String profNo) {
        
        return professionals.get(profNo);
    }

    /**
     * Verifies whether a professional exists within the structure.
     *
     * @param profNo Professional number
     * @return {@code true} if the professional exists, {@code false} otherwise
     */
    public boolean containsProf(String profNo) {
        
        return professionals.containsKey(profNo);
    }

    /**
     * Creates professional and adds them onto the professionals hashmap.
	 * 
	 * @param name Professional name
	 * @param address Professional address
     * @param city Professional city
     * @param province Professional province
	 * @param postalCode Professional postal code
	 * @param email Professional email
     * @return Newly created professional
     * @throws InvalidFormatException if any of the provided fields does not
     * respect the imposed format
	 */
	public Professional createProf(String name, String address, String city,
        String province, String postalCode, String email)
        throws InvalidFormatException {

        Professional prof = new Professional(name, address, city, province,
        postalCode, email);
        
        professionals.put(prof.getCode(), prof);
        
        return prof;
	}

    /**
     * Deletes a professional from the professionals hashmap using the provided
     * professional number. Also removes the current member registrations to the
     * sessions taught by this professional.
	 * 
	 * @param profNo Professional number
     * @throws NullPointerException if an invalid number is provided
	 */
	public void deleteProf(String profNo) throws NullPointerException {
		
        if (professionals.remove(profNo) == null) {
            
            throw new NullPointerException();
        }
        
        accountUtils.removeSessionsProf(profNo);
        accountUtils.removeProvidedProf(profNo);
        listRegistrations.removeRegistrationsProf(profNo);
        listServices.deleteServicesProf(profNo);
	}
}