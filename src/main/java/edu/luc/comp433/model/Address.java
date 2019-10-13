package edu.luc.comp433.model;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NonNull
    private String country;

    @NotBlank
    @NonNull
    private String street;

    private String unit;

    @NotBlank
    @NonNull
    private String city;

    private String state;

    private int zipcode;

    private String phonenumber;

    public Address(@NonNull String country, @NonNull String street, String unit, @NonNull String city, String state, int zipcode, String phonenumber) {
        this.country = country;
        this.street = street;
        this.unit = unit;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.phonenumber = phonenumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return id.equals(address.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
