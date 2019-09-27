package edu.luc.comp433.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String country;

    private String street;

    private String unit;

    private String city;

    private String state;

    private int zipcode;

    private String phonenumber;

}
