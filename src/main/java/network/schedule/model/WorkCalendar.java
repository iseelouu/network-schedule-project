package network.schedule.model;
import java.time.LocalDate;

public interface WorkCalendar {
    boolean isWorkingDay(LocalDate date);
    LocalDate calculateEndDate(LocalDate startDate, int duration);
    LocalDate getNextWorkingDay(LocalDate date);
    LocalDate getPreviousWorkingDay(LocalDate date);
}
