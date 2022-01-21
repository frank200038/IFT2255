package personal.gym.session;

import org.junit.Test;
import personal.gym.exception.InvalidFormatException;
import personal.gym.service.ListServices;
import personal.gym.util.Day;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static personal.gym.util.Day.*;

public class TestListSessions {

    private ListSessions listSessions;
    private ListServices listServices;

    @Test
    public void testSessionConstructor() throws ParseException {
        Session sessionA = new Session(
                "Yoga",
                MONDAY,
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-07-18 12:30:00"),
                30,
                4000,
                "333666999",
                "1112222");

        Session sessionB = new Session("Meditation",
                TUESDAY,
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-07-19 12:30:00"),
                30,
                4000,
                "444777222",
                "0000444");

        Map<String, Session> sessionsToContructor = new HashMap<>();

        sessionsToContructor.put("1112222", sessionA);
        sessionsToContructor.put("0000444", sessionB);

        listSessions = new ListSessions(sessionsToContructor);

        Session retrievedSessionB = listSessions.getSession("0000444");

        assertEquals(sessionB, retrievedSessionB);

        Session retrievedSessionA = listSessions.getSession("1112222");

        assertNotEquals(sessionB, retrievedSessionA);

    }

    @Test
    public void testGenerateSessionNo() throws ParseException, InvalidFormatException {
        // generateSessionNo is private, we will test it via refreshSessions method

        // initialise empty lists
        listServices = new ListServices(null);
        listSessions = new ListSessions(null);

        // link lists
        listSessions.setServicesDirectory(new ServicesDirectory(null, null));
        listSessions.setListServices(listServices);
        listServices.setListSessions(listSessions);

        listServices.createService("Pilates",
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-07-19 12:30:00"),
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-09-19 12:30:00"),
                new Day[]{FRIDAY},
                20,
                "Bring water",
                2500,
                new SimpleDateFormat("HH:mm").parse("10:30"),
                "123456789");

        listSessions.refreshSessions();

        assertNotNull(listSessions.getSession("0005589"));
        assertNull(listSessions.getSession("0001122"));
    }

}
