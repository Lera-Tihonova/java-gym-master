package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        //Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        // Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        //Проверить, что за понедельник в 14:00 не вернулось занятий
    }

}
    @Test
    void testMultipleSessionsAtSameTime() {
        Timetable timetable = new Timetable();
        DayOfWeek day = DayOfWeek.MONDAY;
        TimeOfDay time = TimeOfDay.MORNING;
        
        Coach coach1 = new Coach("Иван", "Иванов");
        Coach coach2 = new Coach("Петр", "Петров");
        
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
        
        Coach coach1 = new Coach("Анна", "Сидорова");
        Coach coach2 = new Coach("Ольга", "Кузнецова");
        
        timetable.addNewTrainingSession(new TrainingSession(DayOfWeek.MONDAY, TimeOfDay.MORNING, coach1, Group.A));
        timetable.addNewTrainingSession(new TrainingSession(DayOfWeek.TUESDAY, TimeOfDay.EVENING, coach1, Group.B));
        timetable.addNewTrainingSession(new TrainingSession(DayOfWeek.WEDNESDAY, TimeOfDay.MORNING, coach1, Group.C));
        
        timetable.addNewTrainingSession(new TrainingSession(DayOfWeek.MONDAY, TimeOfDay.EVENING, coach2, Group.A));
        
        Map<String, Integer> result = timetable.getCountByCoaches();
        
        assertEquals(2, result.size());
        Map.Entry<String, Integer> first = result.entrySet().iterator().next();
        assertEquals("Анна Сидорова", first.getKey());
        assertEquals(3, first.getValue());
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
