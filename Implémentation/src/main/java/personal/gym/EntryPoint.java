package personal.gym;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import personal.gym.person.Person;
import personal.gym.person.Status;

import personal.gym.service.Service;

import personal.gym.util.*;

/**
 * View of the app.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public class EntryPoint {
    
    static Scanner sc = new Scanner(System.in);
    static DataCenterApplication db = new DataCenterApplication();
    
    /**
     * Processes commands retrieved from the command line.
     * 
     * @param cmd String command retrieved from command line
     */
    private static void processIncomingCommand(String cmd) {
        
        switch (cmd) {

            case "change person status" : {
                
                System.out.print("\tCode (9 digits)>\t\t\t\t");
                String code = sc.nextLine();
                
                System.out.print("\tType (Member/Professional)>\t\t\t");
                Type type;
                try {
                    
                    type = Type.valueOf(sc.nextLine().toUpperCase());
                } catch(IllegalArgumentException e) {
                    
                    DataCenterApplication.showMessage("Invalid type specified");
                    break;
                }
                
                System.out.print(
                "\tStatus (Valid/Invalid number/Suspended)>\t");
                Status status;
                try {
                    
                    status = Status.valueOf(
                    sc.nextLine().toUpperCase().replaceAll("\\s", "_"));
                } catch(IllegalArgumentException e) {
                    
                    DataCenterApplication.showMessage(
                    "Invalid status specified");
                    break;
                }
                
                db.changeStatus(code, type, status);
                
                break;
            }

            case "create person" : {

                System.out.print(
                "\tName (<= 25 characters, Firstname Lastname)>\t");
                String name = sc.nextLine();

                System.out.print("\tAddress (<= 25 characters)>\t\t\t");
                String address = sc.nextLine();

                System.out.print("\tCity (<= 14 characters)>\t\t\t");
                String city = sc.nextLine();

                System.out.print("\tProvince (2 characters)>\t\t\t");
                String province = sc.nextLine();

                System.out.print("\tPostal Code (A#A#A#)>\t\t\t\t");
                String postalCode = sc.nextLine();

                System.out.print("\tEmail (example@facebook.com)>\t\t\t");
                String email = sc.nextLine();

                System.out.print("\tType (Member/Professional)>\t\t\t");
                Type type;
                try {

                    type = Type.valueOf(sc.nextLine().toUpperCase());
                } catch(IllegalArgumentException e) {

                    DataCenterApplication.showMessage("Invalid type specified");
                    break;
                }

                db.createPerson(name, address, city, province, postalCode,
                email, type);
                
                break;
            }
            
            case "modify person" : {

                System.out.print("\tCode (9 digits)>\t\t\t\t");
                String code = sc.nextLine();

                System.out.print("\tType (Member/Professional)>\t\t\t");
                Type type;
                try {
                    
                    type = Type.valueOf(sc.nextLine().toUpperCase());
                } catch(IllegalArgumentException e) {
                    
                    DataCenterApplication.showMessage("Invalid type specified");
                    break;
                }

                Person person = db.getPerson(code, type);

                if (person == null) {

                    DataCenterApplication.showMessage("Invalid number input");
                    break;
                }

                System.out.println("\nLeave blank to keep current setting\n");

                String name;
                String currentName = person.getName();
                System.out.println("\tCurrent Name: " + currentName);
                System.out.print(
                "\tName (<= 25 characters), Firstname Lastname>\t");
                if ((name = sc.nextLine()).equals("")) {

                    name = currentName;
                }

                String address;
                String currentAddress = person.getAddress();
                System.out.println("\tCurrent Address: " + currentAddress);
                System.out.print("\tAddress (<= 25 characters)>\t\t\t");
                if ((address = sc.nextLine()).equals("")) {

                    address = currentAddress;
                }

                String city;
                String currentCity = person.getCity();
                System.out.println("\tCurrent City: " + currentCity);
                System.out.print("\tCity (<= 14 characters)>\t\t\t");
                if ((city = sc.nextLine()).equals("")) {

                    city = currentCity;
                }

                String province;
                String currentProvince = person.getProvince();
                System.out.println("\tCurrent Province: " + currentProvince);
                System.out.print("\tProvince (2 characters)>\t\t\t");
                if ((province = sc.nextLine()).equals("")) {

                    province = currentProvince;
                }

                String postalCode;
                String currentPostalCode = person.getPostalCode();
                System.out.println("\tCurrent PostalCode: " +
                currentPostalCode);
                System.out.print("\tPostal Code (A#A#A#)>\t\t\t\t");
                if ((postalCode = sc.nextLine()).equals("")) {

                    postalCode = currentPostalCode;
                }

                String email;
                String currentEmail = person.getEmail();
                System.out.println("\tCurrent Email: " + currentEmail);
                System.out.print("\tEmail (example@facebook.com)>\t\t\t");
                if ((email = sc.nextLine()).equals("")) {

                    email = currentEmail;
                }

                db.modifyPerson(person, name, address, city, province,
                postalCode, email);
                
                break;
            }
            
            case "delete person" : {

                System.out.print("\tCode (9 digits)>\t\t\t\t");
                String code = sc.nextLine();

                System.out.print("\tType (Member/Professional)>\t\t\t");
                String type = sc.nextLine();

                db.deletePerson(code, Type.valueOf(type.toUpperCase()));
                
                break;
            }
            
            case "create service" : {

                System.out.print("\tName (<= 25 characters, Example)>\t\t");
                String name = sc.nextLine();

                System.out.print("\tStart Date (JJ-MM-AAAA)>\t\t\t");
                Date startDate;
                try {

                    startDate = strToDate(sc.nextLine(), "dd-MM-yyyy");
                } catch(ParseException e) {

                    DataCenterApplication.showMessage(e.getMessage());
                    break;
                }

                System.out.print("\tEnd Date (JJ-MM-AAAA)>\t\t\t\t");
                Date endDate;
                try {

                    endDate = strToDate(sc.nextLine(), "dd-MM-yyyy");
                } catch(ParseException e) {

                    DataCenterApplication.showMessage(e.getMessage());
                    break;
                }

                System.out.print("\tWeekly recurrence (Day1, Day2, ...)>\t\t");
                Day[] occurrences = strToDayArray(sc.nextLine());

                int capacityMax;
                System.out.print("\tMax capacity (<= 30)>\t\t\t\t");
                try {

                    capacityMax = Integer.parseInt(sc.nextLine());
                } catch(NumberFormatException e) {

                    DataCenterApplication.showMessage(e.getMessage());
                    break;
                }

                System.out.print("\tService fee (<= $100.00)>\t\t\t");
                int fee;
                try {
                
                    fee = strToCents(sc.nextLine());
                } catch(NumberFormatException e) {
                
                    DataCenterApplication.showMessage(e.getMessage());
                    break;
                }

                System.out.print("\tService Time (HH:MM)>\t\t\t\t");
                Date serviceTime;
                try {

                    serviceTime = strToDate(sc.nextLine(), "HH:mm");
                } catch(ParseException e) {

                    DataCenterApplication.showMessage(e.getMessage());
                    break;
                }

                System.out.print("\tProfessional code (9 digits)>\t\t\t");
                String profNo = sc.nextLine();

                System.out.print("\tComment (<= 100 characters)>\t\t\t");
                String comment = sc.nextLine();

                db.createService(name, startDate, endDate, occurrences,
                capacityMax, comment, fee, serviceTime, profNo);
                
                break;
            }
            
            case "modify service" : {

                System.out.print("\tService code (7 digits)>\t\t\t");
                String code = sc.nextLine();

                Service service = db.getService(code);

                if (service == null) {

                    DataCenterApplication.showMessage("Invalid number input");
                    break;
                }

                System.out.println("Leave field blank to keep original\n");

                String name;
                String currentName = service.getName();
                System.out.println("\tCurrent Name: " + currentName);
                System.out.print("\tName (<= 25 characters, Example)>\t\t");
                if ((name = sc.nextLine()).equals("")) {

                    name = currentName;
                }

                SimpleDateFormat startEnd = new SimpleDateFormat("dd-MM-yyyy");

                Date startDate;
                Date currentStartDate = service.getStartDate();
                String strStartDate;
                System.out.println("\tCurrent Start Date: " +
                startEnd.format(currentStartDate));
                System.out.print("\tStart Date (JJ-MM-AAAA)>\t\t\t");
                if ((strStartDate = sc.nextLine()).equals("")) {

                    startDate = currentStartDate;
                } else {

                    try {

                        startDate = strToDate(strStartDate, "dd-MM-yyyy");
                    } catch(ParseException e) {

                        DataCenterApplication.showMessage(e.getMessage());
                        break;
                    }
                }

                Date endDate;
                Date currentEndDate = service.getEndDate();
                String strEndDate;
                System.out.println("\tCurrent End Date: " +
                startEnd.format(currentEndDate));
                System.out.print("\tEnd Date (JJ-MM-AAAA)>\t\t\t\t");
                if ((strEndDate = sc.nextLine()).equals("")) {

                    endDate = currentEndDate;
                } else {

                    try {

                        endDate = strToDate(strEndDate, "dd-MM-yyyy");
                    } catch(ParseException e) {

                        DataCenterApplication.showMessage(e.getMessage());
                        break;
                    }
                }

                Day[] occurrences;
                Day[] currentOccurrences = service.getOccurrences();
                String strOccurrences;
                System.out.println("\tCurrent weekly recurrence: " +
                Arrays.toString(currentOccurrences));
                System.out.print("\tWeekly recurrence (Day1, Day2, ...)>\t\t");
                if ((strOccurrences = sc.nextLine()).equals("")) {

                    occurrences = currentOccurrences;
                } else {

                    occurrences = strToDayArray(strOccurrences);
                }

                int capacityMax;
                int currentCapacityMax = service.getCapacityMax();
                String strCapacityMax;
                System.out.println("\tCurrent max capacity: " +
                currentCapacityMax);
                System.out.print("\tMax capacity (<= 30)>\t\t\t\t");
                if ((strCapacityMax = sc.nextLine()).equals("")) {

                    capacityMax = currentCapacityMax;
                } else {

                    try {

                        capacityMax = Integer.parseInt(strCapacityMax);
                    } catch(NumberFormatException e) {

                        DataCenterApplication.showMessage(e.getMessage());
                        break;
                    }
                }

                int fee;
                int currentFee = service.getFee();
                String strFee;
                System.out.println("\tCurrent fee: " +
                String.format("%.2f", currentFee / 100.0));
                System.out.print("\tService fee (<= $100.00)>\t\t\t");
                if ((strFee = sc.nextLine()).equals("")) {

                    fee = currentFee;
                } else {

                    try {

                        fee = strToCents(strFee);
                    } catch(NumberFormatException e) {

                        DataCenterApplication.showMessage(e.getMessage());
                        break;
                    }
                }

                SimpleDateFormat time = new SimpleDateFormat("HH:mm");

                Date serviceTime;
                Date currentServiceTime = service.getServiceTime();
                String strServiceTime;
                System.out.println("\tCurrent Service Time: " +
                time.format(currentServiceTime));
                System.out.print("\tService Time (HH:MM)>\t\t\t\t");
                if ((strServiceTime = sc.nextLine()).equals("")) {
                
                    serviceTime = currentServiceTime;
                } else {
                
                    try {
                
                        serviceTime = strToDate(strServiceTime, "HH:mm");
                    } catch(ParseException e) {
                
                        DataCenterApplication.showMessage(e.getMessage());
                        break;
                    }
                }

                String profNo;
                String currentProfNo = service.getProfNo();
                System.out.println("\tCurrent professional number: " +
                currentProfNo);
                System.out.print("\tProfessional code (9 digits)>\t\t\t");
                if ((profNo = sc.nextLine()).equals("")) {
                
                    profNo = currentProfNo;
                }

                String comment;
                String currentComment = service.getComment();
                System.out.println("\tCurrent comment: " + currentComment);
                System.out.print("\tComment (<= 100 characters)>\t\t\t");
                if ((comment = sc.nextLine()).equals("")) {

                    comment = currentComment;
                }

                db.modifyService(service, name, startDate, endDate, occurrences,
                capacityMax, comment, fee, serviceTime, profNo);
                
                break;
            }
            
            case "delete service" : {

                System.out.print("\tService code (7 digits)>\t\t\t");
                String code = sc.nextLine();

                db.deleteService(code);
                
                break;
            }
            
            case "sessions today" : {
                
                db.getSessionsToday();
                break;
            }

            case "available services" : {
                
                db.getAvailableServices();
                break;
            }

            case "generate weekly sessions report" : {
                
                db.generateWeeklySessionsReport();
                break;
            }

            case "confirm registration" : {

                System.out.print("\tMember code (9 digits)>\t\t\t\t");
                String codeMem = sc.nextLine();

                System.out.print("\tSession code (7 digits)>\t\t\t");
                String code = sc.nextLine();

                System.out.print("\tComment (<= 100 characters)>\t\t\t");
                String comment = sc.nextLine();

                db.confirmRegistration(codeMem, code, comment);
                
                break;
            }
            
            case "register session" : {

                System.out.print("\tMember code (9 digits)>\t\t\t\t");
                String codeMem = sc.nextLine();

                System.out.print("\tSession code (7 digits)>\t\t\t");
                String code = sc.nextLine();

                System.out.print("\tComment (<= 100 characters)>\t\t\t");
                String comment = sc.nextLine();

                db.registerSession(codeMem, code, comment);
                
                break;
            }
            
            case "access #gym" : {

                System.out.print("\tMember code (9 digits)>\t\t\t\t");
                String code = sc.nextLine();

                db.accessGym(code);
                break;
            }
            
            case "consult registrations" : {

                System.out.print("\tProfessional code (9 digits)>\t\t\t");
                String profNo = sc.nextLine();

                System.out.print("\tSession code (7 digits)>\t\t\t");
                String sessionNo = sc.nextLine();

                db.consultRegistrations(profNo, sessionNo);
                
                break;
            }
            
            case "exit" : {
                
                db.overwriteDataFiles();
                break;
            }

            default : DataCenterApplication.showMessage("Invalid command");
        }
    }
    
    /**
     * Helper method for converting user-input to appropriate {@link Day}
     * array.
     *
     * @param days Input retrieved from command line 
     * @return <b>dayArray</b> Appropriate {@link Day} array
     */
    private static Day[] strToDayArray(String days) {

        String[] strDayArray = days.toUpperCase().split(",(\\s)?");
        Day[] dayArray = new Day[strDayArray.length];

        for (int i = 0; i < dayArray.length; i++) {
            
            dayArray[i] = Day.valueOf(strDayArray[i]);
        }

        return dayArray;
    }
    
    /**
     * Helper method for converting user-input to appropriate date value.
     *
     * @param date Input retrieved from command line
     * @param pattern Appropriate Date pattern for parsing
     * @return Corresponding {@link Date} object
     * @throws ParseException in case of unrecognizable input
     */
    private static Date strToDate(String date, String pattern)
        throws ParseException {
        
        return (new SimpleDateFormat(pattern)).parse(date);
    }
    
    /**
     * Helper method for converting user-input to appropriate cents value.
     *
     * @param fee Amount entered in dollars
     * @return Equivalent amount in cents
     * @throws NumberFormatException if input fee is in a different format than
     * expected
     */
    private static int strToCents(String fee) throws NumberFormatException {

        if (! fee.contains(".")) {
            
            return Integer.parseInt(
            String.join("", (fee + ".00").split("\\.")));
        } else if (fee.matches("[0-9]+\\.[0-9]{2}")
            || fee.matches("\\.[0-9]{2}")) {
            
            return Integer.parseInt(String.join("", fee.split("\\.")));
        } else {
            
            throw new NumberFormatException("Invalid fee format");
        }
    }
    
    /**
     * Starting method of application.
     *
     * @param args Not used for the purposes of this application
     */
    public static void main(String[] args) {
        
        String cmd;
        
        do {
        
            System.out.println("\nEnter one of the following commands: ");
            System.out.println("\tChange Person Status");
            System.out.println("\tCreate Person");
            System.out.println("\tModify Person");
            System.out.println("\tDelete Person");
            System.out.println("\tCreate Service");
            System.out.println("\tModify Service");
            System.out.println("\tDelete Service");
            System.out.println("\tSessions Today");
            System.out.println("\tAvailable Services");
            System.out.println("\tGenerate Weekly Sessions Report");
            System.out.println("\tConfirm Registration");
            System.out.println("\tRegister Session");
            System.out.println("\tAccess #GYM");
            System.out.println("\tConsult Registrations");
            System.out.println("\tExit\n");
        
            System.out.print("Command> ");
            processIncomingCommand(cmd = sc.nextLine().toLowerCase());
        } while (! cmd.equals("exit"));
        
        sc.close();
        
        System.exit(0);
    }
}