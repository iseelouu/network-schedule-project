package network.schedule.model;
import java.time.LocalDate;

public interface WorkCalendar {
    boolean isWorkingDay(LocalDate date);
}
