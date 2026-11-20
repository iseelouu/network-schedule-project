package network.schedule.model;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

/**
 * Представляет работу, входящую в состав проекта.
 * Содержит название, продолжительность, зависимости,
 * исполнителя, статус и дату начала работы.
 */
public class Work {

    private String name;
    private int duration;
    private List<Dependency> dependencies;
    private Executor executor;
    private WorkStatus status;
    private LocalDate startDate;

    /**
     * Создаёт новую работу.
     *
     * @param name название работы
     * @param duration продолжительность работы в рабочих днях
     * @throws IllegalArgumentException если название некорректно
     * или продолжительность отрицательная
     */
    public Work(String name, int duration) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Некорректное название работы");
        }

        if (duration < 0) {
            throw new IllegalArgumentException("Длительность не может быть отрицательной");
        }

        this.name = name;
        this.duration = duration;
        this.dependencies = new ArrayList<>();
        this.status = WorkStatus.NOT_STARTED;
    }

    /**
     * Добавляет предшествующую работу и тип зависимости.
     *
     * @param precedingWork предшествующая работа
     * @param type тип зависимости
     * @throws IllegalArgumentException если работа равна null,
     * является самой собой или уже добавлена
     */
    public void addPreceding(Work precedingWork, DependencyType type) {
        if (precedingWork == null) {
            throw new IllegalArgumentException("Предшествующая работа не может быть null");
        }

        if (precedingWork == this) {
            throw new IllegalArgumentException("Работа не может зависеть от самой себя");
        }

        for(int i = 0; i < dependencies.size(); i++) {
            if (dependencies.get(i).getPrecedingWork() == precedingWork && dependencies.get(i).getType() == type) {
                throw new IllegalArgumentException("Такая зависимость уже существует");
            }
        }

        Dependency depending = new Dependency(precedingWork, this, type);
        this.dependencies.add(depending);
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    /**
     * Возвращает список зависимостей работы.
     *
     * @return копия списка зависимостей
     */
    public List<Dependency> getDependencies() {
        return new ArrayList<>(dependencies);
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

    public WorkStatus getStatus() {
        return status;
    }

    public void setStatus(WorkStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Состояние работы не может отсутствовать");
        }
        this.status = status;
    }

    public void setStartDate(LocalDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Дата начала не может быть null");
        }

        this.startDate = startDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

}
