package network.schedule.model;

import java.util.List;
import java.util.ArrayList;

public class Work {

    private String name;
    private int duration;
    private List<Work> preceding;

    public Work(String name, int duration) {
        this.name = name;
        this.duration = duration;
        this.preceding = new ArrayList<>();
    }

    public void addPreceding(Work precedingWork) {
        this.preceding.add(precedingWork);
    }
}
