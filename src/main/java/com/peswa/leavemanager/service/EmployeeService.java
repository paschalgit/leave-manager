package com.peswa.leavemanager.service;

import com.peswa.leavemanager.dto.request.EmployeeRequest;
import com.peswa.leavemanager.dto.response.EmployeeResponse;
import com.peswa.leavemanager.model.Employee;

import java.util.List;

public interface EmployeeService {

    // Employee createEmployee(Employee employee);

    EmployeeResponse addEmployee(EmployeeRequest employeeRequest, String email, String username, String password);

    List<Employee> getAllEmployees();

    boolean existsByOfficialEmail(String email);

    List<EmployeeResponse> getListOfEmployees();

    EmployeeResponse getEmployeeByUserId(long userid);
}
