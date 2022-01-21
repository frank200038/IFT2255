package personal.gym.service;

import java.text.ParseException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import personal.gym.exception.InvalidFormatException;

import personal.gym.accounting.AccountingUtils;

import personal.gym.session.ListSessions;

import personal.gym.util.Day;

import personal.gym.validation.ListValidations;

/**
 * Data structure and a secondary controller of the app. Serves as a controller
 * for {@link ListSessions}. This list serves as the contractual engagement
 * between #GYM and its professionals. Can also be interpreted as the master
 * schedule for #GYM's service offering. On a functional level, this class is
 * used to generate and modify {@link ListSessions}.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public class ListServices {

	private Map<String, Service> services;

	private ListSessions listSessions;
	private ListValidations listValidations;

    /**
     * Initializes services hashmap.
     *
     * @param services Services hashmap
     */
    public ListServices(Map<String, Service> services) {
        
        if ((this.services = services) == null) {
            
            this.services = new HashMap<>();
        }
    }

    /**
     * @param listSessions ListSessions reference
     */
    public void setListSessions(ListSessions listSessions) {
        
        this.listSessions = listSessions;
    }

    /**
     * @param listValidations ListValidations reference
     */
    public void setListValidations(ListValidations listValidations) {
        
        this.listValidations = listValidations;
    }

    /**
     * @return Services map
     */
    public Map<String, Service> getServices() {
        
		return services;
	}

    /**
     * Retrieves the service associated with the given service number or null
     * if no service associated.
     *
     * @param serviceNo Service number
     * @return Associated service or null if no number associated
     */
	public Service getService(String serviceNo) {

        return services.get(serviceNo);
	}

	/**
     * Creates a new service and adds it onto list of services. Also generates
     * the appropriate sessions.
	 * 
	 * @param name Service name
	 * @param startDate Service start date
	 * @param endDate Service end date
	 * @param occurrences Service weekly recurrence
	 * @param capacityMax Service maximum capacity
	 * @param comment Service comment
	 * @param fee Service fee
	 * @param serviceTime Service time
	 * @param profNo Service professional's number
     * @return Created service
     * @throws ParseException if a parsing error occurs
     * @throws InvalidFormatException if any of the provided attributes does not
     * respect the imposed format
	 */
	public Service createService(String name, Date startDate, Date endDate,
        Day[] occurrences, int capacityMax, String comment, int fee,
        Date serviceTime, String profNo) throws ParseException,
        InvalidFormatException {

        Service service = new Service(name, startDate, endDate, occurrences,
        capacityMax, comment, fee, serviceTime, profNo);

        services.put(service.getCode(), service);

        listSessions.updateSessions(service);
        
        return service;
	}

	/**
     * Modifies information relative to a service. Also updates existing
     * sessions if affected.
	 * 
	 * @param service Service to modify
	 * @param name Modified Service name
	 * @param startDate Modified Service start date
	 * @param endDate Modified Service end date
     * @param occurrences Modified Service weekly recurrence
	 * @param capacityMax Modified Service maximum capacity
	 * @param comment Modified Service comment
	 * @param fee Modified Service fee
	 * @param serviceTime Modified Service time
	 * @param profNo Modified Service professional's number
     * @throws InvalidFormatException if any of the provided attributes does not
     * respect the imposed format
     * @throws ParseException if a parse error occurs
	 */
	public void modifyService(Service service, String name, Date startDate,
        Date endDate, Day[] occurrences, int capacityMax, String comment,
        int fee, Date serviceTime, String profNo) throws InvalidFormatException,
        ParseException {

        Service.verifyFormat(name, startDate, endDate, occurrences, capacityMax,
        comment, fee, profNo);

        service.setName(name);
        service.setStartDate(startDate);
        service.setEndDate(endDate);
        service.setOccurrences(occurrences);
        service.setCapacityMax(capacityMax);
        service.setComment(comment);
        service.setFee(fee);
        service.setServiceTime(serviceTime);
        service.setProfNo(profNo);

        listSessions.updateSessions(service);
	}

	/**
     * Deletes an existing service if no active validations currently exist.
     * Also deletes the service's associated sessions.
	 * 
	 * @param serviceNo Service number
     * @return Deleted service or null if none deleted
	 */
	public Service deleteService(String serviceNo) {

        if (listValidations.isValidationExisting(serviceNo)) {
            
            return null;
        } else {
            
            try {
                
                Service service = services.remove(serviceNo);
                
                listSessions.deleteSessionsNameProf(service.getName(),
                service.getProfNo());
                return service;
            } catch(NullPointerException e) {
                
                return null;
            }
        }
	}

    /**
     * Deletes all services under a professional who is quitting the #GYM
     * facility. Also deletes the existing sessions under that professional.
	 * 
	 * @param profNo Professional number
	 */
	public void deleteServicesProf(String profNo) {
    
        for (Map.Entry<String, Service> e : services.entrySet()) {
    
            if (e.getValue().getProfNo().equals(profNo)) {
    
                listSessions.deleteSessionsProf(profNo);
                services.remove(e.getKey());
            }
        }
	}
}