package com.bayzdelivery.service;

import java.util.List;
import java.util.Optional;

import com.bayzdelivery.dtos.ApiResponse;
import com.bayzdelivery.dtos.PersonDto;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.utilites.PERSON_TYPE;

public interface PersonService {
    ApiResponse<List<Object>> getAllPeople();
    ApiResponse<Object> registerPerson(PersonDto p);
    ApiResponse<Object> findPersonByid(Long personId);
    Optional<Person> findPersonByIdAndType(Long personId, PERSON_TYPE type);

}
