package edu.luc.comp433.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @NonNull
    @Column(unique = true)
    private String name;

    @URL
    private String website;

    @Email
    private String email;

    @OneToMany(
            mappedBy = "seller",
            fetch = FetchType.LAZY
    )
    private List<Product> products = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Address> addresses = new ArrayList<>();

    public Seller(String name, String website, String email) {
        this.name = name;
        this.website = website;
        this.email = email;
    }

    public void addProduct(Product p) {
        p.setSeller(this);
        this.products.add(p);
    }

    public void removeProduct(Product p) {
        this.products.remove(p);
        p.setSeller(null);
    }

    public void addAddress(Address address) {
        addresses.add(address);
    }

    public Address getAddress(long addressId) {
        for (Address a : this.addresses) {
            if (a.getId() == addressId) return a;
        }
        return null;
    }

    public void removeAddress(Address address) {
        this.addresses.remove(address);
    }

}
