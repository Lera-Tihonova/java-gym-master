package ru.yandex.practicum.gym;

import ru.yandex.practicum.gym.model.*;
import java.util.*;

public class Timetable {
    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> schedule = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> dayMap =
                schedule.computeIfAbsent(day, k -> new TreeMap<>());

        List<TrainingSession> sessions =
                dayMap.computeIfAbsent(time, k -> new ArrayList<>());

        sessions.add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = schedule.get(dayOfWeek);
        if (dayMap == null) {
            return Collections.emptyList();
        }
        List<TrainingSession> result = new ArrayList<>();
        for (List<TrainingSession> list : dayMap.values()) {
            result.addAll(list);
        }
        return result;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = schedule.get(dayOfWeek);
        if (dayMap == null) {
            return Collections.emptyList();
        }
        List<TrainingSession> sessions = dayMap.get(timeOfDay);
        return sessions != null ? Collections.unmodifiableList(sessions) : Collections.emptyList();
    }

    public List<Map.Entry<Coach, Integer>> getCountByCoaches() {
        Map<Coach, Integer> counter = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> dayMap : schedule.values()) {
            for (List<TrainingSession> sessions : dayMap.values()) {
                for (TrainingSession ts : sessions) {
                    Coach coach = ts.getCoach();
                    counter.put(coach, counter.getOrDefault(coach, 0) + 1);
                }
            }
        }
        List<Map.Entry<Coach, Integer>> list = new ArrayList<>(counter.entrySet());
        list.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        return list;
    }
}