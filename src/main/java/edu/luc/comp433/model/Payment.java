package edu.luc.comp433.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String nameOnCard;

    private String cardNumber;

    private String cardType;

    private int expireMonth;

    private int expireYear;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address billingAddress;


}
