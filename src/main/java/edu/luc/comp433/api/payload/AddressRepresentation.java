package edu.luc.comp433.api.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.luc.comp433.business.dto.AddressDTO;
import edu.luc.comp433.util.Patterns;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressRepresentation extends RepresentationModel<AddressRepresentation> {

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

    public static AddressRepresentation of(AddressDTO dto) {
        return new AddressRepresentation(dto.getId(), dto.getCountry(), dto.getStreet(), dto.getUnit(), dto.getCity(), dto.getState(), dto.getZipcode(), dto.getPhonenumber());
    }
}
