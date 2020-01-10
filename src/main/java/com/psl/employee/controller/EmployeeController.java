package com.psl.employee.controller;

import com.psl.employee.model.Employee;
import com.psl.employee.service.EmployeeService;
import com.psl.employee.exceptions.*;
import io.swagger.annotations.*;
import lombok.extern.slf4j.XSlf4j;
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

/**
 * Represents all the end points of the Employee Service API
 */
@RestController
@RequestMapping("/api")
@Api(
    value = "Employee Management System",
    description = "Operations pertaining to employee in Employee Management System")
// @XSlf4j
public class EmployeeController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  @Autowired private EmployeeService employeeService;

  @ApiOperation(
      value = "Returns employee details",
      notes = "Returns a complete list of employees details.",
      response = List.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful retrieval of employee details",
            response = List.class),
        @ApiResponse(code = 404, message = "No data available"),
        @ApiResponse(code = 401, message = "not authorized!"),
        @ApiResponse(code = 403, message = "Access forbidden"),
        @ApiResponse(code = 500, message = "Internal server error")
      })
  @GetMapping(value = "/employees")
  public ResponseEntity<List<Employee>> getAllEmployees() {
    logger.info("entered method(controller): access all employees");
    List<Employee> list = null;
    try {
      list = employeeService.getAllEmployees();
      return ResponseEntity.ok(list);
    } catch (ResourceNotFoundException e) {
      logger.error(e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @ApiOperation(
      value = "Returns specific employee details",
      notes = "Returns employee's details with given id",
      response = Employee.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful retrieval of employee details",
            response = Employee.class),
        @ApiResponse(code = 404, message = "No data available"),
        @ApiResponse(code = 401, message = "not authorized!"),
        @ApiResponse(code = 403, message = "Access forbidden"),
        @ApiResponse(code = 500, message = "Internal server error")
      })
  @GetMapping(value = "/employee/{id}")
  public ResponseEntity<Employee> getEmployeeById(
      @ApiParam(value = "Employee id", required = true) @PathVariable("id") int id) {
    logger.info("entered method(controller): get employee with id " + id);
    try {
      Employee employee = employeeService.getEmployeeById(id);
      return ResponseEntity.ok(employee); // return 200, with json body
    } catch (ResourceNotFoundException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
    }
  }

  @ApiOperation(
      value = "Returns employee details with given manager id",
      notes = "Returns a complete list of employees details under given manager id",
      response = List.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful retrieval of employee details",
            response = List.class),
        @ApiResponse(code = 404, message = "No data available"),
        @ApiResponse(code = 401, message = "not authorized!"),
        @ApiResponse(code = 403, message = "Access forbidden"),
        @ApiResponse(code = 500, message = "Internal server error")
      })
  @GetMapping("/employee/manager/{mgrid}")
  public ResponseEntity<List<Employee>> getEmployeeByMgrid(
      @ApiParam(value = "Manager id", required = true) @PathVariable("mgrid") int mgrid) {
    logger.info("entered method(controller): get all employees with manager id: " + mgrid);
    List<Employee> list = null;
    try {
      list = employeeService.getEmployeeByMgrId(mgrid);
      return ResponseEntity.ok(list);
    } catch (ResourceNotFoundException e) {
      logger.error(e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @ApiOperation(
      value = "insert employee in the database",
      notes = "create employee in the database",
      response = Employee.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful insertion of employee details",
            response = Employee.class),
        @ApiResponse(code = 401, message = "not authorized!"),
        @ApiResponse(code = 500, message = "Internal server error")
      })
  @PostMapping("/employee")
  public ResponseEntity<Employee> createEmployee(
      @ApiParam("Employee information for a new employee to be created") @Valid @RequestBody
          Employee employee)
      throws URISyntaxException {
    logger.info("entered method(controller): all employees are accessed by the user");
    try {
      Employee newEmployee = employeeService.createEmployee(employee);
      return ResponseEntity.created(new URI("employee/id=" + newEmployee.getId())).body(employee);
    } catch (ResourceAlreadyExistsException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    } catch (BadResourceException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @ApiOperation(
      value = "update employee in the database",
      notes = "update employee in the database",
      response = Employee.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful updation of employee details",
            response = Employee.class),
        @ApiResponse(code = 401, message = "not authorized!"),
        @ApiResponse(code = 403, message = "Access forbidden"),
        @ApiResponse(code = 500, message = "Internal server error")
      })
  @PutMapping("/employee")
  public ResponseEntity<Employee> updateEmployee(
      @ApiParam("Employee information of an existing employee to be updated") @Valid @RequestBody
          Employee employee) {
    logger.info("entered method(controller): update employee with id: " + employee.getId());
    try {
      employeeService.updateEmployee(employee);
      return ResponseEntity.ok(employee);
    } catch (ResourceNotFoundException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.notFound().build();
    }
  }

  @ApiOperation(
      value = "delete employee from the database",
      notes = "delete employee from the database",
      response = Employee.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful deletion of employee",
            response = String.class),
        @ApiResponse(code = 404, message = "No data available"),
        @ApiResponse(code = 401, message = "not authorized!"),
        @ApiResponse(code = 403, message = "Access forbidden"),
        @ApiResponse(code = 500, message = "Internal server error")
      })
  @PutMapping("/employee/delete")
  public ResponseEntity<String> deleteEmployee(
      @ApiParam(value = "Employee id", required = true) @PathVariable("id") int id) {
    logger.info("entered method(controller): delete employee with id: " + id);
    try {
      return ResponseEntity.ok(employeeService.deleteEmployee(id));
    } catch (ResourceNotFoundException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.notFound().build();
    }
  }
}
