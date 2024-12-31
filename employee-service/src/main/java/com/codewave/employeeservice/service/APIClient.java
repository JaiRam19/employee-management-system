package com.codewave.employeeservice.service;

import com.codewave.employeeservice.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//this is client for department service and it looks for below base url to get the department details
//for the given department code
//spring cloud automatically provides the implementation of the below getDepartmentByCode method in cloud
//@FeignClient(url = "http://localhost:8080", value = "DEPARTMENT-SERVICE")//name of the client

//instead of using single instance("http://localhost:8080") which is running on eureka sever
// to fetch department details, we can use service id of all instances of department service that are running
//on eureka server so, the employee service pick the department details from the instance which is available / if other
//instances are down
@FeignClient(name = "DEPARTMENT-SERVICE")//name of the client
public interface APIClient {

    @GetMapping("/api/departments/{department-code}")
    public DepartmentDto getDepartmentByCode(@PathVariable("department-code") String departmentCode);
}
