package com.example.Calendar_Assistant;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalendarServiceImpl implements CalendarService {


    private final Map<String, CalendarOwner> calendarOwners = new HashMap<>();

    @Override
    public void bookMeeting(String ownerId, Meeting meeting) {
        CalendarOwner owner = calendarOwners.get(ownerId);
        if (owner != null) {
            owner.getMeetings().add(meeting);
        } else {
            owner = new CalendarOwner(ownerId, new ArrayList<>());
            owner.getMeetings().add(meeting);
            calendarOwners.put(ownerId, owner);
        }
    }

    @Override
    public List<LocalDateTime> findFreeSlots(String ownerId1, String ownerId2, int durationInMinutes) {
        List<Meeting> owner1Meetings = calendarOwners.get(ownerId1).getMeetings();
        List<Meeting> owner2Meetings = calendarOwners.get(ownerId2).getMeetings();

        List<Meeting> mergedMeetings = new ArrayList<>(owner1Meetings);
        mergedMeetings.addAll(owner2Meetings);
        mergedMeetings.sort(Comparator.comparing(Meeting::getStartTime));

        List<LocalDateTime> freeSlots = new ArrayList<>();
        LocalDateTime startTime = LocalDateTime.MIN;

        for (Meeting meeting : mergedMeetings) {
            LocalDateTime endTime = meeting.getStartTime();

            if (Duration.between(startTime, endTime).toMinutes() >= durationInMinutes) {
                freeSlots.add(startTime);
                freeSlots.add(endTime);
            }

            startTime = meeting.getEndTime();
        }

        LocalDateTime endTime = LocalDateTime.MAX;
        if (Duration.between(startTime, endTime).toMinutes() >= durationInMinutes) {
            freeSlots.add(startTime);
            freeSlots.add(endTime);
        }

        return freeSlots;
    }

    @Override
    public List<String> findMeetingConflicts(Meeting meeting) {
        List<String> conflictedOwners = new ArrayList<>();

        for (Map.Entry<String, CalendarOwner> entry : calendarOwners.entrySet()) {
            CalendarOwner owner = entry.getValue();
            if (hasConflict(owner.getMeetings(), meeting)) {
                conflictedOwners.add(owner.getOwnerId());
            }
        }

        return conflictedOwners;
    }

    private boolean hasConflict(List<Meeting> meetings, Meeting newMeeting) {
        for (Meeting meeting : meetings) {
            if (newMeeting.getStartTime().isBefore(meeting.getEndTime())
                    && newMeeting.getEndTime().isAfter(meeting.getStartTime())) {
                return true;
            }
        }
        return false;
    }



}
