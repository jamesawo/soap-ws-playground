/*
 * @Author: james.junior
 * @Date: 8/31/23 12:29
 *
 * @Project: course-ws
 */

package com.jamesaworo.coursews.configuration;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@EnableWs
@Configuration
public class WsConfiguration extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> registrationBean(ApplicationContext context){
        MessageDispatcherServlet dispatcherServlet = new MessageDispatcherServlet();
        dispatcherServlet.setApplicationContext(context);
        dispatcherServlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(dispatcherServlet, "/ws/*");
    }

    @Bean
    public XsdSchema courseSchema(){
        return new SimpleXsdSchema(new ClassPathResource("course-details.xsd"));
    }

    @Bean(name = "courses")
    public DefaultWsdl11Definition wsdl11Definition(XsdSchema schema){
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("CoursePort");
        definition.setTargetNamespace("http://jamesaworo.com/courses");
        definition.setLocationUri("/ws");
        definition.setSchema(schema);
        return definition;
    }

    @Bean
    public XwsSecurityInterceptor securityInterceptor(){
        XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
        securityInterceptor.setCallbackHandler(validationCallbackHandler());
        securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
        return securityInterceptor;
    }

    @Bean
    public SimplePasswordValidationCallbackHandler validationCallbackHandler(){
        SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
        handler.setUsersMap(Collections.singletonMap("user", "password"));
        return handler;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        System.out.println("intercepting request at: " + new Date().toInstant().toString());
//        interceptors.add(payloadLoggingInterceptor());
//        interceptors.add(payloadValidatingInterceptor());
//        interceptors.add(securityInterceptor());
    }

    @Bean
    public PayloadLoggingInterceptor payloadLoggingInterceptor() {
        return new PayloadLoggingInterceptor();
    }

    @Bean
    public PayloadValidatingInterceptor payloadValidatingInterceptor() {
        final PayloadValidatingInterceptor payloadValidatingInterceptor = new PayloadValidatingInterceptor();
        payloadValidatingInterceptor.setSchema(new ClassPathResource("customer-service.xsd"));
        return payloadValidatingInterceptor;
    }
}
