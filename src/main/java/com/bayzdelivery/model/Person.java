package com.bayzdelivery.model;

import java.io.Serializable;

import com.bayzdelivery.utilites.PERSON_TYPE;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "person")
@Data
@Builder
@AllArgsConstructor
public class Person implements Serializable{

  private static final long serialVersionUID = 432154291451321L;

  public Person() {}

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "name")
  String name;

  @NotNull
  @Email
  @Column(name = "email")
  String email;

  @NotNull
  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  PERSON_TYPE type;

  @Column(name = "registration_number")
  String registrationNumber;





}
