package com.example.Calendar_Assistant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")

public class CalendarController {


    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @PostMapping("/book/{ownerId}")
    public ResponseEntity<String> bookMeeting(@PathVariable String ownerId, @RequestBody Meeting meeting) {
        calendarService.bookMeeting(ownerId, meeting);
        return ResponseEntity.ok("Meeting booked successfully");
    }

    @GetMapping("/free-slots/{ownerId1}/{ownerId2}/{durationInMinutes}")
    public ResponseEntity<List<LocalDateTime>> findFreeSlots(
            @PathVariable String ownerId1,
            @PathVariable String ownerId2,
            @PathVariable int durationInMinutes) {
        List<LocalDateTime> freeSlots = calendarService.findFreeSlots(ownerId1, ownerId2, durationInMinutes);
        return ResponseEntity.ok(freeSlots);
    }

    @PostMapping("/meeting-conflicts")
    public ResponseEntity<List<String>> findMeetingConflicts(@RequestBody Meeting meeting) {
        List<String> conflictedOwners = calendarService.findMeetingConflicts(meeting);
        return ResponseEntity.ok(conflictedOwners);
    }




}
