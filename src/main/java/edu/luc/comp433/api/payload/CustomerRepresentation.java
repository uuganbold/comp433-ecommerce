package edu.luc.comp433.api.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.luc.comp433.business.dto.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import static edu.luc.comp433.util.Patterns.EMAIL_PATTERN;
import static edu.luc.comp433.util.Patterns.PHONE_PATTERN;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRepresentation {

    private long id;

    @NonNull
    @NotNull
    @Size(min = 3, max = 25)
    private String firstName;

    @NonNull
    @NotNull
    @Size(min = 3, max = 25)
    private String lastName;

    @NonNull
    @Pattern(regexp = EMAIL_PATTERN)
    private String email;

    @Pattern(regexp = PHONE_PATTERN)
    private String phonenumber;

    public static CustomerRepresentation of(CustomerDto dto) {
        return new CustomerRepresentation(dto.getId(), dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPhonenumber());
    }
}
