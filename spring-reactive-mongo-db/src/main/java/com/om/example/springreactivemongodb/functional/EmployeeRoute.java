package com.om.example.springreactivemongodb.functional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class EmployeeRoute {


    @Bean
    RouterFunction<ServerResponse> employeeRouter(@Autowired EmployeeHandler handler) {
        return route()
                .path("/api/v1/employeeRoute", builder -> builder
                        .GET("/{id}", accept(APPLICATION_JSON), handler::findById)
                        .GET("", accept(APPLICATION_JSON), handler::findAll)
                        .POST("", handler::save)
                        .PUT("/{id}", handler::update)
                        .DELETE("/{id}", handler::delete))
                .build();
    }
}
