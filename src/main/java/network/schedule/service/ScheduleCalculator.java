package network.schedule.service;

import network.schedule.model.Dependency;
import network.schedule.model.Project;
import network.schedule.model.Work;

import java.util.ArrayList;
import java.util.List;

public class ScheduleCalculator {
    // для каждой работы формируется список ее предшественников
    public void calculate(Project project) {
        List<Work> orderedWorks = new ArrayList<>();
        boolean workAdded = true;

        while (workAdded) {
            workAdded = false;
            for (Work work : project.getWorks()) {
                List<Work> precedingWorks = new ArrayList<>();
                List<Dependency> dependencies = work.getDependencies();

                for (int i = 0; i < dependencies.size(); i++) {
                    precedingWorks.add(dependencies.get(i).getPrecedingWork());
                }

                boolean allPrecedingWorksAdded = true;

                for (Work precedingWork : precedingWorks) {
                    if (!orderedWorks.contains(precedingWork)) {
                        allPrecedingWorksAdded = false;
                    }
                }

                if (allPrecedingWorksAdded && !orderedWorks.contains(work)) {
                    orderedWorks.add(work);
                    workAdded = true;
                }
            }
        }

        if(orderedWorks.size() != project.getWorks().size()) {


        }
    }

}
