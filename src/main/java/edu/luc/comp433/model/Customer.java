package edu.luc.comp433.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

import static edu.luc.comp433.util.Patterns.PHONE_PATTERN;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Validated
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank
    private String firstName;

    @NonNull
    @NotBlank
    private String lastName;

    @NonNull
    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @Pattern(regexp = PHONE_PATTERN)
    private String phonenumber;

    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Payment> paymentOptions = new ArrayList<>();

    @OneToMany(
            mappedBy = "customer",
            fetch = FetchType.LAZY
    )
    private List<Order> orders;

    public void addPaymentOption(Payment payment) {
        paymentOptions.add(payment);
        payment.setCustomer(this);
    }

    public void removePaymentOption(Payment payment) {
        paymentOptions.remove(payment);
        payment.setCustomer(null);
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
        addresses.remove(address);
    }

    public Payment getDefaultPayment() {
        if (paymentOptions.isEmpty()) return null;
        return paymentOptions.get(0);
    }

    public Address getDefaultAddress() {
        if (addresses.isEmpty()) return null;
        return addresses.get(0);
    }

    public void addReview(Review r) {
        r.setCustomer(this);
        this.reviews.add(r);
    }

    public void removeReview(Review r) {
        r.setCustomer(null);
        this.reviews.remove(r);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return id != null ? id.equals(customer.id) : customer.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
