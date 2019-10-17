package edu.luc.comp433.model;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

import static edu.luc.comp433.util.Patterns.PHONE_PATTERN;

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

    @Pattern(regexp = PHONE_PATTERN, message = "Invalid phone number")
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
}
