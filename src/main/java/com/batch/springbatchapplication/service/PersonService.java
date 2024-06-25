package com.batch.springbatchapplication.service;

import com.batch.springbatchapplication.entities.Person;

import java.util.List;

public interface PersonService {
    void saveAll(List<Person> personsList);
}
