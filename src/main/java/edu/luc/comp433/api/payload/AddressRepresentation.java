package edu.luc.comp433.api.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "address")
public class AddressRepresentation {

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


}
