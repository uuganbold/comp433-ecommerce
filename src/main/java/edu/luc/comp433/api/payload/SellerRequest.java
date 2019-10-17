package edu.luc.comp433.api.payload;

import edu.luc.comp433.util.Patterns;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "seller")
public class SellerRequest {

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @Pattern(regexp = Patterns.WEB_PATTERN)
    private String website;

    @Pattern(regexp = Patterns.EMAIL_PATTERN)
    private String email;

}
