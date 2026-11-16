package network.schedule.service;

import network.schedule.model.Work;

import java.util.ArrayList;
import java.util.List;

public class CycleDependencyException extends RuntimeException {
    private final List<Work> cycleWorks;

    public CycleDependencyException(String message, List<Work> cycleWorks) {
        super(message);
        this.cycleWorks = new ArrayList<>(cycleWorks);
    }

    public List<Work> getCycleWorks() {
        return new ArrayList<>(cycleWorks);
    }
}
