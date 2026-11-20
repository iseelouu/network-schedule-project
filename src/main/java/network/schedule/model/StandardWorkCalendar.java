package network.schedule.model;

import java.util.Set;
import java.util.HashSet;
import java.time.MonthDay;
import java.time.LocalDate;

/**
 * Реализация рабочего календаря.
 * Учитывает выходные дни, разовые и ежегодные праздники
 * при расчёте продолжительности работ и дат.
 */
public class StandardWorkCalendar implements WorkCalendar {
    private Set<LocalDate> holidays;
    private Set<MonthDay> annualHolidays;

    /**
     * Создаёт рабочий календарь с указанными праздниками.
     *
     * @param holidays список разовых праздничных дат
     * @param annualHolidays список ежегодных праздников
     * @throws IllegalArgumentException если один из наборов равен null
     */
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

    /**
     * Проверяет, является ли указанная дата рабочим днём.
     *
     * @param date проверяемая дата
     * @return true, если дата рабочая, иначе false
     */
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

    /**
     * Рассчитывает дату окончания работы с учётом рабочих дней.
     *
     * @param startDate дата начала работы
     * @param duration продолжительность работы в рабочих днях
     * @return дата окончания работы
     * @throws IllegalArgumentException если дата начала равна null
     * или продолжительность отрицательная
     */
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

    /**
     * Находит следующий рабочий день после указанной даты.
     *
     * @param date исходная дата
     * @return следующая рабочая дата
     * @throws IllegalArgumentException если дата равна null
     */
    @Override
    public LocalDate getNextWorkingDay(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Дата не может быть null");
        }

        LocalDate nextDate = date.plusDays(1);
        if (!isWorkingDay(nextDate)) {
            nextDate = getNextWorkingDay(nextDate);
        }
        return nextDate;
    }

    /**
     * Находит предыдущий рабочий день перед указанной датой.
     *
     * @param date исходная дата
     * @return предыдущая рабочая дата
     * @throws IllegalArgumentException если дата равна null
     */
    @Override
    public LocalDate getPreviousWorkingDay(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Дата не может быть null");
        }

        LocalDate previousDate = date.minusDays(1);
        if(!isWorkingDay(previousDate)) {
            previousDate = getPreviousWorkingDay(previousDate);
        }

        return previousDate;
    }
}
