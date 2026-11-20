package network.schedule.service;
/**
 * Определяет состояние посещения работы
 */
public enum VisitState {
    /**
     * Работа ещё не посещалась при обходе графа.
     */
    NOT_VISITED,

    /**
     * Работа в данный момент обрабатывается.
     */
    VISITING,

    /**
     * Работа уже обработана.
     */
    VISITED
}
