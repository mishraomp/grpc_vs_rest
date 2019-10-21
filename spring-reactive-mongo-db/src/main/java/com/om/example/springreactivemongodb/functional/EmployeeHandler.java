package com.om.example.springreactivemongodb.functional;

import com.om.example.springreactivemongodb.model.Employee;
import com.om.example.springreactivemongodb.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
public class EmployeeHandler {
    @Autowired
    private EmployeeRepository repository;

    public Mono<ServerResponse> findById(final ServerRequest request) {
        val id = request.pathVariable("id");
        return ok().contentType(APPLICATION_JSON).body(repository.findById(id), Employee.class);
    }


    public Mono<ServerResponse> findAll(final ServerRequest request) {
        return ok().contentType(APPLICATION_JSON).body(repository.findAll(), Employee.class);
    }

    public Mono<ServerResponse> save(final ServerRequest request) {
        val employee = request.bodyToMono(Employee.class).toProcessor().peek();
        return ok().contentType(APPLICATION_JSON).body(repository.save(employee), Employee.class);
    }

    public Mono<ServerResponse> update(final ServerRequest request) {
        val employee = request.bodyToMono(Employee.class).toProcessor().peek();
        if (employee != null) {
            try {
                return repository.findById(employee.getEmployeeId())
                        .flatMap(existingEmployee -> {
                            existingEmployee.setEmployeeName(employee.getEmployeeName());
                            existingEmployee.setAge(employee.getAge());
                            return ok().contentType(APPLICATION_JSON).body(repository.save(existingEmployee), Employee.class);
                        }).switchIfEmpty(ServerResponse.notFound().build());
            } catch (final Exception ex) {
                log.error("Exception while updating Employee", ex);
            }
        }

        return ServerResponse.notFound().build();
    }

    public Mono<ServerResponse> delete(final ServerRequest request) {
        val id = request.pathVariable("id");
        return repository.findById(id)
                .flatMap(existingEmployee ->
                        repository.delete(existingEmployee)
                                .then(ServerResponse.ok().build())
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
