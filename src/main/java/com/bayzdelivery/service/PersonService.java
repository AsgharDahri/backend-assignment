package com.bayzdelivery.service;

import java.util.List;
import java.util.Optional;

import com.bayzdelivery.dtos.ApiResponse;
import com.bayzdelivery.dtos.PersonDto;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.utilites.PERSON_TYPE;

public interface PersonService {
  public ApiResponse<List<Person>> getAllPeople();

  public ApiResponse<Person> registerPerson(PersonDto p);

  public ApiResponse<Person> findPersonByid(Long personId);

  Optional<Person> findPersonByIdAndType(Long personId, PERSON_TYPE type);

}
