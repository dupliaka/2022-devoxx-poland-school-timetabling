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
            for (Timeslot timeslot : timeTable.getTimeslotList()) {
                lesson.setTimeslot(timeslot);
                for (Room room : timeTable.getRoomList()) {
                    lesson.setRoom(room);
                    int score = calculateScore(timeTable);
                    if (score > bestScore) {
                        bestScore = score;
                        bestRoom = room;
                        bestTimeslot = timeslot;
                    }
                    lesson.setRoom(null);
                }
                lesson.setTimeslot(null);
            }
            lesson.setTimeslot(bestTimeslot);
            lesson.setRoom(bestRoom);
        }
        timeTable.setScore(calculateScore(timeTable));
        return timeTable;
    }

    private int calculateScore(TimeTable timeTable) {
        int hardScore = 0;
        for (Lesson lesson1 : timeTable.getLessonList()) {
            for (Lesson lesson2 : timeTable.getLessonList()) {
                if (lesson1 == lesson2) {
                    break;
                }
                if (Objects.equals(lesson1.getRoom(), lesson2.getRoom())
                        && Objects.equals(lesson1.getTimeslot(), lesson2.getTimeslot())) {
                    hardScore--;
                }
                if (Objects.equals(lesson1.getTeacher(), lesson2.getTeacher())
                        && Objects.equals(lesson1.getTimeslot(), lesson2.getTimeslot())) {
                    hardScore--;
                }
                if (Objects.equals(lesson1.getStudentGroup(), lesson2.getStudentGroup())
                        && Objects.equals(lesson1.getTimeslot(), lesson2.getTimeslot())) {
                    hardScore--;
                }
            }
        }
        return hardScore;
    }
}
