package com.employee.controller;

import com.employee.exception.ResourceNotFoundException;
import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private ProducerService producerService;

    @GetMapping("/employee")
    public List<Employee> getAllEmployees()
    {
        return employeeRepository.findAll();
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable(name = "id") long id)
    {
        return employeeRepository.findById(id) .orElseThrow(() -> new ResourceNotFoundException("Employee","id",id));
    }

    @PostMapping("/employee")
    public Employee saveEmployee(@RequestBody Employee employee)
    {
        return employeeRepository.save(employee);
    }

    @PutMapping("/employee/{id}")
    public Employee editEmployee(@PathVariable(value = "id") Long id,@RequestBody Employee employee)
    {
        Employee employee1 = employeeRepository.findById(id) .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        employee1.setAddress(employee.getAddress());
        employee1.setEmail(employee.getEmail());
        employee1.setFullname(employee.getFullname());
        employee1.setMobile(employee.getMobile());
        employee1.setNic(employee.getNic());
        employee1.setPassword(employee.getPassword());
        employee1.setCompanyId(employee.getCompanyId());
        Employee updatedEmployee = employeeRepository.save(employee1);
        return updatedEmployee;
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") Long id)
    {
        Employee employee = employeeRepository.findById(id) .orElseThrow(() -> new ResourceNotFoundException("Employee","id",id));
        employeeRepository.delete(employee);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/produce")
    public Object sendMsg(@RequestBody Employee employee) throws Exception {
        return producerService.sendMsg(employee.getCompanyId());
    }
}
