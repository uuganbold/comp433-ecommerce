package edu.luc.comp433.business.dto;

import edu.luc.comp433.model.Address;
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
public class AddressDTO {

    private Long id;

    @Size(min = 2)
    private String country;

    @Size(min = 5, max = 100)
    private String street;

    @Size(min = 3, max = 20)
    private String unit;

    @Size(min = 3, max = 20)
    private String city;

    private String state;

    private int zipcode;

    @Size(max = 20)
    private String phonenumber;

    public AddressDTO(@NotNull @Min(2) String country, @NotNull @NonNull @Size(min = 5, max = 100) String street, @Size(min = 3, max = 20) String unit, @NotNull @NonNull @Size(min = 3, max = 20) String city, String state, int zipcode, @Max(20) String phonenumber) {
        this.country = country;
        this.street = street;
        this.unit = unit;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.phonenumber = phonenumber;
    }

    public AddressDTO(long id) {
        this.id = id;
    }

    public static AddressDTO of(Address a) {
        if (a == null) return null;
        return new AddressDTO(a.getId(), a.getCountry(), a.getStreet(), a.getUnit(), a.getCity(), a.getState(), a.getZipcode(), a.getPhonenumber());
    }

    public Address toEntity() {
        return new Address(this.id, this.country, this.street,
                this.unit, this.city, this.getState(),
                this.zipcode, this.phonenumber);
    }
}
