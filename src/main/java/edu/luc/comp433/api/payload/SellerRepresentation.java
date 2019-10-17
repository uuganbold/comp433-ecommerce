package edu.luc.comp433.api.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.luc.comp433.business.dto.SellerDTO;
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

    public static SellerRepresentation of(SellerDTO sellerDTO) {
        if (sellerDTO == null) return null;
        return new SellerRepresentation(sellerDTO.getId(), sellerDTO.getName(), sellerDTO.getWebsite(), sellerDTO.getEmail());
    }
}
