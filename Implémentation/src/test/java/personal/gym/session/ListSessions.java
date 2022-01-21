package personal.gym.session;

import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.ZoneId;

import java.time.temporal.TemporalAdjusters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import java.util.Map;

import personal.gym.service.*;

import personal.gym.util.Day;

/**
 * Data structure holding the set of available sessions. Sessions are recycled,
 * registrations to those sessions however are cleared each week along with the
 * accounting procedure.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public class ListSessions {

    private Map<String, Session> sessions;
    
    private ListServices listServices;
    private ServicesDirectory servicesDirectory;

    /**
     * Initializes the sessions hashmap
     *
     * @param sessions Sessions hashmap
     */
    public ListSessions(Map<String, Session> sessions) {
        
        if ((this.sessions = sessions) == null) {
            
            this.sessions = new HashMap<>();
        }
    }

    /**
     * @param listServices ListServices reference
     */
    public void setListServices(ListServices listServices) {
        
        this.listServices = listServices;
    }

    /**
     * @param servicesDirectory ServicesDirectory reference
     */
    public void setServicesDirectory(ServicesDirectory servicesDirectory) {
        
        this.servicesDirectory = servicesDirectory;
    }

    /**
     * @return Sessions map
     */
    public Map<String, Session> getSessions() {
        
        return sessions;
    }

    /**
     * Clears the sessions map and refreshes its contents. This method should be
     * called on a weekly basis.
     */
    public void clear() {
       
       sessions.clear();
       refreshSessions();
    }
    
    /**
     * Retrieves the session associated with the given session number or null
     * if no session associated.
     *
     * @param sessionNo Session number
     * @return Session with associated number or null if no associated number
     */
    public Session getSession(String sessionNo) {

        return sessions.get(sessionNo);
    }

    /**
     * Retrieves the list of sessions available this very day.
     *
     * @return Collection of sessions available today
     */
    public Collection<Session> getSessionsToday() {

        Collection<Session> sessionsToday = new ArrayList<>();

        Day day = Day.valueOf(((
        new SimpleDateFormat("EEEE")).format(System.currentTimeMillis())).
        toUpperCase());

        for (Session s : sessions.values()) {

            if(s.getOccurrence().equals(day)) {
                
                sessionsToday.add(s);
            }
        }
        
        return sessionsToday;
    }

    /**
     * Generates a session number according to a particular format: <ul><li>The
     * first 3 digits correspond to a service code acquired from the
     * {@link ServicesDirectory} class</li><li>The next 2 digits correspond to
     * a session number retrieved from an occurrence code set in the {@link Day}
     * Enum</li><li>The last 2 digits correspond to the last 2 digits of the
     * professional's professional number who is in charge of teaching the
     * session</li></ul>
     *
     * @param serviceName Service name for acquiring a service code
     * @param occurrence Session occurrence
     * @param profNo Professional number
     */
    private String generateSessionNo(String serviceName, Day occurrence,
        String profNo) {
        
        return
        servicesDirectory.obtainServiceNo(serviceName) + occurrence.getCode() +
        profNo.substring(profNo.length() - 2);
    }

    /**
     * Updates the available sessions following any service changes regarding
     * the creation or modification of said service.
     *
     * @param service Newly created or modified service
     */
    public void updateSessions(Service service) {
        
        for (Day occurrence : service.getOccurrences()) {
            
            String sessionNo = generateSessionNo(service.getName(), occurrence,
            service.getProfNo());
            
            Session session;
            
            if ((session = sessions.get(sessionNo)) == null) {
                
                if (isWithinDateInterval(service.getStartDate(),
                    service.getEndDate(), occurrence)) {
                    
                    sessions.put(sessionNo, new Session(service, occurrence,
                    sessionNo));
                }
            } else {
                
                if (! session.equals(new Session(service, occurrence,
                    sessionNo)) && isWithinDateInterval(service.getStartDate(),
                    service.getEndDate(), occurrence)) {
                    
                    sessions.replace(sessionNo, session);
                }
            }
        }
    }

    /**
     * Procedure called each week along with accounting procedures to refresh
     * the list of sessions and repopulate it according to the available
     * services.
     */
    public void refreshSessions() {
        
        Collection<Service> services = listServices.getServices().values();
        
        for (Service service : services) {
            
            for (Day occurrence : service.getOccurrences()) {
                
                if (isWithinDateInterval(service.getStartDate(),
                    service.getEndDate(), occurrence)) {
                    
                    String serviceName = service.getName();
                    String profNo = service.getProfNo();
                    
                    String sessionNo = generateSessionNo(serviceName,
                    occurrence, profNo);
                    
                    sessions.put(sessionNo, new Session(serviceName,
                    occurrence, service.getServiceTime(),
                    service.getCapacityMax(), service.getFee(), profNo,
                    sessionNo));
                }
            }
        }
    }

    /**
     * Deletes sessions after a service under a professional was deleted.
     *
     * @param serviceName Service name
     * @param profNo Professional number
     */
    public void deleteSessionsNameProf(String serviceName, String profNo) {
    
        for (Map.Entry<String, Session> e : sessions.entrySet()) {
    
            if (e.getValue().getProfNo().equals(profNo)
                && e.getValue().getServiceName().equals(serviceName)) {
    
                sessions.remove(e.getKey());
            }
        }
    }
    
    /**
     * Deletes sessions under a professional.
     *
     * @param profNo Professional number
     */
    public void deleteSessionsProf(String profNo) {
        
        for (Map.Entry<String, Session> e : sessions.entrySet()) {
            
            if (e.getValue().getProfNo().equals(profNo)) {
                
                sessions.remove(e.getKey());
            }
        }
    }
    
    /**
     * Verifies whether a given day is within a given start and end date
     * interval
     *
     * @param startDate Start date
     * @param endDate End date
     * @param occurrence Day of the week
     */
    private static boolean isWithinDateInterval(Date startDate, Date endDate,
        Day occurrence) {
        
        Date sessionDate = Date.from((LocalDate.now()
        .with(TemporalAdjusters.nextOrSame(occurrence.getDay())))
        .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        // Session starts on start/end dates inclusively or within boundaries
        return
        (startDate.compareTo(sessionDate) == 0 || startDate.before(sessionDate))
        && (endDate.compareTo(sessionDate) == 0 || endDate.after(sessionDate));
    }
}
