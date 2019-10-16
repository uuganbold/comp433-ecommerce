package edu.luc.comp433.api.payload;

import edu.luc.comp433.business.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "order")
public class OrderRepresentation {

    private Long id;

    private CustomerRepresentation customer;

    private Instant date;

    private Instant updateDate;

    private List<OrderItemRepresentation> items = new ArrayList<>();

    private String status;

    private AddressRepresentation address;

    private PaymentRepresentation payment;

    private double totalValue;

    public OrderRepresentation(Long id, Instant date, Instant updateDate, String status, Double totalValue) {
        this.id = id;
        this.date = date;
        this.updateDate = updateDate;
        this.status = status;
        this.totalValue = totalValue;
    }

    public static OrderRepresentation of(OrderDTO dto) {
        OrderRepresentation order = new OrderRepresentation(dto.getId(), dto.getDate(), dto.getUpdateDate(), dto.getStatus(), dto.getTotalValue());
        dto.getItems().forEach(i -> order.getItems().add(OrderItemRepresentation.of(i)));
        if (dto.getCustomer() != null) order.setCustomer(CustomerRepresentation.of(dto.getCustomer()));
        if (dto.getAddress() != null) order.setAddress(AddressRepresentation.of(dto.getAddress()));
        if (dto.getPayment() != null) order.setPayment(PaymentRepresentation.of(dto.getPayment()));
        return order;
    }
}
