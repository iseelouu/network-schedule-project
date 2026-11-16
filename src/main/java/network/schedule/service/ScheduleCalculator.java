package network.schedule.service;

import network.schedule.model.Dependency;
import network.schedule.model.Project;
import network.schedule.model.Work;
import network.schedule.model.WorkCalendar;

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
    public List<Work> calculate(Project project, LocalDate projectStartDate) {
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
        return orderedWorks;
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

}
