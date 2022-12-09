package com.peswa.leavemanager.service.impl;

import com.peswa.leavemanager.dto.request.EmployeeRequest;
import com.peswa.leavemanager.dto.request.UserGroupRequest;
import com.peswa.leavemanager.dto.request.UserRequest;
import com.peswa.leavemanager.dto.response.EmployeeResponse;
import com.peswa.leavemanager.dto.response.UserResponse;
import com.peswa.leavemanager.email.EmailSenderService;
import com.peswa.leavemanager.enums.RoleEnum;
import com.peswa.leavemanager.model.Employee;
import com.peswa.leavemanager.model.Role;
import com.peswa.leavemanager.model.User;
import com.peswa.leavemanager.model.UserGroup;
import com.peswa.leavemanager.repository.EmployeeRepository;
import com.peswa.leavemanager.service.EmployeeService;
import com.peswa.leavemanager.service.SequenceGeneratorService;
import com.peswa.leavemanager.service.UserGroupService;
import com.peswa.leavemanager.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    UserService userService;

    @Autowired
    EmailSenderService emailSenderService;


    public EmployeeResponse addEmployee(EmployeeRequest employeeRequest,String officialEmail,String username,String password) {
        if(employeeRequest!=null){
            //create the user first
            UserRequest userRequest = new UserRequest(
                    username,
                    password,
                    RoleEnum.ROLE_USER
            );
            User user = userService.addUserR(userRequest);
            if(user!=null){
                Employee employee = new Employee(
                        sequenceGeneratorService.generateSequence(Employee.SEQUENCE_NAME),
                        employeeRequest.getFirstName(),
                        employeeRequest.getLastName(),
                        employeeRequest.getPersonalEmail(),
                        officialEmail,
                        employeeRequest.getDateOfBirth(),
                        employeeRequest.getEmploymentStartDate(),
                        user
                );
                Employee result =  employeeRepository.save(employee);
                //Send the username and the temporary password to the new employee's personal email
                if(result!=null) {
                    //Send an email notification to the supervisor
                     emailSenderService.sendWelcomeEmail(result.getPersonalEmail(),
                             result.getFirstName(),
                             username,
                             password);
                }
                return  getEmployeeResponse(result,user);
            }else{
                // throw unable to create user exception
            }
        }
        return null;
    }

    public boolean existsByOfficialEmail(String email){
        return employeeRepository.existsByOfficialEmail(email);
    }


    public List<EmployeeResponse> getListOfEmployees(){
        List<Employee> employees = new ArrayList<Employee>();
        employeeRepository.findAll().forEach(employees::add);
        List<EmployeeResponse> responses = new ArrayList<>();
            for(Employee employee:employees){
                responses.add(getEmployeeResponse(employee,employee.getUser()));
            }
            return responses;
    }

    public EmployeeResponse getEmployeeByUserId(long userid){
        User user = userService.getUserById(userid);
        Employee employee = employeeRepository.findByUser(user);
        return getEmployeeResponse(employee,user);
    }



    public EmployeeResponse getEmployeeResponse(Employee result,User user){
        if(result != null) {
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(user, userResponse);
            userResponse.setRoles(getRole(user.getRoles()));
            return new EmployeeResponse(
                    result.getId(),
                    result.getFirstName(),
                    result.getLastName(),
                    result.getPersonalEmail(),
                    result.getOfficialEmail(),
                    result.getDateOfBirth(),
                    result.getEmploymentStartDate(),
                    result.getEmploymentEndDate(),
                    userResponse
            );
        }
        return null;
    }
    public List<Employee> getAllEmployees(){
        List<Employee> employees = new ArrayList<Employee>();
        employeeRepository.findAll().forEach(employees::add);
        return employees;
    }

    private String[] getRole(Collection<Role> role){
        String[] roleName = new String[role.size()];
        List<Role> roleList = new ArrayList<>(role);
        for(int i = 0; i < role.size(); i++){
            roleName[i] = roleList.get(i).getName().name();
        }
        return roleName;
    }
}
