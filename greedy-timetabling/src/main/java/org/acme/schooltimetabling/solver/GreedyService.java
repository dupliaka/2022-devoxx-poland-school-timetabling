package org.acme.schooltimetabling.solver;

import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;

import org.acme.schooltimetabling.domain.Lesson;
import org.acme.schooltimetabling.domain.Room;
import org.acme.schooltimetabling.domain.TimeTable;
import org.acme.schooltimetabling.domain.Timeslot;
@ApplicationScoped
public class GreedyService {
    public TimeTable solve(TimeTable timeTable) {
        for (Lesson lesson : timeTable.getLessonList()) {
            int bestScore = Integer.MIN_VALUE;
            Room bestRoom = null;
            Timeslot bestTimeslot = null;
            for (Room room : timeTable.getRoomList()) {
                for (Timeslot timeslot : timeTable.getTimeslotList()) {
                    int score = calculateScore(timeTable, timeslot, room, lesson.getStudentGroup(), lesson.getTeacher());
                    if (score > bestScore) {
                        bestScore = score;
                        bestRoom = room;
                        bestTimeslot = timeslot;
                    }
                }
            }
            lesson.setTimeslot(bestTimeslot);
            lesson.setRoom(bestRoom);
            timeTable.setScore(timeTable.getScore() + bestScore);
            timeTable.setSolved();
        }
        return timeTable;
    }

    private int calculateScore(TimeTable unsolved, Timeslot timeslot, Room room, String studentGroup, String teacher) {
        for (Lesson l : unsolved.getLessonList()) {
            if (l.getRoom() != null
                    && l.getTimeslot() != null
                    && Objects.equals(l.getRoom().getId(), room.getId())
                    && Objects.equals(l.getTimeslot().getId(), timeslot.getId())
            ) {
                return -1;
            }
            if (l.getStudentGroup() != null
                    && l.getTimeslot() != null
                    && Objects.equals(l.getTimeslot().getId(), timeslot.getId())
                    && Objects.equals(l.getStudentGroup(),studentGroup)
            ) {
                return -1;
            }
            if (l.getTeacher() != null
                    && l.getTimeslot() != null
                    && Objects.equals(l.getTimeslot().getId(), timeslot.getId())
                    && Objects.equals(l.getTeacher(),teacher)
            ) {
                return -1;
            }
        }
        return 0;
    }
}
