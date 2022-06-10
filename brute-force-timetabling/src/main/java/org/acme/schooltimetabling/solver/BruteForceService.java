package org.acme.schooltimetabling.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

import javax.enterprise.context.ApplicationScoped;

import org.acme.schooltimetabling.domain.Lesson;
import org.acme.schooltimetabling.domain.TimeTable;

@ApplicationScoped
public class BruteForceService {

    private static void swap(int[] elements, int a, int b) {

        int tmp = elements[a];
        elements[a] = elements[b];
        elements[b] = tmp;
    }

    public TimeTable solve(TimeTable timeTable) {

        ArrayList<int[]> permutation = getPermutation(timeTable.getRoomList().size() + timeTable.getTimeslotList().size());

        TimeTable bestTimeTable = new TimeTable();
        int bestScore = Integer.MIN_VALUE;
        for (int[] per : permutation) {
            int l = 0;
            for (int r = 0; r < timeTable.getRoomList().size(); r++) {
                for (int t = 0; t < timeTable.getTimeslotList().size(); t++) {
                    timeTable.getLessonList().get(per[l]).setRoom(timeTable.getRoomList().get(r));
                    timeTable.getLessonList().get(per[l]).setTimeslot(timeTable.getTimeslotList().get(t));
                    l++;
                }
            }
            int score = calculateScore(timeTable);
            printLessons(timeTable,score);
            if (score > bestScore) {
                bestScore = score;
                bestTimeTable = timeTable;
            }
        }

        bestTimeTable.setScore(bestScore);
        return bestTimeTable;
    }

    private ArrayList<int[]> getPermutation(int size) {
        ArrayList<int[]> permutation = new ArrayList<>();

        int[] elements = IntStream.rangeClosed(0, size-1).toArray();
        int[] indexes = new int[elements.length];

        int i = 0;
        while (i < elements.length) {
            if (indexes[i] < i) {
                swap(elements, i % 2 == 0 ? 0 : indexes[i], i);
                permutation.add(Arrays.copyOf(elements, size));
                indexes[i]++;
                i = 0;
            } else {
                indexes[i] = 0;
                i++;
            }
        }
        return permutation;
    }

    private void printLessons(TimeTable timeTable, int score) {
        System.out.println();
        for (Lesson lesson:timeTable.getLessonList()) {
            System.out.println(lesson.print());
        }
        System.out.println("score:"+ score);
    }

    private int calculateScore(TimeTable timeTable) {
        int hardScore = 0;
        for (Lesson lesson1 : timeTable.getLessonList()) {
            for (Lesson lesson2 : timeTable.getLessonList()) {
                if (lesson1 != lesson2) {
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
        }
        return hardScore;
    }
}
