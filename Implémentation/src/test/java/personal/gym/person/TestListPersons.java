package personal.gym.person;

import org.junit.Before;
import org.junit.Test;
import personal.gym.exception.InvalidFormatException;
import personal.gym.registration.ListRegistrations;
import personal.gym.service.ListServices;

import static org.junit.Assert.*;
import static personal.gym.person.Status.*;

public class TestListPersons {
    private ListPersons listPersons;
    private ListRegistrations listRegistrations;
    private ListServices listServices;

    @Before
    public void setUp() {
        // Initialize Empty Lists
        listPersons = new ListPersons(null, null);
        Member.setCurrentMemberId(0);
        Professional.setCurrentProfId(0);

        listRegistrations = new ListRegistrations(null);
        listServices = new ListServices(null);

        listPersons.setListRegistrations(listRegistrations);
        listPersons.setListServices(listServices);
    }


    @Test
    public void testAddMember() throws InvalidFormatException {

        Member member0 = listPersons.createMember("Julien Thibeault",
                "12345 Sherbrooke Est",
                "Montréal",
                "QC",
                "A1A1A1",
                "thibeaultj@facebook.com");

        Member member1 = listPersons.createMember("Philippe Gabriel",
                "987 Sherbrooke Ouest",
                "Montréal",
                "QC",
                "B2B2B2",
                "philgab@facebook.com");

        assertNotNull(listPersons.getMember("000000000"));
        assertNotNull(listPersons.getMember("000000001"));

        assertEquals(member0, listPersons.getMember("000000000"));
        assertEquals(member1, listPersons.getMember("000000001"));
        assertNotEquals(member1, listPersons.getMember("000000000"));


    }


    @Test
    public void testDeleteMember() throws InvalidFormatException {

        Member member0 = listPersons.createMember("Julien Thibeault",
                "12345 Sherbrooke Est",
                "Montréal",
                "QC",
                "A1A1A1",
                "thibeaultj@facebook.com");

        Member member1 = listPersons.createMember("Philippe Gabriel",
                "987 Sherbrooke Ouest",
                "Montréal",
                "QC",
                "B2B2B2",
                "philgab@facebook.com");

        listPersons.deleteMember("000000000");

        assertNull(listPersons.getMember("000000000"));
        assertNotNull(listPersons.getMember("000000001"));
    }

    @Test
    public void testModifyPerson() throws InvalidFormatException {

        Member member0 = listPersons.createMember("Julien Thibeault",
                "12345 Sherbrooke Est",
                "Montréal",
                "QC",
                "A1A1A1",
                "thibeaultj@facebook.com");

        listPersons.modifyPerson(listPersons.getMember("000000000"),
                "Julien Thibeault",
                "4 rue de la Montagne",
                "Montréal",
                "QC",
                "C3C3C3",
                "tibo222@facebook.com");

        assertEquals("4 rue de la Montagne",
                listPersons.getMember("000000000").getAddress());

        assertEquals("tibo222@facebook.com",
                listPersons.getMember("000000000").getEmail());

        assertEquals("C3C3C3",
                listPersons.getMember("000000000").getPostalCode());

        assertEquals("Julien Thibeault",
                listPersons.getMember("000000000").getName());
    }

    @Test
    public void testValidateMember() throws InvalidFormatException {
        Member member0 = listPersons.createMember("Julien Thibeault",
                "12345 Sherbrooke Est",
                "Montréal",
                "QC",
                "A1A1A1",
                "thibeaultj@facebook.com");

        listPersons.getMember("000000000").setStatus(SUSPENDED);

        assertEquals(SUSPENDED, listPersons.validateMember("000000000"));

        listPersons.getMember("000000000").setStatus(Status.INVALID_NUMBER);

        assertEquals(INVALID_NUMBER, listPersons.validateMember("000000000"));

        listPersons.getMember("000000000").setStatus(Status.VALID);

        assertEquals(VALID, listPersons.validateMember("000000000"));

        listPersons.getMember("000000000").setStatus(null);

        assertNull(listPersons.validateMember("000000000"));
    }

    @Test
    public void testCreateProf() throws InvalidFormatException {
        Professional.setCurrentProfId(33);
        Professional prof33 = listPersons.createProf("Yan Zhuang",
                "1 rue St-Denis",
                "Montréal",
                "QC",
                "H1H1H1",
                "yanz@facebook.com");

        assertEquals(prof33,listPersons.getProf("000000033"));
        assertNull(listPersons.getMember("000000033"));

    }

}
