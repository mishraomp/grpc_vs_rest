package com.om.example.grpc.service;


import com.om.example.grpc.struct.AllEmployees;
import com.om.example.grpc.struct.Employee;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Component
@GRpcService
public class EmployeeServiceGrpcImpl extends EmployeeServiceGrpc.EmployeeServiceImplBase {
    @Override
    public void getEmployeeDetailsByPhone(com.om.example.grpc.struct.EmployeeRequestByPhone request,
                                          io.grpc.stub.StreamObserver<com.om.example.grpc.struct.Employee> responseObserver) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
        if (request.getPhone().equals("1234567890")) {
            Employee employee = Employee.newBuilder().setFirstName("Om").setLastName("Mishra").setAge(32).build();
            responseObserver.onNext(employee);
            responseObserver.onCompleted();
        } else {
            Employee employee = Employee.newBuilder().setFirstName("unknown").setLastName("unknown").setAge(0).build();
            responseObserver.onNext(employee);
            responseObserver.onCompleted();
        }
    }

    @Override
    @Transactional()
    public void getEmployees(com.om.example.grpc.struct.Employee request,
                             io.grpc.stub.StreamObserver<com.om.example.grpc.struct.AllEmployees> responseObserver) {
        if (request.getPhone().equals("1234567890")) {
            Employee employee = Employee.newBuilder().setFirstName("Om").setLastName("Mishra").setAge(32).build();
            AllEmployees allEmployees = AllEmployees.newBuilder().addItems(employee).build();
            responseObserver.onNext(allEmployees);
            responseObserver.onCompleted();
        } else {
            Employee employee = Employee.newBuilder().setFirstName("unknown").setLastName("unknown").setAge(0).build();
            AllEmployees allEmployees = AllEmployees.newBuilder().addItems(employee).build();
            responseObserver.onNext(allEmployees);
            responseObserver.onCompleted();
        }
    }
}
