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
            throw new IllegalArgumentException("Start Time cannot be after End time. Kindly beware");
        }
        // otherwise normal scheduling procedure.. in the system...
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
     * Formats the schedule into a readable string. String.format()  to format the string in a readable format.
     * bs combine krna basically...
     */
    public String getScheduleInfo() {
        return String.format("%s from %s to %s in Room %s",
                day.name(), startTime.toString(), endTime.toString(), room);
    }
    public Day getDay() { return day; }
    public void setDay(Day day) { this.day = day; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
}


//public Schedule(String day, LocalTime start, LocalTime end) {
  //  if (!end.isAfter(start)) throw new IllegalArgumentException("End time must be after start time");
   // this.day = day; this.start = start; this.end = end;
//public String getDay() { return day; }
  //  public LocalTime getStart() { return start; }
   // public LocalTime getEnd() { return end; }
    // CALSH...
   // public boolean clashesWith(Schedule other) {
     //   if (other == null || !day.equalsIgnoreCase(other.day)) return false;
       // return start.isBefore(other.end) && other.start.isBefore(end);
    //}
   // @Override public String toString() { return day + " " + start + "-" + end; }

