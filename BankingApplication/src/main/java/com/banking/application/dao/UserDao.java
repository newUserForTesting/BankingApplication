package com.banking.application.dao;

import java.util.List;

import com.banking.application.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Integer>{

    List<User> findByLastName(String lastName);

    List<User> findByFirstName(String firstName);

    List<User> findByEmail(String email);

    User findById(int id);
}
