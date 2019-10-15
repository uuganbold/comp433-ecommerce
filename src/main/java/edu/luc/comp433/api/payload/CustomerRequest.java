package edu.luc.comp433.api.payload;

import edu.luc.comp433.business.dto.CustomerDTO;
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
@NoArgsConstructor
@XmlRootElement(name = "customer")
public class CustomerRequest {
    @NonNull
    @NotNull
    @Size(min = 3, max = 25)
    private String firstName;

    @NonNull
    @NotNull
    @Size(min = 3, max = 25)
    private String lastName;

    @NonNull
    @NotNull
    @Pattern(regexp = EMAIL_PATTERN)
    private String email;

    @Pattern(regexp = PHONE_PATTERN)
    private String phonenumber;

    public CustomerDTO toDTO() {
        return new CustomerDTO(this.getFirstName(), this.getLastName(), this.email, this.phonenumber);
    }
}
