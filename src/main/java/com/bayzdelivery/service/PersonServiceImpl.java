package com.bayzdelivery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bayzdelivery.dtos.ApiResponse;
import com.bayzdelivery.dtos.PersonDto;
import com.bayzdelivery.repositories.PersonRepository;
import com.bayzdelivery.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public ApiResponse<List<Person>> getAll() {
        try {
            List<Person> personList = new ArrayList<>();
            personRepository.findAll().forEach(personList::add);

            return ApiResponse.<List<Person>>builder()
                    .success(true)
                    .message("Persons retrieved successfully")
                    .data(personList)
                    .build();
        } catch (Exception e) {

            return ApiResponse.<List<Person>>builder()
                    .success(false)
                    .message("Error retrieving persons: " + e.getMessage())
                    .data(null)
                    .build();
        }
    }

    @Override
    public ApiResponse<Person> save(PersonDto personRequest) {
        try {
            if(isPersonExist(personRequest).isPresent()) {

                return ApiResponse.<Person>builder()
                        .success(false)
                        .message("User with this email already exists: " + personRequest.getEmail())
                        .data(null)
                        .build();
            }
            
            Person personModel = Person.builder()
                    .name(personRequest.getName())
                    .email(personRequest.getEmail())
                    .type(personRequest.getType())
                    .registrationNumber(personRequest.getRegistrationNumber())
                    .build();
            Person savedPerson = personRepository.save(personModel);

            return ApiResponse.<Person>builder()
                    .success(true)
                    .message("Person registered successfully")
                    .data(savedPerson)
                    .build();
        } catch (Exception e) {

            return ApiResponse.<Person>builder()
                    .success(false)
                    .message("Database error: " + e.getMessage())
                    .data(null)
                    .build();
        }
    }

    @Override
    public ApiResponse<Person> findById(Long personId) {
        try {
            Optional<Person> dbPerson = personRepository.findById(personId);
            if (dbPerson.isPresent()) {

                return ApiResponse.<Person>builder()
                        .success(true)
                        .message("Person retrieved successfully")
                        .data(dbPerson.get())
                        .build();
            } else {

                return ApiResponse.<Person>builder()
                        .success(false)
                        .message("Person not found with id: " + personId)
                        .data(null)
                        .build();
            }
        } catch (Exception e) {

            return ApiResponse.<Person>builder()
                    .success(false)
                    .message("Error retrieving person: " + e.getMessage())
                    .data(null)
                    .build();
        }
    }

    private Optional<Person> isPersonExist(PersonDto personRequest) {
        return personRepository.findByEmail(personRequest.getEmail());
    }
}
