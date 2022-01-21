package personal.gym.person;

import java.io.Serializable;

import personal.gym.exception.InvalidFormatException;

/**
 * Abstract entity of those who hold an account at the #GYM facility.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public abstract class Person implements Serializable {

    private static final int MAX_NAME_LENGTH = 25;
    private static final int MAX_ADDRESS_LENGTH = 25;
    private static final int MAX_CITY_LENGTH = 14;
    private static final int PROVINCE_LENGTH = 2;
    private static final int POSTAL_CODE_LENGTH = 6;
    protected static final int CODE_LENGTH = 9;

	private String name;
	private String address;
	private String city;
	private String province;
	private String postalCode;
    private String email;
	private Status status;
    protected String code;

	/**
     * Verifies whether the different fields of a person respect the imposed
     * format.
	 * 
	 * @param name Person name
	 * @param address Person address
     * @param city Person city
     * @param province Person province
	 * @param postalCode Person postalCode
     * @param email Person email
     * @throws InvalidFormatException if any of the provided attributes does not
     * respect the imposed format
	 */
	public static void verifyFormat(String name, String address, String city,
        String province, String postalCode, String email)
        throws InvalidFormatException {
		
        if (name.length() > MAX_NAME_LENGTH
            || !name.matches("([A-Z][a-z]*\\s[A-Z][a-z]*)")) {
            
            throw new InvalidFormatException("name");
        }

        if (address.length() > MAX_ADDRESS_LENGTH) {
            
            throw new InvalidFormatException("address");
        }

        if (city.length() > MAX_CITY_LENGTH) {
            
            throw new InvalidFormatException("city");
        }

        if (province.length() != PROVINCE_LENGTH) {
            
            throw new InvalidFormatException("province");
        }

        if (postalCode.length() != POSTAL_CODE_LENGTH
            || ! postalCode.matches("[A-Z][0-9][A-Z][0-9][A-Z][0-9]")) {
            
            throw new InvalidFormatException("postalCode");
        }
		
		if (! email.matches("(.)+@facebook\\.com")) {
            
            throw new InvalidFormatException("email");
        }
	}

	/**
     * Creates a person using the provided fields after verifying their format.
	 *
	 * @param name Person name
	 * @param address Person address
     * @param city Person city
     * @param province Person province
	 * @param postalCode Person postal code
	 * @param email Person email
     * @throws InvalidFormatException if any of the fields does not respect the
     * imposed format
	 */
	public Person(String name, String address, String city, String province,
        String postalCode, String email) throws InvalidFormatException {
		
        verifyFormat(name, address, city, province, postalCode, email);
        
        this.name = name;
        this.address = address;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.email = email;
        status = Status.VALID;
	}

    /**
     * @return Person name
     */
	public String getName() {
        
		return name;
	}

	/**
	 * @param name Person name
	 */
	public void setName(String name) {
        
		this.name = name;
	}

    /**
	 * @return Person address
	 */
	public String getAddress() {
        
		return address;
	}

	/**
	 * @param address Person address
	 */
	public void setAddress(String address) {
        
		this.address = address;
	}

    /**
     * @return Person city
     */
    public String getCity() {
        
        return city;
    }

    /**
     * @param city Person city
     */
    public void setCity(String city) {
        
        this.city = city;
    }

    /**
     * @return Person province
     */
    public String getProvince() {
        
        return province;
    }

    /**
     * @param province Person province
     */
    public void setProvince(String province) {
        
        this.province = province;
    }

    /**
	 * @return Person postal code
	 */
	public String getPostalCode() {
        
		return postalCode;
	}

	/**
	 * @param postalCode Person postal code
	 */
	public void setPostalCode(String postalCode) {
        
		this.postalCode = postalCode;
	}

    /**
     * @return Person email
     */
    public String getEmail() {
        
        return email;
    }
    
    /**
     * @param email Person email
     */
	public void setEmail(String email) {
        
		this.email = email;
	}

    /**
	 * @return Person status
	 */
	public Status getStatus() {
        
		return status;
	}

	/**
	 * @param status Person status
	 */
	public void setStatus(Status status) {
        
		this.status = status;
	}
    
    /**
	 * @return Person code
	 */
    public String getCode() {
        
        return code;
    }
}