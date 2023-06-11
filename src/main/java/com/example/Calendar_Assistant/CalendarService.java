package com.example.Calendar_Assistant;

import java.time.LocalDateTime;
import java.util.List;

public interface CalendarService {

    void bookMeeting(String ownerId, Meeting meeting);
    List<LocalDateTime> findFreeSlots(String ownerId1, String ownerId2, int durationInMinutes);
    List<String> findMeetingConflicts(Meeting meeting);

}
