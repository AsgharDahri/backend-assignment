package com.bayzdelivery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bayzdelivery.dtos.PersonDto;
import com.bayzdelivery.dtos.ResponseDto;
import com.bayzdelivery.repositories.PersonRepository;
import com.bayzdelivery.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public List<Person> getAll() {
        List<Person> personList = new ArrayList<>();
        personRepository.findAll().forEach(personList::add);
        return personList;
    }

    public Person save(PersonDto personRequest) {
        try{
            if(isPersonExist(personRequest).isEmpty()) {
                Person personModel = Person.builder()
                        .name(personRequest.getName())
                        .email(personRequest.getEmail())
                        .type(personRequest.getType())
                        .registrationNumber(personRequest.getRegistrationNumber())
                        .build();
                Person savedPerson = personRepository.save(personModel);
                return ResponseDto.success("Person registered successfully", savedPerson).getData();
            }
            else {
                throw new RuntimeException("User with this email already exists: " + personRequest.getEmail());
            }
        } catch (Exception e) {
            // Catch-all for unexpected errors
            throw new RuntimeException("An error occurred while saving the person: " + e.getMessage());
        }
    }

    @Override
    public Person findById(Long personId) {
        Optional<Person> dbPerson = personRepository.findById(personId);
        return dbPerson.orElse(null);
    }

    private Optional<Person> isPersonExist(PersonDto personRequest) {
        return personRepository.findByEmail(personRequest.getEmail());
    }
}
