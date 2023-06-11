package com.example.Calendar_Assistant;

import java.util.List;

public class CalendarOwner {

    private String ownerId;
    private List<Meeting> meetings;


    public CalendarOwner(String ownerId, List<Meeting> meetings) {
        this.ownerId = ownerId;
        this.meetings = meetings;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }
}
