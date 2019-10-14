package edu.luc.comp433.api.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.luc.comp433.business.dto.AddressDto;
import edu.luc.comp433.util.Patterns;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "address")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressRepresentation {

    private long id;

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

    public static AddressRepresentation of(AddressDto dto) {
        return new AddressRepresentation(dto.getId(), dto.getCountry(), dto.getStreet(), dto.getUnit(), dto.getCity(), dto.getState(), dto.getZipcode(), dto.getPhonenumber());
    }
}
