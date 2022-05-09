package org.example.service;

import org.example.entity.Person;
import org.example.repository.PersonRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonService {

    private final PersonRepository repository;


    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Optional<Person> findById(Long id) {
        return repository.findById(id);
    }

    public List<Person> findAll() {
        return repository.findAll();
    }


    public void save(List<Person> persons) {
        repository.saveAll(persons);
    }
}
