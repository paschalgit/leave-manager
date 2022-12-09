package com.peswa.leavemanager.controller;

import com.peswa.leavemanager.dto.request.EmployeeRequest;
import com.peswa.leavemanager.dto.request.RoleRequest;
import com.peswa.leavemanager.dto.response.BaseResponse;
import com.peswa.leavemanager.dto.response.EmployeeResponse;
import com.peswa.leavemanager.dto.response.UserResponse;
import com.peswa.leavemanager.enums.RoleEnum;
import com.peswa.leavemanager.model.Employee;
import com.peswa.leavemanager.repository.EmployeeRepository;
import com.peswa.leavemanager.service.EmployeeService;
import com.peswa.leavemanager.service.SequenceGeneratorService;
import com.peswa.leavemanager.service.UserService;
import com.peswa.leavemanager.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {



        @Autowired
        EmployeeService employeeService;

        @Autowired
        UserService userService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeRequest request) {
        String message = "";
        if (request!=null){
            String firstName = request.getFirstName();
            String lastName = request.getLastName();
            String email = firstName.toLowerCase(Locale.ROOT)+"."+lastName.toLowerCase()+"@peswa.finance";
            String username = firstName.toLowerCase().substring(0,1)+lastName.toLowerCase();
            String password = AppUtil.generateRandomString(10);//Used only once for activation of user.
            System.out.println("Generated username "+username);
            System.out.println("Generated password "+password);

            if (employeeService.existsByOfficialEmail(email)) {
                message = "Error: Employee email already taken!";
                return ResponseEntity
                        .badRequest()
                        .body(new BaseResponse<>(false, message, null));
            }

            if (userService.existsByUsername(username)){
                message = "Error: Username is already taken!";
                return ResponseEntity
                        .badRequest()
                        .body(new BaseResponse<>(false, message, null));
            }

            EmployeeResponse employeeResponse = employeeService.addEmployee(request,email,username,password);
            if (employeeResponse != null) {
                return ResponseEntity.ok(new BaseResponse<>(true, "Employee created successfully. User details has been sent to his personal email address ",
                        employeeResponse));
            } else {
                message = "Failed to create employee:";
            }
        }
        message = "Failed to create employee:";
        return ResponseEntity
                .badRequest()
                .body(new BaseResponse<>(false, message, null));

    }



    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<BaseResponse<List<EmployeeResponse>>> getEmployees() {

        List<EmployeeResponse> employeeResponses = employeeService.getListOfEmployees();
        if(employeeResponses != null){
            return ResponseEntity.ok(new BaseResponse(true, "Employees retrieved successfully", employeeResponses));
        } else {
            return new ResponseEntity<>(new BaseResponse(false, "Employees  not found.", null),
                    HttpStatus.OK);
        }
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("getEmployeeByUserId/{userId}")
    public ResponseEntity<?> getEmployeeByUserId(@PathVariable("userId") Long userId) {
        EmployeeResponse employeeResponse = employeeService.getEmployeeByUserId(userId);
        if(employeeResponse != null){
            return ResponseEntity.ok(new BaseResponse(true, "Employee retrieved successfully", employeeResponse));
        } else {
            return new ResponseEntity<>(new BaseResponse(false, "Employee not found.", null),
                    HttpStatus.OK);
        }
    }


 /*   @GetMapping("/")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
}