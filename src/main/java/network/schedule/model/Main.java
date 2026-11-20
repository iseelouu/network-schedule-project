
package network.schedule.model;

import network.schedule.service.ScheduleCalculator;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {

        Work work1 = new Work("Анализ требований", 3);
        Work work2 = new Work("Изучение предметной области", 2);
        Work work3 = new Work("Составление технического задания", 2);

        Work work4 = new Work("Проектирование архитектуры", 4);
        Work work5 = new Work("Проектирование базы данных", 3);
        Work work6 = new Work("Проектирование интерфейса", 3);

        Work work7 = new Work("Разработка серверной части", 5);
        Work work8 = new Work("Разработка клиентской части", 4);
        Work work9 = new Work("Разработка модуля авторизации", 3);
        Work work10 = new Work("Разработка модуля пользователей", 3);
        Work work11 = new Work("Разработка модуля отчетов", 4);

        Work work12 = new Work("Настройка базы данных", 2);
        Work work13 = new Work("Создание таблиц", 3);
        Work work14 = new Work("Реализация запросов", 3);

        Work work15 = new Work("Интеграция модулей", 3);
        Work work16 = new Work("Проверка взаимодействия модулей", 2);

        Work work17 = new Work("Модульное тестирование", 3);
        Work work18 = new Work("Интеграционное тестирование", 3);
        Work work19 = new Work("Системное тестирование", 4);
        Work work20 = new Work("Исправление ошибок", 3);

        Work work21 = new Work("Подготовка документации", 3);
        Work work22 = new Work("Написание инструкции пользователя", 2);
        Work work23 = new Work("Подготовка технической документации", 3);

        Work work24 = new Work("Подготовка сервера", 2);
        Work work25 = new Work("Настройка окружения", 2);
        Work work26 = new Work("Развертывание системы", 2);

        Work work27 = new Work("Приемочное тестирование", 2);
        Work work28 = new Work("Подготовка к запуску", 2);
        Work work29 = new Work("Запуск системы", 1);
        Work work30 = new Work("Завершение проекта", 1);


        work2.addPreceding(work1, DependencyType.FINISH_TO_START);
        work3.addPreceding(work1, DependencyType.FINISH_TO_START);

        work4.addPreceding(work3, DependencyType.FINISH_TO_START);
        work5.addPreceding(work4, DependencyType.START_TO_START);
        work6.addPreceding(work4, DependencyType.START_TO_START);

        work7.addPreceding(work4, DependencyType.FINISH_TO_START);
        work8.addPreceding(work6, DependencyType.FINISH_TO_START);
        work9.addPreceding(work7, DependencyType.START_TO_START);
        work10.addPreceding(work7, DependencyType.START_TO_START);
        work11.addPreceding(work7, DependencyType.START_TO_START);

        work12.addPreceding(work5, DependencyType.FINISH_TO_START);
        work13.addPreceding(work12, DependencyType.FINISH_TO_START);
        work14.addPreceding(work13, DependencyType.FINISH_TO_START);

        work15.addPreceding(work8, DependencyType.FINISH_TO_START);
        work15.addPreceding(work9, DependencyType.FINISH_TO_START);
        work15.addPreceding(work10, DependencyType.FINISH_TO_START);
        work15.addPreceding(work11, DependencyType.FINISH_TO_START);
        work15.addPreceding(work14, DependencyType.FINISH_TO_START);

        work16.addPreceding(work15, DependencyType.FINISH_TO_START);

        work17.addPreceding(work15, DependencyType.FINISH_TO_START);
        work18.addPreceding(work17, DependencyType.FINISH_TO_START);
        work19.addPreceding(work18, DependencyType.FINISH_TO_START);
        work20.addPreceding(work19, DependencyType.FINISH_TO_START);

        work21.addPreceding(work15, DependencyType.FINISH_TO_START);
        work22.addPreceding(work21, DependencyType.FINISH_TO_START);
        work23.addPreceding(work21, DependencyType.START_TO_START);

        work24.addPreceding(work16, DependencyType.FINISH_TO_START);
        work25.addPreceding(work24, DependencyType.FINISH_TO_START);
        work26.addPreceding(work25, DependencyType.FINISH_TO_START);

        work27.addPreceding(work20, DependencyType.FINISH_TO_START);
        work27.addPreceding(work26, DependencyType.FINISH_TO_START);

        work28.addPreceding(work22, DependencyType.FINISH_TO_START);
        work28.addPreceding(work23, DependencyType.FINISH_TO_START);
        work28.addPreceding(work27, DependencyType.FINISH_TO_START);

        work29.addPreceding(work28, DependencyType.FINISH_TO_START);
        work30.addPreceding(work29, DependencyType.FINISH_TO_START);

        Project project = new Project();

        project.addWork(work1);
        project.addWork(work2);
        project.addWork(work3);
        project.addWork(work4);
        project.addWork(work5);
        project.addWork(work6);
        project.addWork(work7);
        project.addWork(work8);
        project.addWork(work9);
        project.addWork(work10);
        project.addWork(work11);
        project.addWork(work12);
        project.addWork(work13);
        project.addWork(work14);
        project.addWork(work15);
        project.addWork(work16);
        project.addWork(work17);
        project.addWork(work18);
        project.addWork(work19);
        project.addWork(work20);
        project.addWork(work21);
        project.addWork(work22);
        project.addWork(work23);
        project.addWork(work24);
        project.addWork(work25);
        project.addWork(work26);
        project.addWork(work27);
        project.addWork(work28);
        project.addWork(work29);
        project.addWork(work30);


        Set<LocalDate> holidays = new HashSet<>();
        Set<MonthDay> annualHolidays = new HashSet<>();

        WorkCalendar calendar = new StandardWorkCalendar(
                holidays,
                annualHolidays
        );

        ScheduleCalculator calculator = new ScheduleCalculator(calendar);

        LocalDate projectStartDate = LocalDate.of(2026, 11, 23);

        ScheduleResult result = calculator.calculate(project, projectStartDate);

        for (WorkSchedule schedule : result.getSchedules()) {

            System.out.println("\n" + schedule.getWork().getName());

            System.out.println("Раннее начало: " + schedule.getEarlyStart());
            System.out.println("Раннее окончание: " + schedule.getEarlyFinish());

            System.out.println("Позднее начало: " + schedule.getLateStart());
            System.out.println("Позднее окончание: " + schedule.getLateFinish());

            System.out.println("Резерв: " + schedule.getTotalFloat());
        }

        System.out.println("\nОбщая длительность проекта: "
                + result.getProjectDuration() + " рабочих дней");

        for (Work work : result.getCriticalPath()) {
            System.out.println(work.getName());
        }
    }
}
