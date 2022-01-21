package personal.gym.receipt;

import java.io.Serializable;

import personal.gym.person.Person;

/**
 * Describes general information of a receipt relative to a person.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public abstract class Receipt implements Serializable {
    
    protected Person recipient;

    /**
     * Initializes a receipt by indicating the person it is meant towards.
     *
     * @param recipient Receipt recipient
     */
    public Receipt(Person recipient) {
        
        this.recipient = recipient;
    }
    
    /**
     * @return Receipt recipient
     */
    public Person getRecipient() {
        
    	return recipient;
    }
    
    /**
     * String implementation of receipt.
     *
     * @return String implementation of receipt
     */
    @Override
    public String toString() {
    
        return
        "\nName: " + recipient.getName() +
        "\nNumber: " + recipient.getCode() +
        "\nAddress: " + recipient.getAddress() +
        "\nCity: " + recipient.getCity() +
        "\nProvince: " + recipient.getProvince() +
        "\nPostal code:" + recipient.getPostalCode() + "\n";
    }
}