package edu.luc.comp433.api.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "seller")
public class SellerRequest {

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    private String website;

    private String email;

}
