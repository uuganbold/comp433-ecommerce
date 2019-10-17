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
public class PaymentDTO {

    private Long id;

    @Size(min = 5, max = 40)
    private String nameOnCard;

    @Pattern(regexp = Patterns.CARDNUMBER_PATTERN)
    private String cardNumber;

    private int expireMonth;

    private int expireYear;

    private AddressDTO billingAddress;

    public static PaymentDTO of(Payment a) {
        PaymentDTO paymentDto = new PaymentDTO();
        paymentDto.setId(a.getId());
        paymentDto.setNameOnCard(a.getNameOnCard());
        paymentDto.setCardNumber(a.getCardNumber());
        paymentDto.setExpireMonth(a.getExpireMonth());
        paymentDto.setExpireYear(a.getExpireYear());
        return paymentDto;
    }

    public PaymentDTO setBillingAddress(AddressDTO addressDTO) {
        this.billingAddress = addressDTO;
        return this;
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

    public PaymentDTO(Long id) {
        this.id = id;
    }
}
