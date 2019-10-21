package com.om.example.grpc.service;

import com.om.example.grpc.repository.EmployeeRepository;
import com.om.example.grpc.struct.AllEmployeesResponse;
import com.om.example.grpc.struct.Employee;
import com.om.example.grpc.struct.EmployeeResponse;
import com.om.example.grpc.struct.EmployeeServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@GRpcService
@Slf4j
public class EmployeeServiceGRPCImpl extends EmployeeServiceGrpc.EmployeeServiceImplBase {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public void getAllEmployees(com.om.example.grpc.struct.Employee request,
                                io.grpc.stub.StreamObserver<com.om.example.grpc.struct.AllEmployeesResponse> responseObserver) {
        log.info("Entered getAllEmployees");
        final AllEmployeesResponse.Builder allEmployees = AllEmployeesResponse.newBuilder();

        List<com.om.example.grpc.model.Employee> employeeList = repository.findAll();

        for (com.om.example.grpc.model.Employee employee : employeeList) {
            Employee employeeStruct = Employee.newBuilder().setEmployeeId(employee.getEmployeeId()).setEmployeeName(employee.getEmployeeName()).setAge(employee.getAge()).build();
            allEmployees.addItems(employeeStruct);
        }
        allEmployees.setStatus(200).setMessage("Returned All Employees.");
        responseObserver.onNext(allEmployees.build());
        responseObserver.onCompleted();
    }


    /**
     *
     */
    @Override
    public void getEmployeeById(com.om.example.grpc.struct.Employee request,
                                io.grpc.stub.StreamObserver<com.om.example.grpc.struct.EmployeeResponse> responseObserver) {
        final EmployeeResponse.Builder employeeResponseBuilder = EmployeeResponse.newBuilder();
        Optional<com.om.example.grpc.model.Employee> employee = repository.findById(request.getEmployeeId());
        if (employee.isPresent()) {
            employeeResponseBuilder.setStatus(200).setMessage("Employee Created").setEmployee(com.om.example.grpc.struct.Employee.newBuilder().setEmployeeId(employee.get().getEmployeeId()).setEmployeeName(employee.get().getEmployeeName()).setAge(employee.get().getAge()).build());
            responseObserver.onNext(employeeResponseBuilder.build());
            responseObserver.onCompleted();
        } else {
            employeeResponseBuilder.setStatus(404).setMessage("Employee Not Found").build();
            responseObserver.onNext(employeeResponseBuilder.build());
            responseObserver.onCompleted();
        }
    }

    /**
     *
     */
    @Override
    public void createEmployee(com.om.example.grpc.struct.Employee request,
                               io.grpc.stub.StreamObserver<com.om.example.grpc.struct.EmployeeResponse> responseObserver) {
        final EmployeeResponse.Builder employeeResponseBuilder = EmployeeResponse.newBuilder();
        com.om.example.grpc.model.Employee employeeModel = com.om.example.grpc.model.Employee.builder().employeeId(request.getEmployeeId()).employeeName(request.getEmployeeName()).age(request.getAge()).build();
        repository.save(employeeModel);
        employeeResponseBuilder.setStatus(200).setMessage("Employee Created").setEmployee(com.om.example.grpc.struct.Employee.newBuilder().setEmployeeId(employeeModel.getEmployeeId()).setEmployeeName(employeeModel.getEmployeeName()).setAge(employeeModel.getAge()).build());
        responseObserver.onNext(employeeResponseBuilder.build());
        responseObserver.onCompleted();
    }

    /**
     *
     */
    @Override
    public void updateEmployee(com.om.example.grpc.struct.Employee request,
                               io.grpc.stub.StreamObserver<com.om.example.grpc.struct.EmployeeResponse> responseObserver) {
        final EmployeeResponse.Builder employeeResponseBuilder = EmployeeResponse.newBuilder();

        Optional<com.om.example.grpc.model.Employee> employee = repository.findById(request.getEmployeeId());
        if (employee.isPresent()) {
            com.om.example.grpc.model.Employee employeeModel = employee.get();
            employeeModel.setEmployeeName(request.getEmployeeName());
            employeeModel.setAge(request.getAge());
            employeeModel = repository.save(employeeModel);
            employeeResponseBuilder.setStatus(200).setMessage("Employee Updated").setEmployee(com.om.example.grpc.struct.Employee.newBuilder().setEmployeeId(employeeModel.getEmployeeId()).setEmployeeName(employeeModel.getEmployeeName()).setAge(employeeModel.getAge()).build());
            responseObserver.onNext(employeeResponseBuilder.build());
            responseObserver.onCompleted();
        } else {
            employeeResponseBuilder.setStatus(404).setMessage("Employee Not Found").build();
            responseObserver.onNext(employeeResponseBuilder.build());
            responseObserver.onCompleted();
        }
    }

    /**
     *
     */
    @Override
    public void deleteEmployee(com.om.example.grpc.struct.Employee request,
                               io.grpc.stub.StreamObserver<com.om.example.grpc.struct.EmployeeResponse> responseObserver) {
        final EmployeeResponse.Builder employeeResponseBuilder = EmployeeResponse.newBuilder();

        Optional<com.om.example.grpc.model.Employee> employee = repository.findById(request.getEmployeeId());
        if (employee.isPresent()) {
            repository.deleteById(request.getEmployeeId());
            employeeResponseBuilder.setStatus(200).setMessage("Employee removed").build();
            responseObserver.onNext(employeeResponseBuilder.build());
            responseObserver.onCompleted();
        } else {
            employeeResponseBuilder.setStatus(404).setMessage("Employee Not Found").build();
            responseObserver.onNext(employeeResponseBuilder.build());
            responseObserver.onCompleted();
        }
    }
}
