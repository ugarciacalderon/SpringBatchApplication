package com.batch.springbatchapplication.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Persons")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;
    @Column(name = "name")
    @Getter
    @Setter
    private String name;
    @Column(name = "last_name")
    @Getter
    @Setter
    private String lastName;
    @Column(name = "age")
    @Getter
    @Setter
    private Integer age;
    @Getter
    @Setter
    @Column(name = "insertion_date")
    private Date insertionDate;
}
