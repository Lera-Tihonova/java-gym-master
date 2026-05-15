package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> schedule;

    public Timetable() {
        schedule = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            schedule.put(day, new HashMap<>());
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();
        
        if (!schedule.containsKey(day)) {
            schedule.put(day, new HashMap<>());
        }
        if (!schedule.get(day).containsKey(time)) {
            schedule.get(day).put(time, new ArrayList<>());
        }
        schedule.get(day).get(time).add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        List<TrainingSession> result = new ArrayList<>();
        if (schedule.containsKey(dayOfWeek)) {
            for (List<TrainingSession> sessions : schedule.get(dayOfWeek).values()) {
                result.addAll(sessions);
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
