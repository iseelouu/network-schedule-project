package network.schedule.model;

import java.util.ArrayList;
import java.util.List;

public class ScheduleResult {
    private List<WorkSchedule> schedules;
    private int projectDuration;
    private List<Work> criticalPath;

    public ScheduleResult(List<WorkSchedule> schedules,
                          int projectDuration,
                          List<Work> criticalPath) {
        if (schedules == null) {
            throw new IllegalArgumentException("Расписание работ не длжно быть null");
        }

        if (projectDuration < 0) {
            throw new IllegalArgumentException("Длительность работ не должна быть отрицательной");
        }

        if (criticalPath == null) {
            throw new IllegalArgumentException("Критический путь не должен быть null");
        }

        this.criticalPath = new ArrayList<>(criticalPath);

        this.schedules = new ArrayList<>(schedules);
        this.projectDuration = projectDuration;
    }

    public List<WorkSchedule> getSchedules() {
        return new ArrayList<>(schedules);
    }

    public int getProjectDuration() {
        return projectDuration;
    }

    public List<Work> getCriticalPath() {
        List<Work> result = new ArrayList<>(criticalPath);

        return result;
    }
}
