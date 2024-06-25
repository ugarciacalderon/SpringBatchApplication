package com.batch.springbatchapplication.service.impl;

import com.batch.springbatchapplication.entities.Person;
import com.batch.springbatchapplication.repository.PersonDao;
import com.batch.springbatchapplication.service.PersonService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonDao personDao;

    @Override
    @Transactional
    public void saveAll(List<Person> personsList) {
        personDao.saveAll(personsList);
    }
}
