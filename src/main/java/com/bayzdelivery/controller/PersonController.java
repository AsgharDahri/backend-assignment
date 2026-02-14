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
  public ResponseEntity<ApiResponse<Object>> registerPerson(@Valid @RequestBody PersonDto p) {
    ApiResponse<Object> response = personService.registerPerson(p);
    return ResponseEntity.ok(response);
  }

  @GetMapping(path = "/person")
  public ResponseEntity<ApiResponse<List<Object>>> getAllPersons() {
    ApiResponse<List<Object>> response = personService.getAllPeople();
    return ResponseEntity.ok(response);
  }

  @GetMapping(path = "/person/{person-id}")
  public ResponseEntity<ApiResponse<Object>> getPersonById(@PathVariable(name="person-id", required=true)Long personId) {
    ApiResponse<Object> response = personService.findPersonByid(personId);
    return ResponseEntity.ok(response);
  }

}
