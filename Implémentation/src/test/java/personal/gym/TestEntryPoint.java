package personal.gym;

import org.junit.Test;
import personal.gym.util.Day;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class TestEntryPoint {

    @Test
    public void testStrToCents() {

        assertEquals(0, strToCents("0.00"));            // 0
        assertEquals(12300, strToCents("123"));         // $ only
        assertEquals(12345, strToCents("123.45"));      // $ and cents
        assertEquals(45, strToCents("0.45"));           // cents only

    }

    @Test
    public void testStrToDate() throws ParseException {

        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2021-07-18"),
                strToDate("2021-07-18","yyyy-MM-dd"));

        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("0001-01-01"),
                strToDate("0001-01-01","yyyy-MM-dd"));

        assertEquals(new SimpleDateFormat("dd-MM-yyyy").parse("12-12-2012"),
                strToDate("2012-12-12","yyyy-MM-dd"));

    }

    @Test
    public void testStrToDayArray() {

        String days1 = "monday, tuesday, wednesday";
        Day[] dayArray1 = new Day[]{Day.MONDAY,Day.TUESDAY,Day.WEDNESDAY};
        assertArrayEquals(dayArray1, strToDayArray(days1));

        String days2 = "friday";
        Day[] dayArray2 = new Day[]{Day.FRIDAY};
        assertArrayEquals(dayArray2, strToDayArray(days2));

        String days3 = "sunday, monday";
        Day[] dayArray3 = new Day[]{Day.SUNDAY,Day.MONDAY};
        assertArrayEquals(dayArray3, strToDayArray(days3));

    }


    // Private methods copied for testing directly

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

    private static Date strToDate(String date, String pattern)
            throws ParseException {

        return (new SimpleDateFormat(pattern)).parse(date);
    }


    private static Day[] strToDayArray(String days) {

        String[] strDayArray = days.toUpperCase().split(",(\\s)?");
        Day[] dayArray = new Day[strDayArray.length];

        for (int i = 0; i < dayArray.length; i++) {

            dayArray[i] = Day.valueOf(strDayArray[i]);
        }

        return dayArray;
    }




}
