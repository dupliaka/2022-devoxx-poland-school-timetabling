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

package org.acme.schooltimetabling.bootstrap;

import io.quarkus.runtime.StartupEvent;
import org.acme.schooltimetabling.domain.Lesson;
import org.acme.schooltimetabling.domain.Room;
import org.acme.schooltimetabling.domain.Timeslot;
import org.acme.schooltimetabling.persistence.LessonRepository;
import org.acme.schooltimetabling.persistence.RoomRepository;
import org.acme.schooltimetabling.persistence.TimeslotRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DemoDataGenerator {

    @ConfigProperty(name = "timeTable.demoData", defaultValue = "SMALL")
    DemoData demoData;

    @Inject
    TimeslotRepository timeslotRepository;
    @Inject
    RoomRepository roomRepository;
    @Inject
    LessonRepository lessonRepository;

    @Transactional
    public void generateDemoData(@Observes StartupEvent startupEvent) {
        if (demoData == DemoData.NONE) {
            return;
        }

        List<Lesson> lessons = lessonList.subList(0, demoData.size);
        lessonRepository.persist(lessons);

        timeslotRepository.persist(timeslotList);

        List<Room> roomList = new ArrayList<>();
        for (int i = 65; roomList.size() < 2 * demoData.size / timeslotList.size(); i++) {
            roomList.add(new Room("Room " + (char) i));
        }
        roomRepository.persist(roomList);
    }

   private static List<Lesson> lessonList = List.of(
            new Lesson("Math", "A. Turing", "9th grade"),
            new Lesson("History", "I. Jones", "10th grade"),
            new Lesson("Chemistry", "M. Curie", "9th grade"),
            new Lesson("French", "M. Curie", "10th grade"),
            new Lesson("Math", "A. Turing", "9th grade"),
            new Lesson("Physics", "M. Curie", "9th grade"),
            new Lesson("Biology", "C. Darwin", "9th grade"),
            new Lesson("History", "I. Jones", "9th grade"),
            new Lesson("English", "I. Jones", "9th grade"),
            new Lesson("English", "I. Jones", "9th grade"),
            new Lesson("Spanish", "P. Cruz", "9th grade"),
            new Lesson("Spanish", "P. Cruz", "9th grade"),
            new Lesson("Math", "A. Turing", "9th grade"),
            new Lesson("Math", "A. Turing", "9th grade"),
            new Lesson("Math", "A. Turing", "9th grade"),
            new Lesson("ICT", "A. Turing", "9th grade"),
            new Lesson("Physics", "M. Curie", "9th grade"),
            new Lesson("Geography", "C. Darwin", "9th grade"),
            new Lesson("Geology", "C. Darwin", "9th grade"),
            new Lesson("History", "I. Jones", "9th grade"),
            new Lesson("English", "I. Jones", "9th grade"),
            new Lesson("Drama", "I. Jones", "9th grade"),
            new Lesson("Art", "S. Dali", "9th grade"),
            new Lesson("Art", "S. Dali", "9th grade"),
            new Lesson("Physical education", "C. Lewis", "9th grade"),
            new Lesson("Physical education", "C. Lewis", "9th grade"),
            new Lesson("Physical education", "C. Lewis", "9th grade"),
            new Lesson("Math", "A. Turing", "10th grade"),
            new Lesson("Math", "A. Turing", "10th grade"),
            new Lesson("Math", "A. Turing", "10th grade"),
            new Lesson("Physics", "M. Curie", "10th grade"),
            new Lesson("Chemistry", "M. Curie", "10th grade"),
            new Lesson("Geography", "C. Darwin", "10th grade"),
            new Lesson("History", "I. Jones", "10th grade"),
            new Lesson("English", "P. Cruz", "10th grade"),
            new Lesson("Spanish", "P. Cruz", "10th grade"),
            new Lesson("Math", "A. Turing", "10th grade"),
            new Lesson("Math", "A. Turing", "10th grade"),
            new Lesson("ICT", "A. Turing", "10th grade"),
            new Lesson("Physics", "M. Curie", "10th grade"),
            new Lesson("Biology", "C. Darwin", "10th grade"),
            new Lesson("Geology", "C. Darwin", "10th grade"),
            new Lesson("English", "P. Cruz", "10th grade"),
            new Lesson("English", "P. Cruz", "10th grade"),
            new Lesson("Drama", "I. Jones", "10th grade"),
            new Lesson("Art", "S. Dali", "10th grade"),
            new Lesson("Art", "S. Dali", "10th grade"),
            new Lesson("Physical education", "C. Lewis", "10th grade"),
            new Lesson("Physical education", "C. Lewis", "10th grade"),
            new Lesson("Physical education", "C. Lewis", "10th grade"),
            new Lesson("Math", "A. Turing", "11th grade"),
            new Lesson("Math", "A. Turing", "11th grade"),
            new Lesson("Math", "A. Turing", "11th grade"),
            new Lesson("Math", "A. Turing", "11th grade"),
            new Lesson("Math", "A. Turing", "11th grade"),
            new Lesson("ICT", "A. Turing", "11th grade"),
            new Lesson("Physics", "M. Curie", "11th grade"),
            new Lesson("Chemistry", "M. Curie", "11th grade"),
            new Lesson("French", "M. Curie", "11th grade"),
            new Lesson("Physics", "M. Curie", "11th grade"),
            new Lesson("Geography", "C. Darwin", "11th grade"),
            new Lesson("Biology", "C. Darwin", "11th grade"),
            new Lesson("Geology", "C. Darwin", "11th grade"),
            new Lesson("History", "I. Jones", "11th grade"),
            new Lesson("History", "I. Jones", "11th grade"),
            new Lesson("English", "P. Cruz", "11th grade"),
            new Lesson("English", "P. Cruz", "11th grade"),
            new Lesson("English", "P. Cruz", "11th grade"),
            new Lesson("Spanish", "P. Cruz", "11th grade"),
            new Lesson("Drama", "P. Cruz", "11th grade"),
            new Lesson("Art", "S. Dali", "11th grade"),
            new Lesson("Art", "S. Dali", "11th grade"),
            new Lesson("Physical education", "C. Lewis", "11th grade"),
            new Lesson("Physical education", "C. Lewis", "11th grade"),
            new Lesson("Physical education", "C. Lewis", "11th grade"),
            new Lesson("Math", "A. Turing", "12th grade"),
            new Lesson("Math", "A. Turing", "12th grade"),
            new Lesson("Math", "A. Turing", "12th grade"),
            new Lesson("Math", "A. Turing", "12th grade"),
            new Lesson("Math", "A. Turing", "12th grade"),
            new Lesson("ICT", "A. Turing", "12th grade"),
            new Lesson("Physics", "M. Curie", "12th grade"),
            new Lesson("Chemistry", "M. Curie", "12th grade"),
            new Lesson("French", "M. Curie", "12th grade"),
            new Lesson("Physics", "M. Curie", "12th grade"),
            new Lesson("Geography", "C. Darwin", "12th grade"),
            new Lesson("Biology", "C. Darwin", "12th grade"),
            new Lesson("Geology", "C. Darwin", "12th grade"),
            new Lesson("History", "I. Jones", "12th grade"),
            new Lesson("History", "I. Jones", "12th grade"),
            new Lesson("English", "P. Cruz", "12th grade"),
            new Lesson("English", "P. Cruz", "12th grade"),
            new Lesson("English", "P. Cruz", "12th grade"),
            new Lesson("Spanish", "P. Cruz", "12th grade"),
            new Lesson("Drama", "P. Cruz", "12th grade"),
            new Lesson("Art", "S. Dali", "12th grade"),
            new Lesson("Art", "S. Dali", "12th grade"),
            new Lesson("Physical education", "C. Lewis", "12th grade"),
            new Lesson("Physical education", "C. Lewis", "12th grade"),
            new Lesson("Physical education", "C. Lewis", "12th grade"));

    private static List<Timeslot> timeslotList = List.of(
        new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)),
        new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)),
        new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)),
        new Timeslot(DayOfWeek.MONDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)),
        new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)),
        new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)),
        new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)),
        new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)),
        new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)),
        new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)),
        new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)),
        new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)),
        new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)),
        new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)),
        new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)),
        new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)),
        new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)),
        new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)),
        new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)),
        new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)),
        new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)),
        new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)),
        new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)),
        new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)),
        new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));

    public enum DemoData {
        NONE(0), SMALL(30), LARGE(100);

        int size;

        DemoData(int i) {
            this.size = i;
        }
    }

}
