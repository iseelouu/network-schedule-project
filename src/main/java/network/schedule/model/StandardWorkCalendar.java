package network.schedule.model;

import java.util.Set;
import java.util.HashSet;
import java.time.MonthDay;
import java.time.LocalDate;

public class StandardWorkCalendar implements WorkCalendar {
    private Set<LocalDate> holidays;
    private Set<MonthDay> annualHolidays;

    public StandardWorkCalendar(Set<LocalDate> holidays, Set<MonthDay> annualHolidays) {
        if (holidays == null) {
            throw new IllegalArgumentException("Список праздничных дней не может быть null");
        }

        if (annualHolidays == null) {
            throw new IllegalArgumentException("Ежегодные праздники не могут быть null");
        }

        this.holidays = new HashSet<>(holidays);
        this.annualHolidays = new HashSet<>(annualHolidays);
    }

    @Override
    public boolean isWorkingDay(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Дата не может быть null");
        }
        return !isHolidayDay(date);
    }

    private boolean isHolidayDay(LocalDate date) {
        return date.getDayOfWeek().getValue() == 7 ||
                date.getDayOfWeek().getValue() == 6 ||
                holidays.contains(date) ||
                annualHolidays.contains(MonthDay.from(date));
    }

    @Override
    public  LocalDate calculateEndDate(LocalDate startDate, int duration) {
        if (startDate == null) {
            throw new IllegalArgumentException("Дата начала не может быть null");
        }

        if (duration < 0) {
            throw new IllegalArgumentException("Длительность работы не может быть отрицательной");
        }

        if (duration == 0) {
            return startDate;
        }
        LocalDate currentDate = startDate;
        int workingDays = 0;

        while (workingDays < duration) {
            if(isWorkingDay(currentDate)) {
                workingDays++;
            }

            currentDate = currentDate.plusDays(1);
        }

        return currentDate.minusDays(1);
    }

}
