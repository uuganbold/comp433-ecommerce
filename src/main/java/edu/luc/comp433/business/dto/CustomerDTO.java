package edu.luc.comp433.business.dto;

import edu.luc.comp433.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static edu.luc.comp433.util.Patterns.EMAIL_PATTERN;
import static edu.luc.comp433.util.Patterns.PHONE_PATTERN;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private Long id;

    @Size(min = 3, max = 25)
    private String firstName;

    @Size(min = 3, max = 25)
    private String lastName;

    @Pattern(regexp = EMAIL_PATTERN)
    private String email;

    @Pattern(regexp = PHONE_PATTERN)
    private String phonenumber;

    public CustomerDTO(@NonNull @NotNull @Size(min = 3, max = 25) String firstName, @NonNull @NotNull @Size(min = 3, max = 25) String lastName, @NonNull @Pattern(regexp = EMAIL_PATTERN) String email, @Pattern(regexp = PHONE_PATTERN) String phonenumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phonenumber = phonenumber;
    }

    public CustomerDTO(Long id) {
        this.id = id;
    }

    public static CustomerDTO of(Customer customer) {
        return new CustomerDTO(customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhonenumber());
    }

    public Customer toEntity() {
        Customer customer = new Customer();
        customer.setFirstName(this.firstName);
        customer.setLastName(this.lastName);
        customer.setEmail(this.email);
        customer.setPhonenumber(this.phonenumber);
        return customer;
    }
}
