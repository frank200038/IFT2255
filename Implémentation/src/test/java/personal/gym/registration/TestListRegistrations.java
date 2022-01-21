package personal.gym.registration;

import org.junit.Before;
import org.junit.Test;
import personal.gym.exception.InvalidFormatException;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestListRegistrations {
    private ListRegistrations listRegistrations;

    @Before
    public void setUp() {
        // Initialize Empty Lists
        listRegistrations = new ListRegistrations(null);
    }


    @Test
    public void testCreateRegistration() throws InvalidFormatException, ParseException {
        Registration reg1 = listRegistrations.createRegistration("1234567", "111444777",
                "333666999", "test comment" );

        assertEquals(reg1, listRegistrations.getMemberRegistration("111444777", "1234567"));
    }

    @Test
    public void testRemoveRegistrationsProf() throws ParseException, InvalidFormatException {
        Registration reg1 = listRegistrations.createRegistration("1234567", "111444777",
                "333666999", "test comment" );

        listRegistrations.removeRegistrationsProf("333666999");

        assertNull(listRegistrations.getMemberRegistration("111444777", "1234567"));
    }
}
