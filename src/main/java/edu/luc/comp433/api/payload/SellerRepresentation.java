package edu.luc.comp433.api.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "seller")
public class SellerRepresentation {

    @NonNull
    @NotNull
    private long id;

    @NonNull
    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    private String website;

    private String email;

}
