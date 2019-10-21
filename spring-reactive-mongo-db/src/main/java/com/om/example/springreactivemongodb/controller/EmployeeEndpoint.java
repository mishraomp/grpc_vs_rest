package com.om.example.springreactivemongodb.controller;

import com.om.example.springreactivemongodb.model.Employee;
import com.om.example.springreactivemongodb.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


@RequestMapping("/api/v1/employee")
public interface EmployeeEndpoint {


    @GetMapping
    Flux<Employee> findAll();


    @GetMapping("/{id}")
    Mono<Employee> findOne(@PathVariable String id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Employee> create(@RequestBody Employee Employee);

    @DeleteMapping("/{id}")
    Mono<ResponseEntity<Void>> delete(@PathVariable String id);

    @PutMapping("/{id}")
    Mono<ResponseEntity<Employee>> updateEmployee(@RequestBody Employee employee);
}
