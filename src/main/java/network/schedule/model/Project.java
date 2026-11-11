package network.schedule.model;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private List<Work> works;

    public Project(){
        this.works = new ArrayList<>();
    }

    public void addWork(Work work) {
        if (!works.contains(work)) {
            this.works.add(work);
        }
    }

    public int countWork() {
        return works.size();
    }

    public List<Work> getWorks() {
        return new ArrayList<>(works);
    }
}
