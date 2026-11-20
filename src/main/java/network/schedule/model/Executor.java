package network.schedule.model;

/**
 * Представляет исполнителя, назначенного на выполнение работ проекта.
 */
public class Executor {
    private String name;

    /**
     * Создаёт исполнителя.
     *
     * @param name имя исполнителя
     * @throws IllegalArgumentException если имя равно null или пустое
     */
    public Executor(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Некорректное имя");
        }

        this.name = name;
    }

    /**
     * Возвращает имя исполнителя.
     *
     * @return имя исполнителя
     */
    public String getName() {
        return name;
    }
}
