package com.psl.employee.service;

import com.psl.employee.model.Employee;
import com.psl.employee.exceptions.*;
import com.psl.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class EmployeeService {

    private final int check = 0;

    @Autowired
    private EmployeeRepository employeeRepository;

    private boolean existsById(int id) {
        boolean flag = true;
        Employee employee = employeeRepository.findByIsDeletedAndId(check,id);
        if(employee==null){
            flag=false;
        }
        return flag;
    }

    public List<Employee> getAllEmployees() throws ResourceNotFoundException {
        List<Employee> eList = employeeRepository.findByIsdeleted(check);
        if(eList.isEmpty())
            throw new ResourceNotFoundException("No Contacts found");
        return eList;
    }

    public Employee getEmployeeById(int id) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findByIsDeletedAndId(check,id);
        if(employee==null)
            throw new ResourceNotFoundException("Cannot find contact with id: "+id);
        else return employee;
    }

    public List<Employee> getEmployeeByMgrId(int mgrId) throws ResourceNotFoundException {
        List<Employee> list= employeeRepository.findByMgrid(check,mgrId);
        if(list.isEmpty()){
            throw new ResourceNotFoundException("no data available for manager id :: "+mgrId);
        }
        else return list;
    }

    public Employee createEmployee(Employee employee) throws BadResourceException, ResourceAlreadyExistsException {
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


    public Employee updateEmployee(Employee employee) throws ResourceNotFoundException, BadResourceException {
        if(!employee.getFname().isEmpty()){
            if (employee.getId() != 0 && existsById(employee.getId())){
                throw new ResourceNotFoundException("Cannot find contact with id "+employee.getId());
            }
            return employeeRepository.save(employee);
        }
        else{
            BadResourceException badException = new BadResourceException("failed to save contact");
            badException.addErrorMessage("Contact is null or empty");
            throw badException;
        }
    }


    public String deleteEmployee(int id) throws ResourceNotFoundException {
        if (existsById(id)) {
            Employee employee = employeeRepository.findByIsDeletedAndId(check, id);
            employee.setIsdeleted(1);
            employeeRepository.save(employee);
            return "employee deleted with id: " + id;
        } else throw new ResourceNotFoundException("Contact with given id doesn't exists");
    }
}
