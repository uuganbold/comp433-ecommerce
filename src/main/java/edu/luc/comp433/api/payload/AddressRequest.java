package edu.luc.comp433.api.payload;

import edu.luc.comp433.business.dto.AddressDTO;
import edu.luc.comp433.util.Patterns;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@XmlRootElement(name = "address")
public class AddressRequest {

    @NotNull
    @Size(min = 2)
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

    @Size(max = 20)
    @Pattern(regexp = Patterns.PHONE_PATTERN)
    private String phonenumber;

    public AddressDTO toDTO() {
        return new AddressDTO(this.country, this.street, this.unit, this.city, this.state, this.zipcode, this.phonenumber);
    }

}
