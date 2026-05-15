package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> schedule;

    public Timetable() {
        schedule = new HashMap<>();
        // Инициализируем структуру для всех дней недели
        for (DayOfWeek day : DayOfWeek.values()) {
            schedule.put(day, new HashMap<>());
            for (TimeOfDay time : TimeOfDay.values()) {
                schedule.get(day).put(time, new ArrayList<>());
            }
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        if (schedule.containsKey(day) && schedule.get(day).containsKey(time)) {
            schedule.get(day).get(time).add(trainingSession);
        }
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        List<TrainingSession> result = new ArrayList<>();
        if (schedule.containsKey(dayOfWeek)) {
            for (TimeOfDay time : TimeOfDay.values()) {
                result.addAll(schedule.get(dayOfWeek).get(time));
            }
        }
        return result;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (schedule.containsKey(dayOfWeek) && schedule.get(dayOfWeek).containsKey(timeOfDay)) {
            return new ArrayList<>(schedule.get(dayOfWeek).get(timeOfDay));
        }
        return new ArrayList<>();
    }
}
