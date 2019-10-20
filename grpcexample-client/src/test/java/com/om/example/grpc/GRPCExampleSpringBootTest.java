package com.om.example.grpc;

import static org.assertj.core.api.Assertions.assertThat;

import com.om.example.grpc.service.client.EmployeeServiceGrpcClient;
import com.om.example.grpc.struct.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class GRPCExampleSpringBootTest {

    @Autowired
    private EmployeeServiceGrpcClient client;

    @Test
    public void test1() {
        Employee e = client.getEmployeeDetailsByPhone("23145456789");
        System.out.println(e);
        assert(true);
    }
    @Test
    public void test2() {
        long millis = System.currentTimeMillis();
        Employee e = new EmployeeServiceGrpcClient().getEmployeeDetailsByPhone("1234567890");
        long millis1 = System.currentTimeMillis();
        long total = millis1-millis;
        System.out.println(total);
        System.out.println(e);
        assert(true);
    }
}
