package com.psl.employee.repository;

import com.psl.employee.jwtModels.DAOUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<DAOUser, Integer> {
  DAOUser findByUsername(String username);
}
