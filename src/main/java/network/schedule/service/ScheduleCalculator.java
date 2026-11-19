package network.schedule.service;

import network.schedule.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ScheduleCalculator {
    private WorkCalendar calendar;

    public ScheduleCalculator(WorkCalendar calendar) {
        if(calendar == null) {
            throw new IllegalArgumentException("Календарь не может быть null");
        }

        this.calendar = calendar;
    }
    public List<WorkSchedule> calculate(Project project, LocalDate projectStartDate) {
        if (project == null) {
            throw new IllegalArgumentException("Проект не может быть null");
        }

        if (projectStartDate == null) {
            throw new IllegalArgumentException("Дата начала проекта не может быть null");
        }

        List<Work> orderedWorks = new ArrayList<>();
        Map<Work, VisitState> visitStates = new HashMap<>();
        for (Work work : project.getWorks()) {
            visitStates.put(work, VisitState.NOT_VISITED);
        }

        List<Work> path = new ArrayList<>();

        for (Work work : project.getWorks()) {
            if (visitStates.get(work) == VisitState.NOT_VISITED) {
                visit(work, visitStates, path, orderedWorks);
            }
        }

        Map<Work, WorkSchedule> schedules =
                calculateEarlyDates(orderedWorks, projectStartDate);

        calculateLateDates(orderedWorks, schedules);
        return new ArrayList<>(schedules.values());
    }

    private void visit(
            Work work,
            Map<Work, VisitState> visitStates,
            List<Work> path,
            List<Work> orderedWorks) {

        VisitState state = visitStates.get(work);

        if (state == VisitState.VISITED) {
            return;
        }

        if (state == VisitState.VISITING) {
            int cycleStartIndex = path.indexOf(work);
            List<Work> cycleWorks = new ArrayList<>(
                    path.subList(cycleStartIndex, path.size())
            );
            throw new CycleDependencyException(
                    "Обнаружена циклическая зависимость",
                    cycleWorks
            );
        }

        visitStates.put(work, VisitState.VISITING);
        path.add(work);

        for(Dependency dependency : work.getDependencies()) {
            visit(dependency.getPrecedingWork(), visitStates, path, orderedWorks);
        }

        path.remove(path.size() - 1);
        visitStates.put(work, VisitState.VISITED);
        orderedWorks.add(work);
    }

    private Map<Work, WorkSchedule> calculateEarlyDates(
            List<Work> orderedWorks,
            LocalDate projectStartDate) {
        Map<Work, WorkSchedule> schedules = new HashMap<>();

        for (Work work : orderedWorks) {
            LocalDate earlyStart = projectStartDate;
            for (Dependency dependency : work.getDependencies()) {
                WorkSchedule precedingSchedule = schedules.get(dependency.getPrecedingWork());
                if (dependency.getType() == DependencyType.FINISH_TO_START) {
                    LocalDate earlyFinish = precedingSchedule.getEarlyFinish();
                    LocalDate nextDay = calendar.getNextWorkingDay(earlyFinish);
                    if (nextDay.isAfter(earlyStart)) {
                        earlyStart = nextDay; //ранняя дата начала
                    }
                }

                if(dependency.getType() == DependencyType.START_TO_START) {
                    LocalDate precedingEralyStart = precedingSchedule.getEarlyStart();
                     if(precedingEralyStart.isAfter(earlyStart)) {
                         earlyStart = precedingEralyStart;
                     }
                }

                if(dependency.getType() == DependencyType.FINISH_TO_FINISH) {
                    LocalDate precedingEarlyFinish = precedingSchedule.getEarlyFinish();
                    LocalDate startByFinish = precedingEarlyFinish;

                    for(int i = 1; i < work.getDuration(); i++) {
                        startByFinish = calendar.getPreviousWorkingDay(startByFinish);
                    }

                    if(startByFinish.isAfter(earlyStart)) {
                        earlyStart = startByFinish;
                    }
                }

                if(dependency.getType() == DependencyType.START_TO_FINISH) {
                    LocalDate precedingEarlyStart = precedingSchedule.getEarlyStart();
                    LocalDate startByFinish = precedingEarlyStart;

                    for(int i = 1; i < work.getDuration(); i++) {
                        startByFinish = calendar.getPreviousWorkingDay(startByFinish);
                    }

                    if(startByFinish.isAfter(earlyStart)) {
                        earlyStart = startByFinish;
                    }
                }
            }

            LocalDate earlyFinish = calendar.calculateEndDate(earlyStart, work.getDuration());

            WorkSchedule schedule = new WorkSchedule(work);
            schedule.setEarlyDates(earlyStart, earlyFinish);

            schedules.put(work, schedule);

        }
        return schedules;
    }

    private Map<Work, WorkSchedule> calculateLateDates(
            List<Work> orderedWorks,
            Map<Work, WorkSchedule> schedules) {

        LocalDate projectFinish = calculateProjectFinish(orderedWorks, schedules);

        for(int i = orderedWorks.size() - 1; i >= 0; i--) {
            Work work = orderedWorks.get(i);
            WorkSchedule schedule = schedules.get(work);
            LocalDate lateFinish = projectFinish;
            LocalDate lateStart = lateFinish;
            for(int j = 1; j < work.getDuration(); j++) {
                lateStart = calendar.getPreviousWorkingDay(lateStart);
            }

            for(Work nextWork : orderedWorks) {
                for (Dependency dependency : nextWork.getDependencies()) {
                    if (dependency.getPrecedingWork() == work) {
                        WorkSchedule nextSchedule = schedules.get(nextWork);

                        if (dependency.getType() == DependencyType.FINISH_TO_START) {
                            LocalDate nextLateStart = nextSchedule.getLateStart();
                            LocalDate precedingLateFinish = calendar.getPreviousWorkingDay(nextLateStart);
                            if (precedingLateFinish.isBefore(lateFinish)) {
                                lateFinish = precedingLateFinish;
                                lateStart = lateFinish;
                                for(int k= 1; k < work.getDuration(); k++) {
                                    lateStart = calendar.getPreviousWorkingDay(lateStart);
                                }
                            }
                        }

                        if (dependency.getType() == DependencyType.START_TO_START) {
                            LocalDate nextLateStart = nextSchedule.getLateStart();
                            if (nextLateStart.isBefore(lateStart)) {
                                lateStart = nextLateStart;
                                lateFinish = calendar.calculateEndDate(lateStart, work.getDuration());
                            }
                        }

                        if(dependency.getType() == DependencyType.FINISH_TO_FINISH) {
                            LocalDate nextLateFinish = nextSchedule.getLateFinish();
                            if(nextLateFinish.isBefore(lateFinish)){
                                lateFinish = nextLateFinish;

                                lateStart = lateFinish;
                                for(int k= 1; k < work.getDuration(); k++) {
                                    lateStart = calendar.getPreviousWorkingDay(lateStart);
                                }
                            }
                        }

                        if(dependency.getType() == DependencyType.START_TO_FINISH) {
                            LocalDate nextLateFinish = nextSchedule.getLateFinish();
                            if(nextLateFinish.isBefore(lateStart)) {
                                lateStart = nextLateFinish;
                                lateFinish = calendar.calculateEndDate(lateStart, work.getDuration());
                            }
                        }
                    }
                }
            }
            schedule.setLateDates(lateStart, lateFinish);
            schedule.setTotalFloat(calculateTotalFloat(schedule.getEarlyStart(), lateStart));
        }
        return schedules;
    }

    private LocalDate calculateProjectFinish(
            List<Work> orderedWorks,
            Map<Work, WorkSchedule> schedules) {

        LocalDate projectFinish = null;
        for(Work work : orderedWorks) {
            WorkSchedule schedule = schedules.get(work);

            LocalDate earlyFinish = schedule.getEarlyFinish();
            if(projectFinish == null || earlyFinish.isAfter(projectFinish)) {
                projectFinish = earlyFinish;
            }
        }
        return projectFinish;
    }

    private int calculateTotalFloat(LocalDate earlyStart, LocalDate lateStart) {
        int totalFloat = 0;
        LocalDate currentDate = earlyStart;

        while(currentDate.isBefore(lateStart)) {
            currentDate = calendar.getNextWorkingDay(currentDate);
            totalFloat++;
        }

        return totalFloat;
    }
}
