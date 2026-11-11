package network.schedule.model;

import java.util.List;
import java.util.ArrayList;

public class Work {

    private String name;
    private int duration;
    private List<Work> preceding;
    private Executor executor;

    public Work(String name, int duration) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Некорректное название работы");
        }

        if (duration < 0) {
            throw new IllegalArgumentException("Длительность не может быть отрицательной");
        }

        this.name = name;
        this.duration = duration;
        this.preceding = new ArrayList<>();
    }

    public void addPreceding(Work precedingWork) {
        this.preceding.add(precedingWork);
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public List<Work> getPreceding() {
        return new ArrayList<>(preceding);
    }

    public void setExecutor(Executor executor) {
        if(executor == null) {
            throw new IllegalArgumentException("Исполнитель не может отсутствовать");
        }

        this.executor = executor;
    }

    public Executor getExecutor() {
        return executor;
    }
}
