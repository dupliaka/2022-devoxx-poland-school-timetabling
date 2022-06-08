/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.acme.schooltimetabling.rest;

import io.quarkus.panache.common.Sort;
import org.acme.schooltimetabling.domain.Lesson;
import org.acme.schooltimetabling.domain.Room;
import org.acme.schooltimetabling.domain.TimeTable;
import org.acme.schooltimetabling.domain.Timeslot;
import org.acme.schooltimetabling.persistence.LessonRepository;
import org.acme.schooltimetabling.persistence.RoomRepository;
import org.acme.schooltimetabling.persistence.TimeslotRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Objects;

@Path("timeTable")
public class TimeTableResource {
    @Inject
    TimeslotRepository timeslotRepository;
    @Inject
    RoomRepository roomRepository;
    @Inject
    LessonRepository lessonRepository;

    // To try, open http://localhost:8080/timeTable
    @GET
    public TimeTable getTimeTable() {
        return findById();
    }

    @GET
    @Path("solved")
    public TimeTable getTimeSolvedTable() {

        TimeTable timeTable = findById();

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

    @Transactional
    protected TimeTable findById() {
        return new TimeTable(timeslotRepository.listAll(Sort.by("dayOfWeek").and("startTime").and("endTime").and("id")), roomRepository.listAll(Sort.by("name").and("id")), lessonRepository.listAll(Sort.by("subject").and("teacher").and("studentGroup").and("id")));
    }

    @Transactional
    protected void save(TimeTable timeTable) {
        for (Lesson lesson : timeTable.getLessonList()) {
            Lesson attachedLesson = lessonRepository.findById(lesson.getId());
            attachedLesson.setTimeslot(lesson.getTimeslot());
            attachedLesson.setRoom(lesson.getRoom());
        }
    }

}
