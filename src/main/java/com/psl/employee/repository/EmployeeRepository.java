package com.psl.employee.repository;

import com.psl.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    @Query("from Employee where isdeleted=?1")
    List<Employee> findByIsdeleted(int i);

    @Query("from Employee where isdeleted=?1 and id=?2")
    Employee findByIsDeletedAndId(int check, int id);

    @Query("from Employee where isdeleted=?1 and mgrid=?2")
    List<Employee> findByMgrid(int check, int mgrid);
}
