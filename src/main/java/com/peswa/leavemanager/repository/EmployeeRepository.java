package com.peswa.leavemanager.repository;

import com.peswa.leavemanager.model.Employee;
import com.peswa.leavemanager.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Boolean existsByOfficialEmail(String email);

    Employee findByUser(User user);
}
