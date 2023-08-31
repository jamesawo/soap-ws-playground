/*
 * @Author: james.junior
 * @Date: 8/31/23 12:08
 *
 * @Project: course-ws
 */

package com.jamesaworo.coursews.soap;

import com.jamesaworo.courses.*;
import com.jamesaworo.coursews.exception.CourseNotFoundException;
import com.jamesaworo.coursews.model.Course;
import com.jamesaworo.coursews.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class CourseDetailsEndpoint {
    public static final String NAME_SPACE = "http://jamesaworo.com/courses";

    @Autowired
    CourseService service;

    @PayloadRoot(namespace = NAME_SPACE, localPart = "GetCourseDetailsRequest")
    @ResponsePayload
    public GetCourseDetailsResponse getCourseDetailsResponse(
            @RequestPayload GetCourseDetailsRequest request
    ) {
        GetCourseDetailsResponse response = new GetCourseDetailsResponse();
        Course course = service.findById(request.getId());
        response.setCourseDetails(mapCourse(course));
        return response;
    }

    @PayloadRoot(namespace = NAME_SPACE, localPart = "GetAllCourseDetailsRequest")
    @ResponsePayload
    public GetAllCourseDetailsResponse getAllCourseDetailsResponse(
            @RequestPayload GetAllCourseDetailsRequest request
    ) {
        GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
        List<Course> courses = service.findAll();
        courses.forEach(course ->  response.getCourseDetails().add(mapCourse(course)));
        return response;
    }

    @PayloadRoot(namespace = NAME_SPACE, localPart = "DeleteCourseDetailsRequest")
    @ResponsePayload
    public DeleteCourseDetailsResponse deleteCourseDetailsResponse(@RequestPayload DeleteCourseDetailsRequest request) {
        CourseService.Status status = service.deleteById(request.getId());
        DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
        response.setStatus(com.jamesaworo.courses.Status.valueOf(status.name()));
        return response;
    }


    private CourseDetails mapCourse(Course course) {
        if (course == null){
            throw new CourseNotFoundException("Course does not exist");
        }

        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setId(course.getId());
        courseDetails.setName(course.getName());
        courseDetails.setDescription(course.getDescription());
        return courseDetails;
    }
}
