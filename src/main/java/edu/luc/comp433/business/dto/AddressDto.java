package edu.luc.comp433.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private long id;

    @NotNull
    @Min(2)
    private String country;

    @NotNull
    @NonNull
    @Size(min = 5, max = 100)
    private String street;

    @Size(min = 3, max = 20)
    private String unit;

    @NotNull
    @NonNull
    @Size(min = 3, max = 20)
    private String city;

    private String state;

    private int zipcode;

    @Max(20)
    private String phonenumber;

    public AddressDto(@NotNull @Min(2) String country, @NotNull @NonNull @Size(min = 5, max = 100) String street, @Size(min = 3, max = 20) String unit, @NotNull @NonNull @Size(min = 3, max = 20) String city, String state, int zipcode, @Max(20) String phonenumber) {
        this.country = country;
        this.street = street;
        this.unit = unit;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.phonenumber = phonenumber;
    }
}
