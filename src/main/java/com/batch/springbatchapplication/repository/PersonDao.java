package com.batch.springbatchapplication.repository;

import com.batch.springbatchapplication.entities.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonDao extends CrudRepository<Person, Long> {
}
