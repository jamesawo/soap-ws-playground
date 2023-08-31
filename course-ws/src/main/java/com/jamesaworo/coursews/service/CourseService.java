/*
 * @Author: james.junior
 * @Date: 8/31/23 13:09
 *
 * @Project: course-ws
 */

package com.jamesaworo.coursews.service;

import com.jamesaworo.coursews.model.Course;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseService {

    public enum Status {
        SUCCESS,
        FAILURE
    }

    private static List<Course> courses = new ArrayList<>();
    static {
            courses.add(new Course(1, "Java", "Java basics and object oriented programming"));
            courses.add(new Course(2, "C++", "C++ basics and object oriented programming"));
            courses.add(new Course(3, "PHP", "PHP basic course"));
            courses.add(new Course(4, "Python", "Introduction to python programming"));
    }

    public Course findById(int id){
        for (Course course: courses){
            if (course.getId() == id){
                return course;
            }
        }
        return null;
    }

    public Status deleteById(int id){
        boolean removed = courses.removeIf(course -> course.getId() == id);
        return removed ? Status.SUCCESS : Status.FAILURE;
    }

    public List<Course> findAll() {
        return courses;
    }

}
