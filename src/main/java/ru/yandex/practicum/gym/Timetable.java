package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> schedule;

    public Timetable() {
        schedule = new TreeMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            schedule.put(day, new TreeMap<>());
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        if (trainingSession == null) {
            return;
        }
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        if (!schedule.containsKey(day)) {
            schedule.put(day, new TreeMap<>());
        }
        if (!schedule.get(day).containsKey(time)) {
            schedule.get(day).put(time, new ArrayList<>());
        }
        schedule.get(day).get(time).add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if (dayOfWeek == null || !schedule.containsKey(dayOfWeek)) {
            return Collections.emptyList();
        }
        List<TrainingSession> result = new ArrayList<>();
        for (List<TrainingSession> sessions : schedule.get(dayOfWeek).values()) {
            result.addAll(sessions);
        }
        return result;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (dayOfWeek == null || timeOfDay == null ||
            !schedule.containsKey(dayOfWeek) ||
            !schedule.get(dayOfWeek).containsKey(timeOfDay)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(schedule.get(dayOfWeek).get(timeOfDay));
    }

    public Map<String, Integer> getCountByCoaches() {
        Map<String, Integer> coachCount = new HashMap<>();

        for (Map<TimeOfDay, List<TrainingSession>> dayMap : schedule.values()) {
            for (List<TrainingSession> sessions : dayMap.values()) {
                for (TrainingSession session : sessions) {
                    Coach coach = session.getCoach();
                    String fullName = coach.getSurname() + " " + coach.getName() + " " + coach.getMiddleName();
                    coachCount.put(fullName, coachCount.getOrDefault(fullName, 0) + 1);
                }
            }
        }

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(coachCount.entrySet());
        entries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        Map<String, Integer> sortedResult = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entries) {
            sortedResult.put(entry.getKey(), entry.getValue());
        }
        return sortedResult;
    }
}
