package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class TimetableTest {

    @Test
    void testAddAndGetTrainingSession() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Иванов", "Иван", "Иванович");
        TrainingSession session = new TrainingSession(DayOfWeek.MONDAY, TimeOfDay.MORNING, coach, Group.A);

        timetable.addNewTrainingSession(session);
        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, TimeOfDay.MORNING);

        assertEquals(1, sessions.size());
        assertEquals(session, sessions.get(0));
    }

    @Test
    void testGetTrainingSessionsForDay() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Петров", "Петр", "Петрович");

        timetable.addNewTrainingSession(new TrainingSession(DayOfWeek.MONDAY, TimeOfDay.MORNING, coach, Group.A));
        timetable.addNewTrainingSession(new TrainingSession(DayOfWeek.MONDAY, TimeOfDay.EVENING, coach, Group.B));
        timetable.addNewTrainingSession(new TrainingSession(DayOfWeek.TUESDAY, TimeOfDay.MORNING, coach, Group.C));

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        assertEquals(2, mondaySessions.size());
    }

    @Test
    void testMultipleSessionsAtSameTime() {
        Timetable timetable = new Timetable();
        DayOfWeek day = DayOfWeek.MONDAY;
        TimeOfDay time = TimeOfDay.MORNING;

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Петр", "Петрович");

        TrainingSession session1 = new TrainingSession(day, time, coach1, Group.A);
        TrainingSession session2 = new TrainingSession(day, time, coach2, Group.B);

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(day, time);
        assertEquals(2, sessions.size());
        assertTrue(sessions.contains(session1));
        assertTrue(sessions.contains(session2));
    }

    @Test
    void testEmptyListWhenNoSessions() {
        Timetable timetable = new Timetable();

        List<TrainingSession> sessionsByDay = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertTrue(sessionsByDay.isEmpty());

        List<TrainingSession> sessionsByDayAndTime = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, TimeOfDay.MORNING);
        assertTrue(sessionsByDayAndTime.isEmpty());
    }

    @Test
    void testGetCountByCoaches() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Сидорова", "Анна", "Ивановна");
        Coach coach2 = new Coach("Кузнецова", "Ольга", "Петровна");

        timetable.addNewTrainingSession(new TrainingSession(DayOfWeek.MONDAY, TimeOfDay.MORNING, coach1, Group.A));
        timetable.addNewTrainingSession(new TrainingSession(DayOfWeek.TUESDAY, TimeOfDay.EVENING, coach1, Group.B));
        timetable.addNewTrainingSession(new TrainingSession(DayOfWeek.WEDNESDAY, TimeOfDay.MORNING, coach1, Group.C));
        timetable.addNewTrainingSession(new TrainingSession(DayOfWeek.MONDAY, TimeOfDay.EVENING, coach2, Group.A));

        Map<String, Integer> result = timetable.getCountByCoaches();

        assertEquals(2, result.size());
        Map.Entry<String, Integer> first = result.entrySet().iterator().next();
        assertEquals("Сидорова Анна Ивановна", first.getKey());
        assertEquals(3, first.getValue());
    }
}
