package com.psl.employee.service;

import com.psl.employee.exceptions.BadResourceException;
import com.psl.employee.exceptions.ResourceAlreadyExistsException;
import com.psl.employee.exceptions.ResourceNotFoundException;
import com.psl.employee.model.Employee;
import com.psl.employee.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeServiceTest {
  @InjectMocks EmployeeService service;

  @Mock EmployeeRepository repository;

  private static final int id = 1;
  private static final int check = 0;
  private static final int mgrid = 501;

  @Test
  public void existsByIdTest() {
    int empNo = 0;
    Employee employee = mock(Employee.class);
    when(employee.getId()).thenReturn(id);
    when(employee.getFname()).thenReturn("test fName");
    when(employee.getLname()).thenReturn("test lName");
    when((employee.getEmpno())).thenReturn(empNo);
    when(employee.getMgrid()).thenReturn(mgrid);
    when(repository.findByIsDeletedAndId(check, id)).thenReturn(employee);
    service.existsById(id);
    verify(repository).findByIsDeletedAndId(check, id);
  }

  @Test
  public void getAllEmployeesTest() throws ResourceNotFoundException {
    int empNo = 0;
    List<Employee> list = new ArrayList<Employee>();
    Employee employee = mock(Employee.class);
    when(employee.getId()).thenReturn(id);
    when(employee.getFname()).thenReturn("test fName");
    when(employee.getLname()).thenReturn("test lName");
    when((employee.getEmpno())).thenReturn(empNo);
    when(employee.getMgrid()).thenReturn(mgrid);
    list.add(employee);
    when(repository.findByIsdeleted(check)).thenReturn(list);
    service.getAllEmployees();
    verify(repository).findByIsdeleted(check);
  }

  @Test
  public void getEmployeeByIdTest() throws ResourceNotFoundException {
    int empNo = 0;
    Employee employee = mock(Employee.class);
    when(employee.getId()).thenReturn(id);
    when(employee.getFname()).thenReturn("test fName");
    when(employee.getLname()).thenReturn("test lName");
    when((employee.getEmpno())).thenReturn(empNo);
    when(employee.getMgrid()).thenReturn(mgrid);
    when(repository.findByIsDeletedAndId(check, id)).thenReturn(employee);
    service.getEmployeeById(id);
    verify(repository).findByIsDeletedAndId(check, id);
  }

  @Test
  public void getEmployeeByMgrIdTest() throws ResourceNotFoundException {
    int empNo = 0;
    List<Employee> list = new ArrayList<Employee>();
    Employee employee = mock(Employee.class);
    when(employee.getId()).thenReturn(id);
    when(employee.getFname()).thenReturn("test fName");
    when(employee.getLname()).thenReturn("test lName");
    when((employee.getEmpno())).thenReturn(empNo);
    when(employee.getMgrid()).thenReturn(mgrid);
    list.add(employee);
    when(repository.findByIsdeleted(check)).thenReturn(list);
    service.getEmployeeByMgrId(mgrid);
    verify(repository).findByMgrid(check, mgrid);
  }

  @Test
  public void createEmployeeTest() throws BadResourceException, ResourceAlreadyExistsException {
    int empNo = 0;
    Employee employee = mock(Employee.class);
    employee.setId(id);
    employee.setMgrid(mgrid);
    employee.setEmpno(empNo);
    employee.setFname("prerna");
    employee.setLname("kapoor");
    employee.setIsdeleted(0);
    service.createEmployee(employee);
    verify(repository).save(employee);
  }

  @Test
  public void updateEmployeeTest() throws ResourceNotFoundException {
    int empNo = 0;
    Employee employee = mock(Employee.class);
    when(employee.getId()).thenReturn(id);
    when(employee.getFname()).thenReturn("test fName");
    when(employee.getLname()).thenReturn("test lName");
    when((employee.getEmpno())).thenReturn(empNo);
    when(employee.getMgrid()).thenReturn(mgrid);
    when(repository.findByIsDeletedAndId(check, id)).thenReturn(employee);
    service.updateEmployee(employee);
    verify(repository).save(employee);
  }

  @Test
  public void deleteEmployee() throws ResourceNotFoundException {
    int getIsDeleted = 0;
    Employee employee = mock(Employee.class);
    when(employee.getId()).thenReturn(id);
    when(employee.getIsdeleted()).thenReturn(getIsDeleted);
    when(repository.findByIsDeletedAndId(check, id)).thenReturn(employee);
    service.deleteEmployee(id);
    verify(repository).save(employee);
  }
}
