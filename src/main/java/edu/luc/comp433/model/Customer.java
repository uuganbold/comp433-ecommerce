package edu.luc.comp433.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phonenumber;

    @OneToMany(mappedBy = "customer")
    private Set<Review> reviews;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Address> addresses;

    @OneToMany(mappedBy = "customer")
    private Set<Payment> paymentOptions;

    @OneToMany(mappedBy = "customer")
    private Set<CustomerOrder> orders;
}
