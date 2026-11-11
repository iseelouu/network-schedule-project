package network.schedule.model;

public class Executor {
    private String name;

    public Executor(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Некорректное имя");
        }

        this.name = name;
    }
}
