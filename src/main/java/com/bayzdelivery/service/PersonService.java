package com.bayzdelivery.service;

import java.util.List;

import com.bayzdelivery.dtos.ApiResponse;
import com.bayzdelivery.dtos.PersonDto;
import com.bayzdelivery.model.Person;

public interface PersonService {
  public ApiResponse<List<Person>> getAll();

  public ApiResponse<Person> save(PersonDto p);

  public ApiResponse<Person> findById(Long personId);

}
