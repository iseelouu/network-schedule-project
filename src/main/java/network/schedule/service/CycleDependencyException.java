package network.schedule.service;

import network.schedule.model.Work;

import java.util.ArrayList;
import java.util.List;

/**
 * Исключение, возникающее при обнаружении циклической
 * зависимости между работами проекта.
 * Содержит список работ, образующих цикл.
 */
public class CycleDependencyException extends RuntimeException {
    private final List<Work> cycleWorks;

    /**
     * Создаёт исключение с сообщением и списком работ, образующих цикл.
     *
     * @param message сообщение об ошибке
     * @param cycleWorks список работ, образующих цикл
     */
    public CycleDependencyException(String message, List<Work> cycleWorks) {
        super(message);
        this.cycleWorks = new ArrayList<>(cycleWorks);
    }
}