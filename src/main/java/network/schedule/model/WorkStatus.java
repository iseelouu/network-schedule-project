package network.schedule.model;

/**
 * Определяет текущий статус выполнения работы.
 */
public enum WorkStatus {
    /**
     * Работа ещё не начата.
     */
    NOT_STARTED,

    /**
     * Работа находится в процессе выполнения.
     */
    IN_PROGRESS,

    /**
     * Работа полностью завершена.
     */
    COMPLETED
}
