package com.bayzdelivery.controller;

import java.util.List;

import com.bayzdelivery.dtos.PersonDto;
import com.bayzdelivery.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
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
  public ResponseEntity<Person> register(@RequestBody PersonDto p) {
    return ResponseEntity.ok(personService.save(p));
  }

  @GetMapping(path = "/person")
  public ResponseEntity<List<Person>> getAllPersons() {
    return ResponseEntity.ok(personService.getAll());
  }

  @GetMapping(path = "/person/{pers-id}")
  public ResponseEntity<Person> getPersonById(@PathVariable(name="person-id", required=true)Long personId) {
    Person person = personService.findById(personId);
    if (person != null) {
      return ResponseEntity.ok(person);
    }
    return ResponseEntity.notFound().build();
  }

}
