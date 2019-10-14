package edu.luc.comp433.api.payload;

import edu.luc.comp433.business.dto.AddressDto;
import edu.luc.comp433.business.dto.PaymentDto;
import edu.luc.comp433.util.Patterns;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "payment")
public class PaymentRequest {

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

    @Min(1)
    private long addressId;

    public PaymentDto toDto() {
        PaymentDto dto = new PaymentDto();
        dto.setNameOnCard(this.nameOnCard);
        dto.setCardNumber(this.cardNumber);
        dto.setExpireMonth(this.expireMonth);
        dto.setExpireYear(this.expireYear);
        dto.setBillingAddress(new AddressDto(addressId));
        return dto;
    }
}
