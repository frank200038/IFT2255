package personal.gym.session;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestServicesDirectory {
    private ServicesDirectory servicesDirectory;

    @Before
    public void setUp() {
        // Initialize Empty directory
        servicesDirectory = new ServicesDirectory(null, null);
        ServicesDirectory.setNoElements(0);
    }

    @Test
    public void testObtainingNewServiceNo() {
        String serviceNo0 = servicesDirectory.obtainServiceNo("Yoga");
        assertEquals("000",serviceNo0);

        String serviceNo1 = servicesDirectory.obtainServiceNo("Meditation");
        assertEquals("001",serviceNo1);

        String serviceNo2 = servicesDirectory.obtainServiceNo("Spinning");
        assertEquals("002",serviceNo2);
    }

    @Test
    public void testObtainingExistingServiceNo() {
        String serviceNo0 = servicesDirectory.obtainServiceNo("Yoga");
        String serviceNo1 = servicesDirectory.obtainServiceNo("Meditation");
        String serviceNo2 = servicesDirectory.obtainServiceNo("Spinning");

        String serviceNo3 = servicesDirectory.obtainServiceNo("Yoga");

        assertEquals(serviceNo0,serviceNo3);
    }

}
