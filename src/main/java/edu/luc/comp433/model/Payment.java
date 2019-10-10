package edu.luc.comp433.model;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NonNull
    @NotBlank
    private String nameOnCard;

    @NonNull
    @NotBlank
    private String cardNumber;

    private String cardType;

    private int expireMonth;

    private int expireYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address billingAddress;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        return id != null && id.equals(((Payment) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
