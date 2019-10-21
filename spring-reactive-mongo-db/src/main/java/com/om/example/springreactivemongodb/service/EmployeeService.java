package com.om.example.springreactivemongodb.service;

import com.om.example.springreactivemongodb.model.Employee;
import com.om.example.springreactivemongodb.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;

    public Mono<Employee> findById(String id) {
        return repository.findById(id);
    }


    public Flux<Employee> findAll() {
        return repository.findAll();
    }

    public Mono<Employee> save(final Employee employee) {
        return repository.save(employee);
    }

    public Mono<ResponseEntity<Employee>> update(final Employee employee) {
        try {
            return repository.findById(employee.getEmployeeId())
                    .flatMap(existingEmployee -> {
                        existingEmployee.setEmployeeName(employee.getEmployeeName());
                        existingEmployee.setAge(employee.getAge());
                        return repository.save(existingEmployee);
                    })
                    .map(updateEmployee -> new ResponseEntity<>(updateEmployee, HttpStatus.OK))
                    .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (final Exception ex) {
            log.error("Exception while updating Employee", ex);
        }


        return Mono.empty();
    }

    public Mono<ResponseEntity<Void>> delete(String id) {
        return repository.findById(id)
                .flatMap(existingEmployee ->
                        repository.delete(existingEmployee)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
