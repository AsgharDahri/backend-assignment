package com.bayzdelivery.repositories;

import com.bayzdelivery.utilites.PERSON_TYPE;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import com.bayzdelivery.model.Person;

import java.util.Optional;

@RestResource(exported=false)
public interface PersonRepository extends CrudRepository<Person, Long>, PagingAndSortingRepository<Person, Long> {

    Optional<Person> findByEmail(String email);
    Optional<Person> findByIdAndType(Long id, PERSON_TYPE type);
}
