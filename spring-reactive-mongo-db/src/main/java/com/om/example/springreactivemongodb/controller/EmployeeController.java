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

@Slf4j
@RestController
public class EmployeeController implements EmployeeEndpoint {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public Flux<Employee> findAll() {
        log.debug("findAll Employee");
        return employeeService.findAll().switchIfEmpty(Mono.empty());
    }


    @Override
    public Mono<Employee> findOne(@PathVariable String id) {
        log.info("findOne Employee with id : {}", id);
        return employeeService.findById(id);
    }

    @Override
    public Mono<Employee> create(@RequestBody Employee Employee) {
        log.debug("create Employee with Employee : {}", Employee);
        return employeeService.save(Employee);
    }

    @Override
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        log.info("delete Employee with id : {}", id);
        return employeeService.delete(id);
    }

    @Override
    public Mono<ResponseEntity<Employee>> updateEmployee(@RequestBody Employee employee) {
        return employeeService.update(employee);
    }
}