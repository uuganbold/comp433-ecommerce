package edu.luc.comp433.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String website;

    private String email;

    @OneToMany(mappedBy = "seller")
    private Set<Product> products;

    @OneToMany
    private Set<Address> addresses;
}
