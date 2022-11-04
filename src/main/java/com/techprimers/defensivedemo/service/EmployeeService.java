package com.techprimers.defensivedemo.service;

import com.techprimers.defensivedemo.advice.EmployeeNotFoundException;
import com.techprimers.defensivedemo.advice.EmployeeValidationException;
import com.techprimers.defensivedemo.model.Employee;
import com.techprimers.defensivedemo.repository.EmployeeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository repository;

    Logger logger = org.slf4j.LoggerFactory.getLogger(EmployeeService.class);


    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    public Employee get(Integer employeeId) {
        Optional<Employee> optionalEmployee = repository.findById(employeeId);
        if (optionalEmployee.isPresent()) {
        return  optionalEmployee.get();
        } else {
            throw new EmployeeNotFoundException("Employee Not found: " + employeeId);
        }
    }

    public Employee update(Integer employeeId, Employee employee) {
        if (employeeId != employee.getEmployeeId()) {
            throw new EmployeeValidationException("Mismatch in EmployeeId");
        }
        Optional<Employee> employeeOptional = repository.findById(employeeId);
        if (employeeOptional.isPresent()) {
            return repository.save(employee);
        }
        else {
            throw new EmployeeNotFoundException("Employee Not found: " + employeeId);
        }
    }






    public List<Employee> getAll() {
        List <Employee> employees = null;

        try {
            employees = repository.findAll();
        } catch (Exception e) {
            logger.error("Caught an exception.");
            employees = new ArrayList<>();
        }
        return employees;
    }


}
