package network.schedule.service;


import network.schedule.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleCalculatorTest {
    @Test
    void shouldThrowExceptionWhenCycleExists() {
        Work work1 = new Work("Работа 1", 2);
        Work work2 = new Work("Работа 2", 3);
        Work work3 = new Work("Работа 3", 1);

        work1.addPreceding(work3, DependencyType.FINISH_TO_START);
        work2.addPreceding(work1, DependencyType.FINISH_TO_START);
        work3.addPreceding(work2, DependencyType.FINISH_TO_START);

        Project project = new Project();
        project.addWork(work1);
        project.addWork(work2);
        project.addWork(work3);

        ScheduleCalculator calculator = new ScheduleCalculator(new StandardWorkCalendar(Set.of(), Set.of()));

        assertThrows(
                CycleDependencyException.class,
                () -> calculator.calculate(project, LocalDate.of(2026, 11, 17))
        );
    }

    @Test
    void shouldNotThrowExceptionWhenNoCycleExists() {
        Work work1 = new Work("Работа 1", 2);
        Work work2 = new Work("Работа 2", 3);
        Work work3 = new Work("Работа 3", 1);

        work2.addPreceding(work1, DependencyType.FINISH_TO_START);
        work3.addPreceding(work2, DependencyType.FINISH_TO_START);

        Project project = new Project();
        project.addWork(work1);
        project.addWork(work2);
        project.addWork(work3);

        ScheduleCalculator calculator = new ScheduleCalculator(
                new StandardWorkCalendar(Set.of(), Set.of())
        );

        assertDoesNotThrow(
                () -> calculator.calculate(project, LocalDate.of(2026, 11, 17))
        );
    }

    @Test
    void shouldThrowExceptionWhenPrecedingWorkIsNotInProject() {
        Work work1 = new Work("Работа 1", 2);
        Work work2 = new Work("Работа 2", 3);

        work2.addPreceding(work1, DependencyType.FINISH_TO_START);

        Project project = new Project();
        project.addWork(work2);

        ScheduleCalculator calculator = new ScheduleCalculator(
                new StandardWorkCalendar(Set.of(), Set.of())
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculate(project, LocalDate.of(2026, 11, 17))
        );
    }

    @Test
    void shouldCalculateEarlyDatesForSequentialWorks() {
        Work work1 = new Work("Работа 1", 2);
        Work work2 = new Work("Работа 2", 3);

        work2.addPreceding(work1, DependencyType.FINISH_TO_START);

        Project project = new Project();
        project.addWork(work1);
        project.addWork(work2);

        ScheduleCalculator calculator = new ScheduleCalculator(
                new StandardWorkCalendar(Set.of(), Set.of())
        );

        ScheduleResult result = calculator.calculate(
                project, LocalDate.of(2026, 11, 17)
        );

        WorkSchedule schedule1 = result.getSchedules().get(0);
        WorkSchedule schedule2 = result.getSchedules().get(1);

        assertEquals(LocalDate.of(2026, 11, 17), schedule1.getEarlyStart());
        assertEquals(LocalDate.of(2026, 11, 18), schedule1.getEarlyFinish());

        assertEquals(LocalDate.of(2026, 11, 19), schedule2.getEarlyStart());
        assertEquals(LocalDate.of(2026, 11, 23), schedule2.getEarlyFinish());
    }

    @Test
    void shouldCalculateLateDatesForSequentialWorks() {
        Work work1 = new Work("Работа 1", 2);
        Work work2 = new Work("Работа 2", 3);

        work2.addPreceding(work1, DependencyType.FINISH_TO_START);

        Project project = new Project();
        project.addWork(work1);
        project.addWork(work2);

        ScheduleCalculator calculator = new ScheduleCalculator(
                new StandardWorkCalendar(Set.of(), Set.of())
        );

        ScheduleResult result = calculator.calculate(
                project, LocalDate.of(2026, 11, 17)
        );

        WorkSchedule schedule1 = result.getSchedules().get(0);
        WorkSchedule schedule2 = result.getSchedules().get(1);

        assertEquals(LocalDate.of(2026, 11, 17), schedule1.getLateStart());
        assertEquals(LocalDate.of(2026, 11, 18), schedule1.getLateFinish());

        assertEquals(LocalDate.of(2026, 11, 19), schedule2.getLateStart());
        assertEquals(LocalDate.of(2026, 11, 23), schedule2.getLateFinish());
    }

    @Test
    void shouldCalculateProjectDuration() {
        Work work1 = new Work("Работа 1", 2);
        Work work2 = new Work("Работа 2", 3);

        work2.addPreceding(work1, DependencyType.FINISH_TO_START);

        Project project = new Project();
        project.addWork(work1);
        project.addWork(work2);

        ScheduleCalculator calculator = new ScheduleCalculator(
                new StandardWorkCalendar(Set.of(), Set.of())
        );

        ScheduleResult result = calculator.calculate(
                project, LocalDate.of(2026, 11, 17)
        );

        assertEquals(5, result.getProjectDuration());
    }

    @Test
    void shouldCalculateTotalFloat() {
        Work work1 = new Work("Работа 1", 2);
        Work work2 = new Work("Работа 2", 4);

        Project project = new Project();
        project.addWork(work1);
        project.addWork(work2);

        ScheduleCalculator calculator = new ScheduleCalculator(
                new StandardWorkCalendar(Set.of(), Set.of())
        );

        ScheduleResult result = calculator.calculate(
                project, LocalDate.of(2026, 11, 17)
        );

        WorkSchedule schedule1 = result.getSchedules().get(0);
        WorkSchedule schedule2 = result.getSchedules().get(1);

        assertEquals(2, schedule1.getTotalFloat());
        assertEquals(0, schedule2.getTotalFloat());
    }

    @Test
    void shouldFindCriticalPath() {
        Work work1 = new Work("Работа 1", 2);
        Work work2 = new Work("Работа 2", 3);
        Work work3 = new Work("Работа 3", 2);

        work2.addPreceding(work1, DependencyType.FINISH_TO_START);
        work3.addPreceding(work2, DependencyType.FINISH_TO_START);

        Project project = new Project();
        project.addWork(work1);
        project.addWork(work2);
        project.addWork(work3);

        ScheduleCalculator calculator = new ScheduleCalculator(
                new StandardWorkCalendar(Set.of(), Set.of())
        );

        ScheduleResult result = calculator.calculate(
                project, LocalDate.of(2026, 11, 17)
        );

        List<Work> criticalPath = result.getCriticalPath();

        assertEquals(3, criticalPath.size());
        assertTrue(criticalPath.contains(work1));
        assertTrue(criticalPath.contains(work2));
        assertTrue(criticalPath.contains(work3));
    }

    @Test
    void shouldCalculateEmptyProject() {
        Project project = new Project();

        ScheduleCalculator calculator = new ScheduleCalculator(
                new StandardWorkCalendar(Set.of(), Set.of())
        );

        ScheduleResult result = calculator.calculate(
                project, LocalDate.of(2026, 11, 17)
        );

        assertTrue(result.getSchedules().isEmpty());
        assertEquals(0, result.getProjectDuration());
        assertTrue(result.getCriticalPath().isEmpty());
    }

    @Test
    void shouldCalculateProjectWithOneWork() {
        Work work = new Work("Работа 1", 3);

        Project project = new Project();
        project.addWork(work);

        ScheduleCalculator calculator = new ScheduleCalculator(
                new StandardWorkCalendar(Set.of(), Set.of())
        );

        ScheduleResult result = calculator.calculate(
                project, LocalDate.of(2026, 11, 17)
        );

        WorkSchedule schedule = result.getSchedules().get(0);

        assertEquals(LocalDate.of(2026, 11, 17), schedule.getEarlyStart());
        assertEquals(LocalDate.of(2026, 11, 19), schedule.getEarlyFinish());
    }

    @Test
    void shouldSkipHolidaysWhenCalculatingDates() {
        Work work = new Work("Работа 1", 3);

        Project project = new Project();
        project.addWork(work);

        StandardWorkCalendar calendar = new StandardWorkCalendar(
                Set.of(LocalDate.of(2026, 11, 18)),
                Set.of()
        );

        ScheduleCalculator calculator = new ScheduleCalculator(calendar);

        ScheduleResult result = calculator.calculate(
                project, LocalDate.of(2026, 11, 17)
        );

        WorkSchedule schedule = result.getSchedules().get(0);

        assertEquals(LocalDate.of(2026, 11, 17), schedule.getEarlyStart());
        assertEquals(LocalDate.of(2026, 11, 20), schedule.getEarlyFinish());
    }

    @Test
    void shouldSkipWeekendsWhenCalculatingDates() {
        Work work = new Work("Работа 1", 2);

        Project project = new Project();
        project.addWork(work);

        ScheduleCalculator calculator = new ScheduleCalculator(
                new StandardWorkCalendar(Set.of(), Set.of())
        );

        ScheduleResult result = calculator.calculate(
                project, LocalDate.of(2026, 11, 21)
        );

        WorkSchedule schedule = result.getSchedules().get(0);

        assertEquals(LocalDate.of(2026, 11, 23), schedule.getEarlyStart());
        assertEquals(LocalDate.of(2026, 11, 24), schedule.getEarlyFinish());
    }
}
