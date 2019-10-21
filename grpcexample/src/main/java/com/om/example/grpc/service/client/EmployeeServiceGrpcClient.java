package com.om.example.grpc.service.client;

import com.om.example.grpc.struct.Employee;
import com.om.example.grpc.struct.EmployeeServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EmployeeServiceGrpcClient {


    public Employee createEmployee(Employee employee) {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 1020).usePlaintext().build();
        EmployeeServiceGrpc.EmployeeServiceFutureStub employeeServiceFutureStub = EmployeeServiceGrpc.newFutureStub(managedChannel);
        try {
            employeeServiceFutureStub.createEmployee(employee).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return Employee.newBuilder().build();
    }

    public List<Employee> getAllEmployees(Employee employee) {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 1020).usePlaintext().build();
        EmployeeServiceGrpc.EmployeeServiceFutureStub employeeServiceFutureStub = EmployeeServiceGrpc.newFutureStub(managedChannel);
        try {
            employeeServiceFutureStub.getAllEmployees(employee).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
