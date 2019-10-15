package edu.luc.comp433.api.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.luc.comp433.business.dto.PaymentDTO;
import edu.luc.comp433.util.Patterns;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "payment")
@NoArgsConstructor
public class PaymentRepresentation {

    private Long id;

    @NonNull
    @NotNull
    @Size(min = 5, max = 40)
    private String nameOnCard;

    @NonNull
    @NotNull
    @Pattern(regexp = Patterns.CARDNUMBER_PATTERN)
    private String cardNumber;

    @Min(1)
    @Max(12)
    private int expireMonth;

    @Min(2019)
    private int expireYear;

    private AddressRepresentation billingAddress;

    public static PaymentRepresentation of(PaymentDTO dto) {
        PaymentRepresentation p = new PaymentRepresentation();
        p.setId(dto.getId());
        p.setNameOnCard(dto.getNameOnCard());
        p.setCardNumber(dto.getCardNumber());
        p.setExpireMonth(dto.getExpireMonth());
        p.setExpireYear(dto.getExpireYear());
        p.setBillingAddress(dto.getBillingAddress() != null ? AddressRepresentation.of(dto.getBillingAddress()) : null);
        return p;
    }
}
