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
        TrainingSession session = new TrainingSession(DayOfWeek.MONDAY, morning, coach, groupA);

        timetable.addNewTrainingSession(session);
        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, morning);

        assertEquals(1, sessions.size());
        assertEquals(session, sessions.get(0));
    }

    @Test
    void testGetTrainingSessionsForDay() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Петров", "Петр", "Петрович");

        timetable.addNewTrainingSession(new TrainingSession(DayOfWeek.MONDAY, morning, coach, groupA));
        timetable.addNewTrainingSession(new TrainingSession(DayOfWeek.MONDAY, evening, coach, groupB));
        timetable.addNewTrainingSession(new TrainingSession(DayOfWeek.TUESDAY, morning, coach, groupC));

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        assertEquals(2, mondaySessions.size());
    }

    @Test
    void testMultipleSessionsAtSameTime() {
        Timetable timetable = new Timetable();
        DayOfWeek day = DayOfWeek.MONDAY;

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Петр", "Петрович");

        TrainingSession session1 = new TrainingSession(day, morning, coach1, groupA);
        TrainingSession session2 = new TrainingSession(day, morning, coach2, groupB);

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

        timetable.addNewTrainingSession(new TrainingSession(DayOfWeek.MONDAY, morning, coach1, groupA));
        timetable.addNewTrainingSession(new TrainingSession(DayOfWeek.TUESDAY, evening, coach1, groupB));
        timetable.addNewTrainingSession(new TrainingSession(DayOfWeek.WEDNESDAY, morning, coach1, groupC));
        timetable.addNewTrainingSession(new TrainingSession(DayOfWeek.MONDAY, evening, coach2, groupA));

        Map<String, Integer> result = timetable.getCountByCoaches();

        assertEquals(2, result.size());
        Map.Entry<String, Integer> first = result.entrySet().iterator().next();
        assertEquals("Сидорова Анна Ивановна", first.getKey());
        assertEquals(3, first.getValue());
    }
}
