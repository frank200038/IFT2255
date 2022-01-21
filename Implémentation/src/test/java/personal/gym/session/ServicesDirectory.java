package personal.gym.session;

import personal.gym.service.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Special class used to generate a specific service number for a service which
 * is independent of the creation of services under specific professionals.
 * This implies that two services with the same name but created through
 * different professionals, while the service number generated from the creation
 * of those services in the {@link Service} class might differ, the service
 * number generated in this class does not distinguish them.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public class ServicesDirectory {

    private static final int SERVICE_NO_LENGTH = 3;
    
    // Two equivalent hashmaps to facilitate search and promote unity of both
    // keys and values
    private Map<String, String> servicesNameNo; // <name, number>
    private Map<String, String> servicesNoName; // <number, name>
    
    private static int noElements;

    /**
     * @return Counter value
     */
    public static int getNoElements() {
        
        return noElements;
    }

    /**
     * @param num Counter value
     */
    public static void setNoElements(int num) {
        
        noElements = num;
    }

    /**
     * Initializes the hashmaps.
     *
     * @param servicesNameNo Hashmap with the service name as its key and
     * the number as its value
     * @param servicesNoName Hashmap with the service number as its key and
     * the name as its value
     */
    public ServicesDirectory(Map<String, String> servicesNameNo,
        Map<String, String> servicesNoName) {
        
        if ((this.servicesNameNo = servicesNameNo) == null) {
            
            this.servicesNameNo = new HashMap<>();
        }
        
        if ((this.servicesNoName = servicesNoName) == null) {
            
            this.servicesNoName = new HashMap<>();
        }
    }

    /**
     * @return Map with the service name as its key and the number as its value.
     */
    public Map<String, String> getServicesNameNo() {
        
        return servicesNameNo;
    }
    
    /**
     * @return Map with the service number as its key and the name as its value.
     */
    public Map<String, String> getServicesNoName() {
        
        return servicesNoName;
    }

    /**
     * Retrieves or generates a service number for a given service name.
     *
     * @param serviceName Name of the service
     * @return Service number of the given service
     */
    public String obtainServiceNo(String serviceName) {
        
        String serviceNo;
        
        if ((serviceNo = servicesNameNo.get(serviceName)) == null) {
            
            String elemNo = String.valueOf(noElements++);
            serviceNo = "0".repeat(SERVICE_NO_LENGTH - elemNo.length()) +
            elemNo;
            
            servicesNameNo.put(serviceName, serviceNo);
            servicesNoName.put(serviceNo, serviceName);
        }
        
        return serviceNo;
    }

    /**
     * Retrieves a service name from its given number.
     *
     * @param serviceNo Service number
     * @return Service name
     */
    public String getServiceName(String serviceNo) {
        
        return servicesNoName.get(serviceNo);
    }
}
