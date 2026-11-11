package network.schedule.model;

public class Dependency {
    private DependencyType type;
    private Work precedingWork;
    private Work nextWork;

    public Dependency(Work precedingWork, Work nextWork, DependencyType type) {
        if (precedingWork == null) {
            throw new IllegalArgumentException("Предшествующая работа не может отсутствовать");
        }

        if (nextWork == null) {
            throw new IllegalArgumentException("Следующая работа не может отсутствовать");
        }

        if (type == null) {
            throw new IllegalArgumentException("Тип зависимости не может отсутствовать");
        }

        this.precedingWork = precedingWork;
        this.nextWork = nextWork;
        this.type = type;
    }

    public Work getPrecedingWork() {
        return precedingWork;
    }

    public Work getNextWork() {
        return nextWork;
    }

    public DependencyType getType() {
        return type;
    }
}
