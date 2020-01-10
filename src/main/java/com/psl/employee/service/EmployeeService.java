package com.psl.employee.service;

import com.psl.employee.model.Employee;
import com.psl.employee.exceptions.*;
import com.psl.employee.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final int check = 0;

    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean existsById(int id) {
        logger.info("entered method(service): check if employee with id: "+id+" exists");
        boolean flag = true;
        Employee employee = employeeRepository.findByIsDeletedAndId(check,id);
        if(employee==null){
            logger.warn("no employee found with id "+id);
            flag=false;
        }
        return flag;
    }

    public List<Employee> getAllEmployees() throws ResourceNotFoundException {
        logger.info("entered method(service): get all employees");
        List<Employee> eList = employeeRepository.findByIsdeleted(check);
        List<Employee> xlist=new ArrayList<Employee>();
        for(Employee e: eList){
            if(e.getFname().startsWith("di")){
                xlist.add(e);
            }
        }
        if (eList.isEmpty()){
            logger.warn("no employees found");
            throw new ResourceNotFoundException("No Contacts found");
        }
        return xlist;
    }

    public Employee getEmployeeById(int id) throws ResourceNotFoundException {
        logger.info("entered method(service): get employee with id "+id);
        Employee employee = employeeRepository.findByIsDeletedAndId(check,id);
        if(employee==null)
        {
            logger.warn("no employee found with id "+id);
            throw new ResourceNotFoundException("Cannot find contact with id: "+id);
        }
        else return employee;
    }

    public List<Employee> getEmployeeByMgrId(int mgrId) throws ResourceNotFoundException {
        logger.info("entered method(service): get all employees with manager id: "+mgrId);
        List<Employee> list= employeeRepository.findByMgrid(check,mgrId);
        if(list.isEmpty()){
            logger.warn("no employee found with manager id "+mgrId);
            throw new ResourceNotFoundException("no data available for manager id :: "+mgrId);
        }
        else return list;
    }

    public Employee createEmployee(Employee employee) throws BadResourceException, ResourceAlreadyExistsException {
        logger.info("entered method(service): create employee with id "+employee.getId());
        if(!employee.getFname().isEmpty()){
            if(employee.getId() != 0 && existsById(employee.getId())){
                throw new ResourceAlreadyExistsException("Contact with id "+employee.getId()+" already exists");
            }
            return employeeRepository.save(employee);
        }
        else{
            BadResourceException badException = new BadResourceException("failed to save contact");
            badException.addErrorMessage("Contact is null or empty");
            throw badException;
        }
    }


    public Employee updateEmployee(Employee employee) throws ResourceNotFoundException{
        logger.info("entered method(service): update employee with id "+employee.getId());
        if(existsById(employee.getId())){
            Employee newEmployee = employeeRepository.findByIsDeletedAndId(check, employee.getId());
            newEmployee.setFname(employee.getFname());
            newEmployee.setLname(employee.getLname());
            newEmployee.setEmpno(employee.getEmpno());
            newEmployee.setMgrid(employee.getMgrid());
            employeeRepository.save(newEmployee);
            return  newEmployee;
    } else {
      throw new ResourceNotFoundException("Cannot find contact with id " + employee.getId());
        }
    }


    public String deleteEmployee(int id) throws ResourceNotFoundException {
        logger.info("entered method(service): delete employee with id "+id);
        if (existsById(id)) {
            Employee employee = employeeRepository.findByIsDeletedAndId(check, id);
            employee.setIsdeleted(1);
            employeeRepository.save(employee);
            return "employee deleted with id: " + id;
        } else throw new ResourceNotFoundException("Contact with given id doesn't exists");
    }
}
