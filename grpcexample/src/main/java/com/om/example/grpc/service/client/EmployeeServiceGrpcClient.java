package com.om.example.grpc.service.client;

import com.om.example.grpc.service.EmployeeServiceGrpc;
import com.om.example.grpc.struct.Employee;
import com.om.example.grpc.struct.EmployeeRequestByPhone;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;

@Component
public class EmployeeServiceGrpcClient {
    private EmployeeServiceGrpc.EmployeeServiceFutureStub employeeServiceFutureStub;

    @Value("${grpc.port}")
    private int port;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", port).usePlaintext().build();

        employeeServiceFutureStub =
                EmployeeServiceGrpc.newFutureStub(managedChannel);
    }

    public Employee getEmployeeDetailsByPhone(String phoneNumber) {
        EmployeeRequestByPhone req = EmployeeRequestByPhone.newBuilder().setPhone(phoneNumber).build();
        com.google.common.util.concurrent.ListenableFuture<com.om.example.grpc.struct.Employee> result = employeeServiceFutureStub.getEmployeeDetailsByPhone(req);
        try {
            return result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return Employee.newBuilder().build();
    }
}
