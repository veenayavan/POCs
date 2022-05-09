package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    private Long id;

    private String name;

    private String gender;

    private Long age;

    private String[] occupations;

}
