package network.schedule.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Представляет проект, содержащий список работ.
 * Позволяет добавлять работы и получать их список.
 */
public class Project {
    private List<Work> works;

    /**
     * Создаёт пустой проект.
     */
    public Project(){
        this.works = new ArrayList<>();
    }

    /**
     * Добавляет работу в проект.
     *
     * @param work работа, которую необходимо добавить
     * @throws IllegalArgumentException если работа равна null
     */
    public void addWork(Work work) {
        if (work == null) {
            throw new IllegalArgumentException("Нельзя добавить null вместо работы");
        }
        if (!works.contains(work)) {
            this.works.add(work);
        }
    }

    /**
     * Возвращает список работ проекта.
     *
     * @return копия списка работ
     */
    public List<Work> getWorks() {
        return new ArrayList<>(works);
    }
}
