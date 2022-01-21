package personal.gym;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.text.SimpleDateFormat;
import java.text.ParseException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;

import java.time.temporal.TemporalAdjusters;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import personal.gym.accounting.*;

import personal.gym.exception.*;

import personal.gym.person.*;

import personal.gym.receipt.*;

import personal.gym.registration.*;

import personal.gym.service.*;

import personal.gym.session.*;

import personal.gym.util.*;

import personal.gym.validation.*;

/**
 * Main controller of the app.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public class DataCenterApplication {

    private AccountingUtils accountUtils;
    private ListPersons listPersons;
    private ListReceipts listReceipts;
    private ListRegistrations listRegistrations;
    private ListServices listServices;
    private ListSessions listSessions;
    private ListValidations listValidations;
    private ServicesDirectory servicesDirectory;
    
    /**
     * Constructor method for initializing the app controller. Loads appropriate
     * {@code .dat} files if present.
     */
    @SuppressWarnings("unchecked")
    public DataCenterApplication() {
        
        try {

            accountUtils = new AccountingUtils(
            (Map<String, Integer>)
            loadData(new File("res" + File.separator + "sessionsFee.dat")),
            (Map<String, List<String>>)
            loadData(new File("res" + File.separator +
            "profsSessionsProvided.dat")));

            listPersons = new ListPersons(
            (Map<String, Member>)
            loadData(new File("res" + File.separator + "members.dat")),
            (Map<String, Professional>)
            loadData(new File("res" + File.separator + "professionals.dat")));

            listReceipts = new ListReceipts(
            (Map<String, BillMember>)
            loadData(new File("res" + File.separator + "bills.dat")),
            (Map<String, PaymentNoticeProf>)
            loadData(new File("res" + File.separator + "paymentNotices.dat")));

            listRegistrations = new ListRegistrations(
            (List<Registration>)
            loadData(new File("res" + File.separator + "registrations.dat")));

            listServices = new ListServices(
            (Map<String, Service>)
            loadData(new File("res" + File.separator + "services.dat")));

            listSessions = new ListSessions(
            (Map<String, Session>)
            loadData(new File("res" + File.separator + "sessions.dat")));

            listValidations = new ListValidations(
            (List<Validation>)
            loadData(new File("res" + File.separator + "validations.dat")));

            servicesDirectory = new ServicesDirectory(
            (Map<String, String>)
            loadData(new File("res" + File.separator +
            "servicesNameNoDirectory")),
            (Map<String, String>)
            loadData(new File("res" + File.separator +
            "servicesNoNameDirectory")));

            accountUtils.setListPersons(listPersons);
            accountUtils.setListSessions(listSessions);
            
            listPersons.setAccountUtils(accountUtils);
            listPersons.setListRegistrations(listRegistrations);
            listPersons.setListServices(listServices);
            
            listReceipts.setListPersons(listPersons);
            listReceipts.setListSessions(listSessions);
            listReceipts.setServicesDirectory(servicesDirectory);
            
            listRegistrations.setListValidations(listValidations);
            
            listServices.setListSessions(listSessions);
            listServices.setListValidations(listValidations);
            
            listSessions.setListServices(listServices);
            listSessions.setServicesDirectory(servicesDirectory);
            
            listValidations.setListReceipts(listReceipts);
            
            Integer temp;
            
            if ((temp = (Integer)
                loadData(new File("res" + File.separator +
                "currentMemberId.dat"))) != null) {
                
                Member.setCurrentMemberId(temp);
            }
            
            if ((temp = (Integer)
                loadData(new File("res" + File.separator +
                "currentProfId.dat"))) != null) {
                
                Professional.setCurrentProfId(temp);
            }
            
            if ((temp = (Integer)
                loadData(new File("res" + File.separator +
                "currentServiceId.dat"))) != null) {
                
                Service.setCurrentServiceId(temp);
            }
            
            if ((temp = (Integer)
                loadData(new File("res" + File.separator +
                "noElements.dat"))) != null) {
                
                ServicesDirectory.setNoElements(temp);
            }
            
            // Initiates accounting procedure at the scheduled time
            new Timer().scheduleAtFixedRate(new AccountingTask(),
            Date.from((LocalDate.now().with(TemporalAdjusters.nextOrSame(
            Day.FRIDAY.getDay())).atTime(0, 0, 0))
            .atZone(ZoneId.systemDefault()).toInstant()),
            Duration.ofDays(Day.LENGTH_OF_WEEK).toMillis());
        } catch (IOException e) {
            
            showMessage("Unable to retrieve data");
        } catch (ClassNotFoundException e) {
            
            showMessage("Class not found");
        }
    }

    /**
     * Accesses and returns a given file's contents.
     *
     * @param file Given file
     * @return Contents of the file as an {@link Object} or {@code null} if the
     * file does not exist
     * @throws IOException if file does not exist
     * @throws ClassNotFoundException if class of serialized object cannot be
     * found
     */
    private static Object loadData(File file) throws IOException,
        ClassNotFoundException {
        
        if (file.exists() && !file.isDirectory()) {
            
            return (new ObjectInputStream(
            new FileInputStream(file))).readObject();
        }
        
        return null;
    }

    /**
     * Creates or overwrites a file with serialised object content to a given
     * path.
     *
     * @param path Given path
     * @param object Contents of the file as an {@link Object}
     * @throws IOException if an I/O error occurs
     */
    private static void saveData(String path, Object object)
        throws IOException {
        
        File file = new File(path);
        file.createNewFile();
        
        ObjectOutputStream outStream =
        new ObjectOutputStream(new FileOutputStream(file));
        outStream.writeObject(object);
        outStream.close();
    }

    /**
     * Generates a readable file at the given path.
     *
     * @param path Given path
     * @param content Readable contents of the file as a {@link String}
     * @throws IOException if an I/O error occurs
     */
    private static void saveInfo(String path, String content)
        throws IOException {
        
        File file = new File(path);
        file.createNewFile();
        
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.close();
    }

    /**
     * Overwrites the data on the disk with data from the structure objects of
     * the different list attributes. This method is called when exiting the
     * application and overwrite any {@code .dat} files of the same name.
     */
    public void overwriteDataFiles() {

        try {
            
            saveData("res" + File.separator + "sessionsFee.dat",
            accountUtils.getSessionsFee());
            saveData("res" + File.separator + "profsSessionsProvided.dat",
            accountUtils.getProfsSessionsProvided());
            
            saveData("res" + File.separator + "members.dat",
            listPersons.getMembers());
            saveData("res" + File.separator + "professionals.dat",
            listPersons.getProfessionals());
            
            saveData("res" + File.separator + "bills.dat",
            listReceipts.getBills());
            saveData("res" + File.separator + "paymentNotices.dat",
            listReceipts.getPaymentNotices());
            
            saveData("res" + File.separator + "registrations.dat",
            listRegistrations.getRegistrations());
            
            saveData("res" + File.separator + "services.dat",
            listServices.getServices());
            
            saveData("res" + File.separator + "sessions.dat",
            listSessions.getSessions());
            
            saveData("res" + File.separator + "validations.dat",
            listValidations.getValidations());
            
            saveData("res" + File.separator + "servicesNameNoDirectory.dat",
            servicesDirectory.getServicesNameNo());
            saveData("res" + File.separator + "servicesNoNameDirectory.dat",
            servicesDirectory.getServicesNoName());
            
            saveData("res" + File.separator + "currentMemberId.dat",
            Member.getCurrentMemberId());
            saveData("res" + File.separator + "currentProfId.dat",
            Professional.getCurrentProfId());
            saveData("res" + File.separator + "currentServiceId.dat",
            Service.getCurrentServiceId());
            saveData("res" + File.separator + "noElements.dat",
            ServicesDirectory.getNoElements());
        } catch(IOException e) {
            
            showMessage(e.getMessage());
        }
    }

    /**
     * Prints onto the console to notify the user of a given message.
     *
     * @param message Information to display to the user
     */
	public static void showMessage(String message) {
		
        System.out.println(
        "\n\t********************************************\n\t"
        + message + "\n\t********************************************");
	}

    /**
     * Generates a weekly services report file. One of the steps belonging to
     * the accounting procedure which can be commenced at will.
     */
	public void generateWeeklySessionsReport() {
		
        try {
            
            saveInfo("res" + File.separator + "weekly-sessions-report-" +
            (new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss")).format(
            new Date(System.currentTimeMillis())) + ".txt",
            accountUtils.generateWeeklySessionsReport());
            
            showMessage("Generated Weekly Services Report");
        } catch(IOException e) {
            
            showMessage(e.getMessage());
        }
	}

    /**
     * Performs the various phases within the accounting procedure every Friday
     * at midnight.
     */
    private void accountingProcedure() {
        
        try {
            
            List<TEF> tefs = accountUtils.generateTEFs();
            for (TEF tef : tefs) {
                
                saveData("res" + File.separator + tef.getProfName() + "-" +
                tef.getProfNo() + ".tef", tef);
            }
            
            generateWeeklySessionsReport();
            
            for (Receipt bill : listReceipts.getBills().values()) {
                
                saveInfo("res" + File.separator + bill.getRecipient().getName()
                + "-" + (new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")).format(
                new Date(System.currentTimeMillis())) + ".txt",
                bill.toString());
            }
            
            for (Receipt paymentNotice :
                listReceipts.getPaymentNotices().values()) {
                
                saveInfo("res" + File.separator +
                paymentNotice.getRecipient().getName() + "-" +
                (new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")).format(
                new Date(System.currentTimeMillis())) + ".txt",
                paymentNotice.toString());
            }
            
            scheduledReset();
        } catch(IOException e) {
            
            showMessage(e.getMessage());
        }
	}

    /**
     * Performs the weekly reset of the different structures which are
     * week-based.
     */
    private void scheduledReset() {
        
        accountUtils.clear();
        listSessions.clear();
        listReceipts.clear();
        listRegistrations.clear();
        listValidations.clear();
    }

    /**
     * Retrieves the person associated with the given number depending on the
     * provided type or null if not present.
     *
     * @param code Person's code
     * @param type Person's type
     * @return Person associated with given code of the given type or null if
     * no code associated
     */
    public Person getPerson(String code, Type type) {
        
        switch (type) {
            
            case MEMBER : {
                
                return listPersons.getMember(code);
            }
            
            case PROFESSIONAL : {
                
                return listPersons.getProf(code);
            }
            
            default : return null;
        }
    }

    /**
     * Informs of the current status of the member. Used in order to determine
     * whether some actions are allowed for either members or professionals.
	 * 
	 * @param code Person code
     * @param type Person type
     * @return Person status
     * @throws NullPointerException if an invalid number or type is specified
	 */
	private Status validatePerson(String code, Type type)
        throws NullPointerException {
		
        switch (type) {
            
            case MEMBER : return listPersons.validateMember(code);
            case PROFESSIONAL : return listPersons.validateProf(code);
            default : return null;
        }
	}

    /**
     * Changes the status of a given person.
     *
     * @param code Person code
     * @param type Person type
     * @param status Modified status
     */
    public void changeStatus(String code, Type type, Status status) {
        
        try {
            
            getPerson(code, type).setStatus(status);
            showMessage("Status changed");
        } catch(NullPointerException e) {
            
            showMessage("Invalid number input");
        }
    }
    
    /**
     * Grants access to the #GYM facility if the member's status is valid.
     *
     * @param memberNo Member number
     */
    public void accessGym(String memberNo) {
        
        try {
            
            showMessage(validatePerson(memberNo, Type.MEMBER).getMessage());
        } catch(NullPointerException e) {
            
            showMessage("Invalid type assigned");
        }
    }
    
	/**
     * Performs the membership procedure by associating the provided credentials
     * to a new account in the system.
	 *
	 * @param name Name of the individual
	 * @param address Address of the individual
     * @param city City of the individual
     * @param province Province of the individual
	 * @param postalCode Postal code of the individual
     * @param email Facebook account of the individual
	 * @param type {@link Type} of membership requested
	 */
	public void createPerson(String name, String address, String city,
        String province, String postalCode, String email, Type type) {

        switch (type) {
            
            case MEMBER : {

                try {

                    Member member = listPersons.createMember(name, address,
                    city, province, postalCode, email);
                    showMessage("Member created: " + member.getCode());
                } catch(InvalidFormatException e) {

                    showMessage("Invalid format for attribute: " +
                    e.getMessage());
                }
                
                break;
            }
            
            case PROFESSIONAL : {

                try {

                    Professional prof = listPersons.createProf(name, address,
                    city, province, postalCode, email);
                    showMessage("Professional created: " + prof.getCode());
                } catch(InvalidFormatException e) {

                    showMessage("Invalid format for attribute: " +
                    e.getMessage());
                }
                
                break;
            }
            
            default : showMessage("Invalid type: " + type);
        }
	}

	/**
     * Modifies information relative to the provided existing account. A
     * {@link StatusException} is thrown if a member's status does not permit
     * them to modify their information.
	 *
	 * @param person Person to modify
	 * @param name Modified person name
	 * @param address Modified person address
	 * @param city Modified person city
     * @param province Modified person province
     * @param postalCode Modified person postal code
     * @param email Modified person email
	 */
	public void modifyPerson(Person person, String name, String address,
        String city, String province, String postalCode, String email) {

        try {
            
            if (person instanceof Member) {
                
                Status status = validatePerson(person.getCode(), Type.MEMBER);
                
                if (! status.equals(Status.VALID)) {
                    
                    throw new StatusException(status.getMessage());
                }
            }
            
            listPersons.modifyPerson(person, name, address, city, province,
            postalCode, email);
            showMessage("Modifications made");
        } catch(InvalidFormatException e) {
            
            showMessage("Invalid format for attribute: " + e.getMessage());
        } catch(StatusException e) {
            
            showMessage(e.getMessage());
        }
	}

	/**
     * Deletes a person's information from the server. A
     * {@link StatusException} is thrown if a member's status does not permit
     * them to remove their account
	 * 
	 * @param code Code associated with person to delete
	 * @param type {@link Type} of account to delete
	 */
	public void deletePerson(String code, Type type) {

        switch (type) {
            
            case MEMBER : {

                try {
                    
                    Status status = validatePerson(code, Type.MEMBER);
                    
                    if (! status.equals(Status.VALID)) {
                        
                        throw new StatusException(status.getMessage());
                    }
                    
                    listPersons.deleteMember(code);
                    showMessage("Member removed");
                } catch(NullPointerException e) {
                    
                    showMessage("Invalid member number provided");
                    return;
                } catch(StatusException e) {
                    
                    showMessage(e.getMessage());
                    return;
                }
                
                break;
            }
            
            case PROFESSIONAL : {

                try {
                    
                    listPersons.deleteProf(code);
                    showMessage("Professional removed");
                } catch(NullPointerException e) {
                    
                    showMessage("Invalid professional number provided");
                }
                
                break;
            }
            
            default : showMessage("Invalid type: " + type);
        }
	}

    /**
     * Acquires a service from its given code or null if no service associated.
     *
     * @param code Service code
     * @return The associated service or null if no service associated
     */
    public Service getService(String code) {
        
        return listServices.getService(code);
    }

	/**
     * Creates a new service under the requesting professional's code.
	 * 
	 * @param name Name of the service
	 * @param startDate Start date of the service
	 * @param endDate End date of the service
	 * @param occurrences Sessions weekly recurrence of the service
	 * @param capacityMax Maximum capacity of service's sessions
	 * @param comment Comment on service
	 * @param fee Fee of each service's sessions
	 * @param serviceTime Time of the day where sessions are to be undertaken
	 * @param profNo Professional's code associated with requesting 
     * {@link Professional}
	 */
	public void createService(String name, Date startDate, Date endDate,
        Day[] occurrences, int capacityMax, String comment, int fee,
        Date serviceTime, String profNo) {
        
        if (listPersons.containsProf(profNo)) {
            
            try {
                
                listServices.createService(name, startDate, endDate,
                occurrences, capacityMax, comment, fee, serviceTime, profNo);
                showMessage("Service created");
            } catch(InvalidFormatException e) {
                
                showMessage("Invalid format for attribute: " + e.getMessage());
            } catch(ParseException e) {
                
                showMessage(e.getMessage());
            }
        } else {
            
            showMessage("Invalid number specified");
        }
	}

	/**
     * Modifies information relative to the provided existing service.
	 * 
	 * @param service Service to modify
	 * @param name Modified service name
	 * @param startDate Modified service start date
	 * @param endDate Modified service end date
	 * @param occurrences Modified service weekly recurrence
	 * @param capacityMax Modified service maximum capacity
	 * @param comment Modified service comment
	 * @param fee Modified service fee
	 * @param serviceTime Modified service time
	 * @param profNo Modified service professional's number
	 */
	public void modifyService(Service service, String name, Date startDate,
        Date endDate, Day[] occurrences, int capacityMax, String comment,
        int fee, Date serviceTime, String profNo) {
		
        try {
            
            listServices.modifyService(service, name, startDate, endDate,
            occurrences, capacityMax, comment, fee, serviceTime, profNo);
            showMessage("Modifications made");
        } catch(InvalidFormatException e) {
            
            showMessage("Invalid format for attribute: " + e.getMessage());
        } catch(ParseException e) {
            
            showMessage(e.getMessage());
        }
	}

    /**
     * Deletes a service's information from the server.
	 * 
	 * @param code Code associated with service to delete
	 */
	public void deleteService(String code) {

        if (listServices.deleteService(code) == null) {
            
            showMessage("Unavailable or cannot be removed at this time");
        } else {
            
            showMessage("Service removed");
        }
	}

    /**
     * Acquires the list of sessions available today and displays them onto the
     * command line.
     */
    public void getSessionsToday() {
        
        Collection<Session> sessions;

        if ((sessions = listSessions.getSessionsToday()) == null) {

            showMessage("No sessions available");
        } else {

            showMessage("Available sessions: \n" + sessions);
        }
    }

    /**
     * Displays the list of available services.
     */
    public void getAvailableServices() {
        
        showMessage("Available services: \n" +
        listServices.getServices().values());
    }

	/**
     * Verifies a member's registration to a session so as to allow them to
     * attend the session they are registered to. Note that the member number
     * in this case represents the QR code that the member would usually present
     * to the professional in charge.
	 * 
	 * @param memberNo Member number
	 * @param sessionNo Session number
     * @param comment Validation comment
	 */
	public void confirmRegistration(String memberNo, String sessionNo,
        String comment) {
		
        try {
            
            if (listRegistrations.confirmRegistration(memberNo, sessionNo,
                comment)) {
                
                showMessage("Access granted");
            } else {
                
                showMessage("Access denied");
            }
        } catch(InvalidFormatException e) {
            
            showMessage("Invalid format for attribute: " + e.getMessage());
        } catch(ParseException e) {
            
            showMessage(e.getMessage());
        }
	}

	/**
     * Registers a member for a session. A {@link StatusException} is thrown if
     * a member's status does not permit them to register into a session.
	 * 
	 * @param memberNo Member number
	 * @param sessionNo Session number
     * @param comment Registration comment
	 */
	public void registerSession(String memberNo, String sessionNo,
        String comment) {
		
        try {
            
            Status status = validatePerson(memberNo, Type.MEMBER);
            
            if (! status.equals(Status.VALID)) {
                
                throw new StatusException(status.getMessage());
            }
        } catch(StatusException e) {
            
            showMessage(e.getMessage());
            return;
        }
        
        Session session;
        
        if ((session = listSessions.getSession(sessionNo)) == null) {
            
            showMessage("Service unavailable");
        } else {
            
            int cap = session.getRemainCapacity();
            
            if (cap > 0) {
                
                try {
                    
                    String profNo = session.getProfNo();
                    
                    listRegistrations.createRegistration(sessionNo, memberNo,
                    profNo, comment);
                    accountUtils.addSessionFee(sessionNo, session.getFee());
                    accountUtils.addProvidedProfs(profNo, sessionNo);
                    session.setRemainCapacity(cap - 1);
                    showMessage("Registration complete");
                } catch(InvalidFormatException e) {
                    
                    showMessage("Invalid format for attribute: " +
                    e.getMessage());
                } catch(ParseException e) {
                    
                    showMessage(e.getMessage());
                }
            } else {
                
                showMessage("Maximum capacity reached");
            }
        }
	}

    /**
     * Permits a professional to access the list of registrations to a session
     * they give. A {@link StatusException} is thrown and handled if a
     * professional's status does not permit them to consult their
     * registrations.
     *
     * @param profNo Professional number
     * @param sessionNo Session number
     */
	public void consultRegistrations(String profNo, String sessionNo) {

        try {
            
            Status status = validatePerson(profNo, Type.PROFESSIONAL);
            
            if (! status.equals(Status.VALID)) {
                
                throw new StatusException(status.getMessage());
            }
        } catch(NullPointerException e) {
            
            showMessage("Invalid professional number provided");
        } catch(StatusException e) {
            
            showMessage(e.getMessage());
            return;
        }

        showMessage( "Registrations: \n" +
        listRegistrations.consultRegistrations(sessionNo).toString());
    }
    
    /**
     * Custom class task for initiating accounting procedures at a fixed
     * schedule.
     */
    private class AccountingTask extends TimerTask {
        
        /**
         * Initiates the accounting procedure task.
         */
        @Override
        public void run() {
            
            accountingProcedure();
        }
    }
}