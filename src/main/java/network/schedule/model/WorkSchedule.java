package network.schedule.model;

import java.time.LocalDate;

public class WorkSchedule {
    private LocalDate earlyStart;
    private LocalDate earlyFinish;
    private LocalDate lateStart;
    private LocalDate lateFinish;
    private int totalFloat;
    private Work work;

    public WorkSchedule(Work work) {
        if (work == null) {
            throw new IllegalArgumentException("Работа не может быть null");
        }

        this.work = work;
    }

    public Work getWork() {
        return work;
    }

    public LocalDate getEarlyStart() {
        return earlyStart;
    }

    public LocalDate getEarlyFinish() {
        return earlyFinish;
    }

    public LocalDate getLateStart() {
        return lateStart;
    }

    public LocalDate getLateFinish() {
        return lateFinish;
    }

    public int getTotalFloat() {
        return totalFloat;
    }

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

    public void setTotalFloat(int totalFloat) {
        if (totalFloat < 0 ) {
            throw new IllegalArgumentException("Резерв времени не может быть отрицательным");
        }

        this.totalFloat = totalFloat;
    }
}
