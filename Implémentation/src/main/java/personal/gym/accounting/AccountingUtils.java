package personal.gym.accounting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.text.DecimalFormat;

import personal.gym.person.ListPersons;

import personal.gym.session.ListSessions;

/**
 * Contains various utility methods so as to facilitate accounting procedures.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */

public class AccountingUtils {

    /**
     * Number of cents present in a canadian dollar.
     */
    public static final double CENTS_IN_DOLLAR = 100.0;

	private Map<String, Integer> sessionsFee;
    private Map<String, List<String>> profsSessionsProvided;

    private ListPersons listPersons;
    private ListSessions listSessions;

    /**
     * Assigns given hashmaps to current ones or initializes them if null.
     *
     * @param sessionsFee Sessions fee hashmap
     * @param profsSessionsProvided Professionals and provided sessions hashmap
     */
    public AccountingUtils(Map<String, Integer> sessionsFee,
        Map<String, List<String>> profsSessionsProvided) {
        
        if ((this.sessionsFee = sessionsFee) == null) {
            
            this.sessionsFee = new HashMap<>();
        }
        
        if ((this.profsSessionsProvided = profsSessionsProvided) == null) {
            
            this.profsSessionsProvided = new HashMap<>();
        }
    }

    /**
     * @param listPersons ListPersons reference
     */
    public void setListPersons(ListPersons listPersons) {
        
        this.listPersons = listPersons;
    }
    
    /**
     * @param listSessions ListSessions reference
     */
    public void setListSessions(ListSessions listSessions) {
        
        this.listSessions = listSessions;
    }    

    /**
     * @return Sessions fee map
     */
	public Map<String, Integer> getSessionsFee() {
        
		return sessionsFee;
	}

    /**
     * @return Professionals and provided sessions map
     */
 	public Map<String, List<String>> getProfsSessionsProvided() {

 		return profsSessionsProvided;
 	}

    /**
     * Clears the accumulated session fees and professionals provided sessions
     * maps. This method should be called on a weekly basis.
     */
    public void clear() {
       
        sessionsFee.clear();
        profsSessionsProvided.clear();
    }

    /**
     * Calculates total balance of all sessions.
     *
     * @return Total balance of all sessions
     */
	private double getTotalFee() {
		
        int revenue = 0;
        
        for (Map.Entry<String, Integer> e : sessionsFee.entrySet()) {
            
            revenue += e.getValue();
        }
        
        return revenue / CENTS_IN_DOLLAR;
	}

    /**
	 * Adds an entry onto the sessions fee hashmap.
     *
	 * @param sessionNo Session number
	 * @param fee Income obtained from session
	 */
	public void addSessionFee(String sessionNo, int fee) {
    
        sessionsFee.put(sessionNo, fee);
	}

    /**
     * Removes the sessions and their accumulated fee.
     *
     * @param profNo Professional number
     */
    public void removeSessionsProf(String profNo) {
    
        for (String sessionNo : sessionsFee.keySet()) {
    
            if (listSessions.getSession(sessionNo).getProfNo().equals(profNo)) {
    
                sessionsFee.remove(sessionNo);
            }
        }
    }

    /**
     * Adds a session offered by the given professional or creates a new entry
     * if professional offered none before.
	 *
	 * @param profNo Professional number key
	 * @param sessionNo Session number value
	 */
	public void addProvidedProfs(String profNo, String sessionNo) {
		
        List<String> sessionNos = profsSessionsProvided.get(profNo);
        
        if (sessionNos == null) {
            
            sessionNos = new ArrayList<>();
        }
        
        sessionNos.add(sessionNo);
        profsSessionsProvided.put(profNo, sessionNos);
	}

    /**
	 * Removes sessions associated with given professional
     *
	 * @param profNo Professional number key
	 */
	public void removeProvidedProf(String profNo) {
    
        profsSessionsProvided.remove(profNo);
	}

	/**
	 * Calculates a professional's income for the week.
     *
	 * @param profNo Professional's id number
	 */
	private double calculateProfWeeklyRevenue(String profNo) {
		
        int profit = 0;
        List<String> sessions = profsSessionsProvided.get(profNo);
        
        for (String sessionNo : sessions) {
            
            profit += sessionsFee.get(sessionNo);
        }
        
        return profit / CENTS_IN_DOLLAR;
	}

    /**
     * Generates {@link TEF} objects for each professional.
     *
     * @return List of {@link TEF} objects
     */
	public List<TEF> generateTEFs() {
        
        List<TEF> tefs = new ArrayList<>();
        
        for (String profNo : profsSessionsProvided.keySet()) {
            
            tefs.add(
            new TEF(listPersons.getProfessionals().get(profNo).getName(),
            profNo, calculateProfWeeklyRevenue(profNo)));
        }
		
        return tefs;
	}

    /**
     * Generates a sessions report for the week.
     *
     * @return Information in readable String format
     */
	public String generateWeeklySessionsReport() {
		
        StringBuilder report = new StringBuilder("Weekly sessions report\n\n");
        report.append
        ("\tProfessional\t\t|\tProvided sessions\t|\tIncome\n");
        
        DecimalFormat feeFormat = new DecimalFormat("0.00");
        
        for (Map.Entry<String, List<String>> e :
            profsSessionsProvided.entrySet()) {
            
            report.append("\t");
            report.append(
            listPersons.getProfessionals().get(e.getKey()).getName());
            report.append("-" + e.getKey());
            report.append("\t|\t");
            report.append(e.getValue().size());
            report.append("\t|\t\t");
            report.append("$");
            report.append(
            feeFormat.format(calculateProfWeeklyRevenue(e.getKey())));
            report.append("\n");
        }
        
        report.append("\nTotal Professionals: " + profsSessionsProvided.size());
        report.append("\nTotal Sessions: " + sessionsFee.size());
        report.append("\nTotal Fees: $" + feeFormat.format(getTotalFee()));
        
        return report.toString();
	}
}