package edu.luc.comp433.business.dto;

import edu.luc.comp433.model.Payment;
import edu.luc.comp433.util.Patterns;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private Long id;

    @Size(min = 5, max = 40)
    private String nameOnCard;

    @Pattern(regexp = Patterns.CARDNUMBER_PATTERN)
    private String cardNumber;

    private int expireMonth;

    private int expireYear;

    private AddressDto billingAddress;

    public static PaymentDto of(Payment a) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(a.getId());
        paymentDto.setNameOnCard(a.getNameOnCard());
        paymentDto.setCardNumber(a.getCardNumber());
        paymentDto.setExpireMonth(a.getExpireMonth());
        paymentDto.setExpireYear(a.getExpireYear());
        paymentDto.setBillingAddress(AddressDto.of(a.getBillingAddress()));
        return paymentDto;
    }

    public Payment toEntity() {
        Payment p = new Payment();
        p.setId(this.id);
        p.setNameOnCard(this.nameOnCard);
        p.setCardNumber(this.cardNumber);
        p.setExpireMonth(this.expireMonth);
        p.setExpireYear(this.expireYear);
        return p;
    }
}
