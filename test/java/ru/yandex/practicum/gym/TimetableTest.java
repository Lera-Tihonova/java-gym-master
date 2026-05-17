package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class TimetableTest {

    private TimeOfDay morning = new TimeOfDay(9, 0);
    private TimeOfDay evening = new TimeOfDay(18, 0);
    private Group groupA = new Group("A", Age.ADULT, 60);
    private Group groupB = new Group("B", Age.CHILD, 45);
    private Group groupC = new Group("C", Age.ADULT, 90);

    @Test
    void testAddAndGetTrainingSession() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Иванов", "Иван", "Иванович");
        // Правильный порядок: Group, Coach, DayOfWeek, TimeOfDay
        TrainingSession session = new TrainingSession(groupA, coach, DayOfWeek.MONDAY, morning);

        timetable.addNewTrainingSession(session);
        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, morning);

        assertEquals(1, sessions.size());
        assertEquals(session, sessions.get(0));
    }

    @Test
    void testGetTrainingSessionsForDay() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Петров", "Петр", "Петрович");

        timetable.addNewTrainingSession(new TrainingSession(groupA, coach, DayOfWeek.MONDAY, morning));
        timetable.addNewTrainingSession(new TrainingSession(groupB, coach, DayOfWeek.MONDAY, evening));
        timetable.addNewTrainingSession(new TrainingSession(groupC, coach, DayOfWeek.TUESDAY, morning));

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        assertEquals(2, mondaySessions.size());
    }

    @Test
    void testMultipleSessionsAtSameTime() {
        Timetable timetable = new Timetable();
        DayOfWeek day = DayOfWeek.MONDAY;

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Петр", "Петрович");

        TrainingSession session1 = new TrainingSession(groupA, coach1, day, morning);
        TrainingSession session2 = new TrainingSession(groupB, coach2, day, morning);

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(day, morning);
        assertEquals(2, sessions.size());
        assertTrue(sessions.contains(session1));
        assertTrue(sessions.contains(session2));
    }

    @Test
    void testEmptyListWhenNoSessions() {
        Timetable timetable = new Timetable();

        List<TrainingSession> sessionsByDay = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertTrue(sessionsByDay.isEmpty());

        List<TrainingSession> sessionsByDayAndTime = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, morning);
        assertTrue(sessionsByDayAndTime.isEmpty());
    }

    @Test
    void testGetCountByCoaches() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Сидорова", "Анна", "Ивановна");
        Coach coach2 = new Coach("Кузнецова", "Ольга", "Петровна");

        timetable.addNewTrainingSession(new TrainingSession(groupA, coach1, DayOfWeek.MONDAY, morning));
        timetable.addNewTrainingSession(new TrainingSession(groupB, coach1, DayOfWeek.TUESDAY, evening));
        timetable.addNewTrainingSession(new TrainingSession(groupC, coach1, DayOfWeek.WEDNESDAY, morning));
        timetable.addNewTrainingSession(new TrainingSession(groupA, coach2, DayOfWeek.MONDAY, evening));

        Map<String, Integer> result = timetable.getCountByCoaches();

        assertEquals(2, result.size());
        Map.Entry<String, Integer> first = result.entrySet().iterator().next();
        assertEquals("Сидорова Анна Ивановна", first.getKey());
        assertEquals(3, first.getValue());
    }
}
