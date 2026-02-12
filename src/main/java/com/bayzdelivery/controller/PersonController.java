package com.bayzdelivery.controller;

import java.util.List;

import com.bayzdelivery.dtos.ApiResponse;
import com.bayzdelivery.dtos.PersonDto;
import com.bayzdelivery.model.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.bayzdelivery.service.PersonService;

@RestController("/api")
public class PersonController {

  @Autowired
  PersonService personService;

  @PostMapping(path = "/person")
  public ResponseEntity<ApiResponse<Person>> register(@Valid @RequestBody PersonDto p) {
    ApiResponse<Person> response = personService.save(p);
    HttpStatus status = response.getMessage().startsWith("Database error")
        ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK;
    return ResponseEntity.status(status).body(response);
  }

  @GetMapping(path = "/person")
  public ResponseEntity<ApiResponse<List<Person>>> getAllPersons() {
    ApiResponse<List<Person>> response = personService.getAll();
    HttpStatus status = response.getMessage().startsWith("Error retrieving")
        ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK;
    return ResponseEntity.status(status).body(response);
  }

  @GetMapping(path = "/person/{person-id}")
  public ResponseEntity<ApiResponse<Person>> getPersonById(@PathVariable(name="person-id", required=true)Long personId) {
    ApiResponse<Person> response = personService.findById(personId);
    HttpStatus status = response.getMessage().startsWith("Error retrieving") 
        ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK;
    return ResponseEntity.status(status).body(response);
  }

}
