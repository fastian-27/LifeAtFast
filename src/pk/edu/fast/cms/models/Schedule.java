package pk.edu.fast.cms.models;

import pk.edu.fast.cms.utils.Day;
import java.time.LocalTime;

/**
 * Represents the timing and location for a specific section.
 * Encapsulates overlap detection logic.
 */
public class Schedule {
    private Day day;
    private LocalTime startTime;
    private LocalTime endTime;
    private String room;

    /**
     * Constructor for Schedule.
     */
    public Schedule(Day day, LocalTime startTime, LocalTime endTime, String room) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time cannot be after end time.");
        }
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
    }

    /**
     * Checks if this schedule overlaps with another schedule in time or room.
     *
     * @param other The other Schedule to compare against.
     * @return true if there is a clash, false otherwise.
     */
    public boolean hasClash(Schedule other) {
        if (other == null) return false;

        // No clash if they are on different days
        if (this.day != other.day) {
            return false;
        }

        // Time overlap logic: (Start A < End B) AND (End A > Start B)
        boolean timeOverlap = this.startTime.isBefore(other.endTime) &&
                this.endTime.isAfter(other.startTime);

        // A clash occurs if they overlap in time AND share the same room,
        // OR if a student/instructor is assigned to two overlapping times (handled by caller).
        return timeOverlap;
    }

    /**
     * Formats the schedule into a readable string.
     */
    public String getScheduleInfo() {
        return String.format("%s from %s to %s in Room %s",
                day.name(), startTime.toString(), endTime.toString(), room);
    }

    // --- Getters and Setters (Strict Encapsulation) ---
    public Day getDay() { return day; }
    public void setDay(Day day) { this.day = day; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
}