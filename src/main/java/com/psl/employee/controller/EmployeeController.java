package com.psl.employee.controller;

import com.psl.employee.model.Employee;
import com.psl.employee.service.EmployeeService;
import com.psl.employee.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value="/employees")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> list = null;
        try {
            list = employeeService.getAllEmployees();
            return ResponseEntity.ok(list);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/employee")
    public ResponseEntity<Employee> getEmployeeById(@RequestParam(value = "id")Integer id){
        try {
            Employee employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employee);  // return 200, with json body
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }


    @GetMapping("/employee/manager")
    public ResponseEntity<List<Employee>> getEmployeeByMgrid(@RequestParam(value = "mgrid")Integer mgrid){
        List<Employee> list= null;
        try {
            list = employeeService.getEmployeeByMgrId(mgrid);
            return ResponseEntity.ok(list);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }


    @PostMapping("/employee")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) throws URISyntaxException {
        try {
            Employee newEmployee = employeeService.createEmployee(employee);
            return ResponseEntity.created(new URI("employee/id=" + newEmployee.getId())).body(employee);
        } catch (ResourceAlreadyExistsException ex) {
            // log exception first, then return Conflict (409)
               logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (BadResourceException ex) {
            // log exception first, then return Bad Request (400)
           logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PutMapping("/employee")
    public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody Employee employee){
        try {
            employeeService.updateEmployee(employee);
            return ResponseEntity.ok(employee);
        } catch (ResourceNotFoundException ex) {
            // log exception first, then return Not Found (404)
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (BadResourceException ex) {
            // log exception first, then return Bad Request (400)
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PutMapping("/employee/delete")
    public ResponseEntity<Void> deleteEmployee(@RequestParam(value="id")Integer id){
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            // log exception first, then return Not Found (404)
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }


    }

}
