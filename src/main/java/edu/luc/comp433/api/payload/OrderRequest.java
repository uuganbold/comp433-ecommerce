package edu.luc.comp433.api.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.luc.comp433.business.dto.AddressDTO;
import edu.luc.comp433.business.dto.CustomerDTO;
import edu.luc.comp433.business.dto.OrderDTO;
import edu.luc.comp433.business.dto.PaymentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRequest {

    @NotNull
    private Long customerId;

    @NotNull
    private List<OrderItemRequest> items = new ArrayList<>();

    @NotNull
    private Long addressId;

    @NotNull
    private Long paymentId;

    public OrderDTO toDTO() {
        OrderDTO dto = new OrderDTO()
                .setAddress(new AddressDTO(addressId))
                .setCustomer(new CustomerDTO(customerId))
                .setPayment(new PaymentDTO(paymentId));
        items.forEach(i -> dto.addItem(i.toDTO()));
        return dto;
    }
}
