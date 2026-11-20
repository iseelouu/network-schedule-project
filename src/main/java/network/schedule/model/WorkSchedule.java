package network.schedule.model;

import java.time.LocalDate;

/**
 * Хранит рассчитанные параметры выполнения работы:
 * раннее и позднее начало, раннее и позднее окончание,
 * а также общий временной резерв.
 */
public class WorkSchedule {
    private LocalDate earlyStart;
    private LocalDate earlyFinish;
    private LocalDate lateStart;
    private LocalDate lateFinish;
    private int totalFloat;
    private Work work;

    /**
     * Создаёт расписание для указанной работы.
     *
     * @param work работа, для которой создаётся расписание
     * @throws IllegalArgumentException если работа равна null
     */
    public WorkSchedule(Work work) {
        if (work == null) {
            throw new IllegalArgumentException("Работа не может быть null");
        }

        this.work = work;
    }

    /**
     * Возвращает работу, к которой относится расписание.
     *
     * @return работа
     */
    public Work getWork() {
        return work;
    }

    /**
     * Возвращает раннюю дату начала работы.
     *
     * @return ранняя дата начала
     */
    public LocalDate getEarlyStart() {
        return earlyStart;
    }

    /**
     * Возвращает раннюю дату окончания работы.
     *
     * @return ранняя дата окончания
     */
    public LocalDate getEarlyFinish() {
        return earlyFinish;
    }

    /**
     * Возвращает позднюю дату начала работы.
     *
     * @return поздняя дата начала
     */
    public LocalDate getLateStart() {
        return lateStart;
    }

    /**
     * Возвращает позднюю дату окончания работы.
     *
     * @return поздняя дата окончания
     */
    public LocalDate getLateFinish() {
        return lateFinish;
    }

    /**
     * Возвращает общий временной резерв работы в рабочих днях.
     *
     * @return общий временной резерв
     */
    public int getTotalFloat() {
        return totalFloat;
    }

    /**
     * Устанавливает ранние даты начала и окончания работы.
     *
     * @param start ранняя дата начала
     * @param finish ранняя дата окончания
     * @throws IllegalArgumentException если одна из дат равна null
     * или дата окончания раньше даты начала
     */
    public void setEarlyDates(LocalDate start, LocalDate finish) {
        if (start == null || finish == null) {
            throw new IllegalArgumentException("Даты не могут быть null");
        }

        if (finish.isBefore(start)) {
            throw new IllegalArgumentException("Дата окончания не может быть раньше даты начала");
        }

        this.earlyStart = start;
        this.earlyFinish = finish;
    }

    /**
     * Устанавливает поздние даты начала и окончания работы.
     *
     * @param start поздняя дата начала
     * @param finish поздняя дата окончания
     * @throws IllegalArgumentException если одна из дат равна null
     * или дата окончания раньше даты начала
     */
    public void setLateDates(LocalDate start, LocalDate finish) {
        if (start == null || finish == null) {
            throw new IllegalArgumentException("Даты не могут быть null");
        }

        if (finish.isBefore(start)) {
            throw new IllegalArgumentException("Дата окончания не может быть раньше даты начала");
        }

        this.lateStart = start;
        this.lateFinish = finish;
    }

    /**
     * Устанавливает общий временной резерв работы.
     *
     * @param totalFloat величина резерва в рабочих днях
     * @throws IllegalArgumentException если резерв отрицательный
     */
    public void setTotalFloat(int totalFloat) {
        if (totalFloat < 0 ) {
            throw new IllegalArgumentException("Резерв времени не может быть отрицательным");
        }

        this.totalFloat = totalFloat;
    }
}
