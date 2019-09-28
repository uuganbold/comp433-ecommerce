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

    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Review> reviews;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Address> addresses;

    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Payment> paymentOptions;

    @OneToMany(
            mappedBy = "customer"
    )
    private Set<CustomerOrder> orders;

    public void addPaymentOption(Payment payment) {
        paymentOptions.add(payment);
        payment.setCustomer(this);
    }

    public void removePaymentOption(Payment payment) {
        paymentOptions.remove(payment);
        payment.setCustomer(null);
    }
}
