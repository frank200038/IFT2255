package personal.gym.util;

import java.time.DayOfWeek;

/**
 * Constants indicating days of the week.
 *
 * @version 1.0
 * @author Yu Deng
 * @author Philippe Gabriel
 * @author Julien Thibeault
 * @author Yan Zhuang
 */
public enum Day {
    
	MONDAY("11", DayOfWeek.MONDAY),
	TUESDAY("22", DayOfWeek.TUESDAY),
	WEDNESDAY("33", DayOfWeek.WEDNESDAY),
	THURSDAY("44", DayOfWeek.THURSDAY),
	FRIDAY("55", DayOfWeek.FRIDAY),
	SATURDAY("66", DayOfWeek.SATURDAY),
	SUNDAY("77", DayOfWeek.SUNDAY);

    public static final int LENGTH_OF_WEEK = 7;

    private final String code;
    private final DayOfWeek day;

    /**
     * Initializes the day constant parameters.
     *
     * @param code Day code for generating session code
     * @param day DayOfWeek value for updating sessions
     */
    Day(String code, DayOfWeek day) {
        
        this.code = code;
        this.day = day;
    }

    /**
     * @return Day code for generating session code
     */
    public String getCode() {
        
        return code;
    }

    /**
     * @return DayOfWeek value for updating sessions
     */
    public DayOfWeek getDay() {
        
        return this.day;
    }
}