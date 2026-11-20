package network.schedule.model;

/**
 * Представляет зависимость между двумя работами проекта.
 * Хранит предшествующую и последующую работу,
 * а также тип зависимости между ними.
 */
public class Dependency {
    private DependencyType type;
    private Work precedingWork;
    private Work nextWork;

    /**
     * Создаёт зависимость между работами.
     *
     * @param precedingWork предшествующая работа
     * @param nextWork последующая работа
     * @param type тип зависимости
     * @throws IllegalArgumentException если один из параметров равен null
     */
    public Dependency(Work precedingWork, Work nextWork, DependencyType type) {
        if (type == null) {
            throw new IllegalArgumentException("Тип зависимости не может отсутствовать");
        }

        this.precedingWork = precedingWork;
        this.nextWork = nextWork;
        this.type = type;
    }

    /**
     * Возвращает предшествующую работу.
     *
     * @return предшествующая работа
     */
    public Work getPrecedingWork() {
        return precedingWork;
    }

    /**
     * Возвращает последующую работу.
     *
     * @return последующая работа
     */
    public Work getNextWork() {
        return nextWork;
    }

    /**
     * Возвращает тип зависимости.
     *
     * @return тип зависимости
     */
    public DependencyType getType() {
        return type;
    }
}
