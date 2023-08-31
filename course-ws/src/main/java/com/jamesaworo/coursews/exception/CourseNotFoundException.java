/*
 * @Author: james.junior
 * @Date: 8/31/23 14:56
 *
 * @Project: course-ws
 */

package com.jamesaworo.coursews.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT)
public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String message) {
        super(message);
    }
}
