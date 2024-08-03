package com.samsoftware.example.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

// TABLE_PER_CLASS (creates each subclass with the attributes from resource)
// *change to abstract otherwise it will create a table for resource too

//JOINED (we have the resource table (with common columns)
// + each of the subclass (with only the different columns + id linked to resource table)

// SINGLE_TABLE (we have only resource table
// with all columns of all subclasses and a column to differentiate)

//@DiscriminatorColumn(name = "resource_type") // used in SINGLE_TABLE
public abstract class Resource {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private int size;

    private String url;

    @OneToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;
}
