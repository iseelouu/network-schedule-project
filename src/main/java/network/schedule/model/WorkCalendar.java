package network.schedule.model;
import java.time.LocalDate;

/**
 * Определяет операции рабочего календаря,
 * необходимые для расчёта дат выполнения работ.
 */
public interface WorkCalendar {
    /**
     * Проверяет, является ли указанная дата рабочим днём.
     *
     * @param date проверяемая дата
     * @return true, если дата рабочая
     */
    boolean isWorkingDay(LocalDate date);
    /**
     * Рассчитывает дату окончания работы с учётом рабочих дней.
     *
     * @param startDate дата начала работы
     * @param duration продолжительность работы в рабочих днях
     * @return дата окончания работы
     */
    LocalDate calculateEndDate(LocalDate startDate, int duration);
    /**
     * Возвращает следующий рабочий день.
     *
     * @param date исходная дата
     * @return следующая рабочая дата
     */
    LocalDate getNextWorkingDay(LocalDate date);
    /**
     * Возвращает предыдущий рабочий день.
     *
     * @param date исходная дата
     * @return предыдущая рабочая дата
     */
    LocalDate getPreviousWorkingDay(LocalDate date);
}
